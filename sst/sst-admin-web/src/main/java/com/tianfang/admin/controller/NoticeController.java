package com.tianfang.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.dto.AdminDto;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.StringUtils;
import com.tianfang.message.dto.NoticeDto;
import com.tianfang.message.service.INoticeService;

/**
 * 公告Controller
 * @author YIn
 * @time:2016年1月13日 上午9:39:09
 * @ClassName: NoticeController
 * @Description: TODO
 * @
 */
@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController {

	@Autowired
	private INoticeService noticeService;
	
	/**
	 * 跳转至新增页面
	 * @return
	 */
	@RequestMapping(value = "goAdd.do")
	public ModelAndView goAdd() {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		mv.setViewName("/notice/notice_add");
		return mv;
	}

	/**
	 * 增加公告
	 * @author YIn
	 * @time:2016年1月13日 上午10:11:14
	 * @param albumPicDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add")
	public Response<String> addNotice(NoticeDto noticeDto){
		Response<String> data = new Response<String>();
		AdminDto admin = getSessionAdmin();
		noticeDto.setCreateUserId(admin.getId());
		noticeDto.setCreateUserName(admin.getAccount());
		int flag = noticeService.addNotice(noticeDto);
		if(flag > 0){
			data.setMessage("添加公告成功");
			data.setStatus(200);
		}else{
			data.setMessage("添加公告失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 跳转至编辑页面
	 * @return
	 */
	@RequestMapping(value = "goEdit.do")
	public ModelAndView goEdit(String noticeId) {
		logger.info("去公告修改页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		NoticeDto noticeDto = new NoticeDto();
		noticeDto.setId(noticeId);
		List<NoticeDto> list =noticeService.findNotice(noticeDto);
		try {
			mv.setViewName("/notice/notice_edit");
			mv.addObject("msg", "edit");
			mv.addObject("noticeDto", list.get(0));
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 根据主键Id编辑公告
	 * @author YIn
	 * @time:2016年1月13日 上午11:28:05
	 * @param noticeDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/update")
	public Response<String> updateNotice(NoticeDto noticeDto){
		Response<String> data = new Response<String>();
		int flag = noticeService.updateNotice(noticeDto);
		if(flag > 0){
			data.setMessage("编辑公告成功");
			data.setStatus(200);
		}else{
			data.setMessage("编辑公告失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月13日 上午11:28:05
	 * @param noticeDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/del")
	public Response<String> delNoticeLogic(NoticeDto noticeDto){
		Response<String> data = new Response<String>();
		
		int flag = noticeService.delNotice(noticeDto);
		if(flag > 0){
			data.setMessage("删除公告成功");
			data.setStatus(200);
		}else{
			data.setMessage("删除公告失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id删除 -逻辑删除
	 * @author YIn
	 * @time:2016年1月13日 下午5:54:33
	 * @param noticeDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete.do")
	public Map<String, Object> delNotice(NoticeDto noticeDto){
		logger.info("NoticeDto"+noticeDto);
		noticeDto.setStat(0); //逻辑删除
		int status = noticeService.updateNotice(noticeDto);
		if(status > 0){
			return MessageResp.getMessage(true,"删除公告成功");
		}
			return MessageResp.getMessage(false,"删除公告失败");
		
	}
	
	/**
	 * 批量删除
	 * @author YIn
	 * @time:2015年11月16日 下午3:14:51
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/delByIds.do")
    public Map<String, Object> delByIds(String  ids) throws Exception{
	    if (StringUtils.isEmpty(ids)) {
	        return MessageResp.getMessage(false, "请选择需要删除的项！");
	    }
	    Integer resObject =(Integer) noticeService.delByIds(ids);
	    if (resObject == 0) {
            return MessageResp.getMessage(false, "批量删除失败");
        }
	    if (resObject == 1) {
            return MessageResp.getMessage(true, "批量删除成功");
        }
	    return MessageResp.getMessage(false, "删除异常");
	}
	
	/**
	 * 查询公告-不分页
	 * @author YIn
	 * @time:2016年1月13日 上午11:28:05
	 * @param noticeDto
	 * @return
	 */
	@RequestMapping(value="/find")
	@ResponseBody
	public Response<List<NoticeDto>> findNotice(NoticeDto noticeDto){
		Response<List<NoticeDto>> data = new Response<List<NoticeDto>>();
		
		List<NoticeDto> result = noticeService.findNotice(noticeDto);
		if(result.size() > 0){
			data.setMessage("查询公告成功");
			data.setStatus(200);
			data.setData(result);
		}else{
			data.setMessage("查询公告失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 后台公告显示页面-分页
	 * @author YIn
	 * @time:2016年1月13日 下午2:17:03
	 * @param noticeDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findNoticeView")
	public ModelAndView findNoticeViewByPage(NoticeDto noticeDto, ExtPageQuery page){
		logger.info("NoticeDto  : " + noticeDto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<NoticeDto> result = noticeService.queryNoticeViewByPage(noticeDto, page.changeToPageQuery());
		mv.addObject("pageList", result);
		mv.addObject("noticeDto", noticeDto);
		mv.setViewName("/notice/notice_list");
		return mv;
	}
	
	
}
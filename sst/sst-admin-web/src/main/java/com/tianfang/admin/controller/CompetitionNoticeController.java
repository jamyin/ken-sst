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
import com.tianfang.train.dto.CompetitionDto;
import com.tianfang.train.dto.CompetitionNoticeDto;
import com.tianfang.train.service.ICompetitionNoticeService;
import com.tianfang.train.service.ICompetitionService;

/**
 * 赛事公告Controller
 * @author YIn
 * @time:2016年1月15日 上午9:39:09
 * @ClassName: CompetitionNoticeController
 * @Description: TODO
 * @
 */
@Controller
@RequestMapping("/competition/notice")
public class CompetitionNoticeController extends BaseController {

	@Autowired
	private ICompetitionNoticeService competitionNoticeService;
	
	@Autowired
	private ICompetitionService competitionService;
	
	/**
	 * 跳转至新增页面
	 * @return
	 */
	@RequestMapping(value = "goAdd.do")
	public ModelAndView goAdd() {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		List<CompetitionDto> compList =  competitionService.findCompetition(new CompetitionDto());
		mv.addObject("allComp", compList);
		mv.setViewName("/competition/notice/notice_add");
		return mv;
	}

	/**
	 * 增加赛事公告
	 * @author YIn
	 * @time:2016年1月15日 上午10:11:14
	 * @param albumPicDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add")
	public Response<String> addCompetitionNotice(CompetitionNoticeDto competitionNoticeDto){
		Response<String> data = new Response<String>();
		AdminDto admin = getSessionAdmin();
		competitionNoticeDto.setCreateAdminId(admin.getId());
		competitionNoticeDto.setCreateAdminName(admin.getAccount());
		int flag = competitionNoticeService.addCompetitionNotice(competitionNoticeDto);
		if(flag > 0){
			data.setMessage("添加赛事公告成功");
			data.setStatus(200);
		}else{
			data.setMessage("添加赛事公告失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 跳转至编辑页面
	 * @return
	 */
	@RequestMapping(value = "goEdit.do")
	public ModelAndView goEdit(String id) {
		logger.info("去赛事公告修改页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		CompetitionNoticeDto competitionNoticeDto = new CompetitionNoticeDto();
		competitionNoticeDto.setId(id);
		List<CompetitionNoticeDto> list =competitionNoticeService.findCompetitionNotice(competitionNoticeDto);
		List<CompetitionDto> compList =  competitionService.findCompetition(new CompetitionDto());
		mv.addObject("allComp", compList);
		try {
			mv.setViewName("/competition/notice/notice_edit");
			mv.addObject("msg", "edit");
			mv.addObject("competitionNoticeDto", list.get(0));
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 根据主键Id编辑赛事公告
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:05
	 * @param competitionNoticeDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/update")
	public Response<String> updateCompetitionNotice(CompetitionNoticeDto competitionNoticeDto){
		Response<String> data = new Response<String>();
		int flag = competitionNoticeService.updateCompetitionNotice(competitionNoticeDto);
		if(flag > 0){
			data.setMessage("编辑赛事公告成功");
			data.setStatus(200);
		}else{
			data.setMessage("编辑赛事公告失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:05
	 * @param competitionNoticeDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/del")
	public Response<String> delCompetitionNoticeLogic(CompetitionNoticeDto competitionNoticeDto){
		Response<String> data = new Response<String>();
		int flag = competitionNoticeService.delCompetitionNotice(competitionNoticeDto);
		if(flag > 0){
			data.setMessage("删除赛事公告成功");
			data.setStatus(200);
		}else{
			data.setMessage("删除赛事公告失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id删除 -逻辑删除
	 * @author YIn
	 * @time:2016年1月15日 下午5:54:33
	 * @param competitionNoticeDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete.do")
	public Map<String, Object> delCompetitionNotice(CompetitionNoticeDto competitionNoticeDto){
		logger.info("competitionNoticeDto"+competitionNoticeDto);
		competitionNoticeDto.setStat(0); //逻辑删除
		int status = competitionNoticeService.updateCompetitionNotice(competitionNoticeDto);
		if(status > 0){
			return MessageResp.getMessage(true,"删除赛事公告成功");
		}
			return MessageResp.getMessage(false,"删除赛事公告失败");
		
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
	    Integer resObject =(Integer) competitionNoticeService.delByIds(ids);
	    if (resObject == 0) {
            return MessageResp.getMessage(false, "批量删除失败");
        }
	    if (resObject == 1) {
            return MessageResp.getMessage(true, "批量删除成功");
        }
	    return MessageResp.getMessage(false, "删除异常");
	}
	
	/**
	 * 查询赛事公告-不分页
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:05
	 * @param competitionNoticeDto
	 * @return
	 */
	@RequestMapping(value="/find")
	@ResponseBody
	public Response<List<CompetitionNoticeDto>> findCompetitionNotice(CompetitionNoticeDto competitionNoticeDto){
		Response<List<CompetitionNoticeDto>> data = new Response<List<CompetitionNoticeDto>>();
		
		List<CompetitionNoticeDto> result = competitionNoticeService.findCompetitionNotice(competitionNoticeDto);
		if(result.size() > 0){
			data.setMessage("查询赛事公告成功");
			data.setStatus(200);
			data.setData(result);
		}else{
			data.setMessage("查询赛事公告失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 后台赛事公告显示页面-分页
	 * @author YIn
	 * @time:2016年1月15日 下午2:17:03
	 * @param competitionNoticeDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findCompetitionNoticeView")
	public ModelAndView findCompetitionNoticeViewByPage(CompetitionNoticeDto competitionNoticeDto, ExtPageQuery page){
		logger.info("competitionNoticeDto  : " + competitionNoticeDto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<CompetitionNoticeDto> result = competitionNoticeService.findCompetitionNoticeViewByPage(competitionNoticeDto, page.changeToPageQuery());
		mv.addObject("pageList", result);
		mv.addObject("competitionNoticeDto", competitionNoticeDto);
		mv.setViewName("/competition/notice/notice_list");
		return mv;
	}
	
	/**
	 * 后台赛事公告显示页面-分页(连接赛事表)
	 * @author YIn
	 * @time:2016年1月22日 下午5:34:12
	 * @param competitionNoticeDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findCompNoticeView")
	public ModelAndView findCompNoticeViewByPage(CompetitionNoticeDto competitionNoticeDto, ExtPageQuery page){
		logger.info("competitionNoticeDto  : " + competitionNoticeDto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<CompetitionNoticeDto> result = competitionNoticeService.findCompNoticeViewByPage(competitionNoticeDto, page.changeToPageQuery());
		List<CompetitionDto> compList =  competitionService.findCompetition(new CompetitionDto());
		mv.addObject("pageList", result);
		mv.addObject("competitionNoticeDto", competitionNoticeDto);
		mv.addObject("allComp", compList);
		mv.setViewName("/competition/notice/notice_list");
		return mv;
	}
	
	
}
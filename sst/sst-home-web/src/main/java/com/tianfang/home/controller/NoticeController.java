package com.tianfang.home.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.StringUtils;
import com.tianfang.message.dto.NoticeDto;
import com.tianfang.message.dto.NoticeUsersDto;
import com.tianfang.message.service.INoticeService;
import com.tianfang.message.service.INoticeUsersService;
import com.tianfang.user.dto.UserDto;

/**
 * SST给移动端接口-消息助手-公告
 * @author YIn
 * @time:2016年2月2日 下午4:49:57
 * @ClassName: NoticeController
 * @
 */
@Controller
@RequestMapping(value = "/notice")
public class NoticeController extends BaseController {
	
	protected static final Log logger = LogFactory.getLog(NoticeController.class);

	@Autowired
	private INoticeService noticeService;

	@Autowired
	private INoticeUsersService noticeUsersService;
	
	/**
	 * 公告显示页面-分页
	 * @author YIn
	 * @time:2016年2月2日 下午4:54:25
	 * @param noticeDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findNoticeView")
	public Response<PageResult<NoticeDto>> findNoticeViewByPage(NoticeDto noticeDto, ExtPageQuery page){
		logger.info("NoticeDto  : " + noticeDto);
		Response<PageResult<NoticeDto>> response = new Response<PageResult<NoticeDto>>();
		PageResult<NoticeDto> result = noticeService.findNoticeViewByPage(noticeDto, page.changeToPageQuery());
		if(result != null){
			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setData(result);
			response.setMessage("查询成功");
		}else{
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("查询失败");
		}
		return response;
	}
	
	/**
	 * 增加公告
	 * @author YIn
	 * @time:2016年2月3日 上午11:47:35
	 * @param noticeDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add")
	public Response<String> addNotice(NoticeDto noticeDto){
		Response<String> data = new Response<String>();
		UserDto dto = getLoginUser();
		noticeDto.setCreateUserId(dto.getId());
		noticeDto.setCreateUserName(dto.getMobile());
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
	 * 根据主键Id编辑公告
	 * @author YIn
	 * @time:2016年2月3日 上午11:52:51
	 * @param noticeDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/update")
	public Response<String> updateNotice(NoticeDto noticeDto){
		Response<String> data = new Response<String>();
		UserDto dto = getLoginUser();
		noticeDto.setCreateUserId(dto.getId());
		noticeDto.setCreateUserName(dto.getMobile());
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
	 * 根据主键Id删除 -逻辑删除
	 * @author YIn
	 * @time:2016年2月3日 上午11:53:42
	 * @param noticeDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete.do")
	public Response<String> delNotice(NoticeDto noticeDto){
		logger.info("NoticeDto"+noticeDto);
		Response<String> data = new Response<String>();
		noticeDto.setStat(0); //逻辑删除
		int status = noticeService.updateNotice(noticeDto);
		if(status > 0){
			data.setMessage("删除公告成功");
			data.setStatus(200);
		}else{
			data.setMessage("删除公告失败");
			data.setStatus(500);
		}
		return data;
	}
	
	/**
	 * 批量删除
	 * @author YIn
	 * @time:2016年2月3日 上午11:55:43
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/delByIds.do")
    public Response<String> delByIds(String  ids) throws Exception{
		Response<String> data = new Response<String>();
	    if (StringUtils.isEmpty(ids)) {
	    	data.setMessage("没有选取公告");
			data.setStatus(500);
			return data;
	    }
	    Integer resObject =(Integer) noticeService.delByIds(ids);
	    if (resObject == 1) {
	    	data.setMessage("批量删除成功");
			data.setStatus(500);
        }
	    if (resObject == 0) {
	    	data.setMessage("批量删除失败");
			data.setStatus(500);
        }
	    return data;
	}
	
	/**
	 * 手机端发布公告(移动端转json格式字符串)
	 * @author YIn
	 * @time:2016年2月3日 上午11:42:44
	 * @param noticeDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/release")
	@Transactional
	public Response<String> release(String jsonPara){
		Gson gson = new Gson();
	    NoticeDto noticeDto =  gson.fromJson(jsonPara, NoticeDto.class);
		Response<String> data = new Response<String>();
		UserDto dto = getLoginUser();
		noticeDto.setCreateUserId(dto.getId());
		noticeDto.setCreateUserName(dto.getMobile());
		int flag = noticeService.addNotice(noticeDto);
		List<NoticeUsersDto> list = new ArrayList<NoticeUsersDto>();
		if(flag > 0){
			String [] userIds = noticeDto.getUserIds();
			NoticeUsersDto noticeUsersDto = new NoticeUsersDto();
			if(userIds.length > 0){
				for(String id : userIds){
					noticeUsersDto.setNoticeId(noticeDto.getId());
					noticeUsersDto.setUserId(id);
					list.add(noticeUsersDto);
				}
			}
		}
		int releaseFlag = noticeUsersService.releaseNotice(list);
		if(flag > 0 && releaseFlag > 0){
			data.setMessage("发布公告成功");
			data.setStatus(200);
		}else{
			data.setMessage("发布公告失败");
			data.setStatus(500);
		}	   	
		return data;
	}

	/**
	 * 提供移动端.轮询获取最新一条公告
	 * @param userId
	 * @return
     */
	@ResponseBody
	@RequestMapping(value="getLast")
	public Response<NoticeDto> getLast(String userId){
		Response<NoticeDto> response = new Response<NoticeDto>();
		if (StringUtils.isBlank(userId)){
			response.setMessage("参数异常~");
			response.setStatus(DataStatus.DISABLED);
			return response;
		}
		NoticeDto last = noticeUsersService.getLast(userId);
		response.setData(last);
		return response;
	}
}
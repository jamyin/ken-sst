package com.tianfang.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.StringUtils;
import com.tianfang.message.dto.MessageDto;
import com.tianfang.message.service.IMessageService;

/**
 * 消息Controller
 * @author YIn
 * @time:2016年1月14日 上午9:34:20
 * @ClassName: MessageController
 * @Description: TODO
 * @
 */
@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {

	@Autowired
	private IMessageService messageService;
	
	
	/**
	 * 跳转至新增页面
	 * @author YIn
	 * @time:2016年1月14日 下午1:10:10
	 * @return
	 */
	 
	@RequestMapping(value = "goAdd.do")
	public ModelAndView goAdd() {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		mv.setViewName("/message/message_add");
		return mv;
	}

	/**
	 * 增加消息
	 * @author YIn
	 * @time:2016年1月13日 上午10:11:14
	 * @param albumPicDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add")
	public Response<String> addmessage(MessageDto messageDto){
		Response<String> data = new Response<String>();

		int flag = messageService.addMessage(messageDto);
		if(flag > 0){
			data.setMessage("添加消息成功");
			data.setStatus(200);
		}else{
			data.setMessage("添加消息失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 跳转至编辑页面
	 * @return
	 */
	@RequestMapping(value = "goEdit.do")
	public ModelAndView goEdit(String messageId) {
		logger.info("去消息修改页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		MessageDto messageDto = new MessageDto();
		messageDto.setId(messageId);
		List<MessageDto> list =messageService.findMessage(messageDto);
		try {
			mv.setViewName("/message/message_edit");
			mv.addObject("msg", "edit");
			mv.addObject("messageDto", list.get(0));
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 根据主键Id编辑消息
	 * @author YIn
	 * @time:2016年1月13日 上午11:28:05
	 * @param messageDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/update")
	public Response<String> updatemessage(MessageDto messageDto){
		Response<String> data = new Response<String>();
		
		int flag = messageService.updateMessage(messageDto);
		if(flag > 0){
			data.setMessage("编辑消息成功");
			data.setStatus(200);
		}else{
			data.setMessage("编辑消息失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月13日 上午11:28:05
	 * @param messageDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/del")
	public Response<String> delmessageLogic(MessageDto messageDto){
		Response<String> data = new Response<String>();
		
		int flag = messageService.delMessage(messageDto);
		if(flag > 0){
			data.setMessage("删除消息成功");
			data.setStatus(200);
		}else{
			data.setMessage("删除消息失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id删除 -逻辑删除
	 * @author YIn
	 * @time:2016年1月13日 下午5:54:33
	 * @param messageDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete.do")
	public Map<String, Object> delmessage(MessageDto messageDto){
		logger.info("messageDto"+messageDto);
		messageDto.setStat(0); //逻辑删除
		int status = messageService.updateMessage(messageDto);
		if(status > 0){
			return MessageResp.getMessage(true,"删除消息成功");
		}
			return MessageResp.getMessage(false,"删除消息失败");
		
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
	    Integer resObject =(Integer) messageService.delByIds(ids);
	    if (resObject == 0) {
            return MessageResp.getMessage(false, "批量删除失败");
        }
	    if (resObject == 1) {
            return MessageResp.getMessage(true, "批量删除成功");
        }
	    return MessageResp.getMessage(false, "删除异常");
	}
	
	/**
	 * 查询消息-不分页
	 * @author YIn
	 * @time:2016年1月13日 上午11:28:05
	 * @param messageDto
	 * @return
	 */
	@RequestMapping(value="/find")
	@ResponseBody
	public Response<List<MessageDto>> findmessage(MessageDto messageDto){
		Response<List<MessageDto>> data = new Response<List<MessageDto>>();
		
		List<MessageDto> result = messageService.findMessage(messageDto);
		if(result.size() > 0){
			data.setMessage("查询消息成功");
			data.setStatus(200);
			data.setData(result);
		}else{
			data.setMessage("查询消息失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 后台消息显示页面-分页
	 * @author YIn
	 * @time:2016年1月13日 下午2:17:03
	 * @param messageDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findMessageView")
	public ModelAndView findMessageViewByPage(MessageDto messageDto, ExtPageQuery page){
		logger.info("messageDto  : " + messageDto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<MessageDto> result = messageService.findMessageViewByPage(messageDto, page.changeToPageQuery());
		mv.addObject("pageList", result);
		mv.addObject("messageDto", messageDto);
		mv.setViewName("/message/message_list");
		return mv;
	}
    
	
	
}
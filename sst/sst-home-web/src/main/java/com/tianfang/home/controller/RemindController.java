package com.tianfang.home.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Objects;
import com.google.gson.Gson;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.FileUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.user.dto.RemindDto;
import com.tianfang.user.dto.RemindUsersDto;
import com.tianfang.user.dto.UserDto;
import com.tianfang.user.service.IRemindService;
import com.tianfang.user.service.IRemindUsersService;
import com.tianfang.user.service.IUserService;

/**
 * 
	 * 此类描述的是：提醒接口
	 * @author: cwftalus@163.com
	 * @version: 2016年3月31日 下午3:17:30
 */
@Controller
@RequestMapping(value="remind")
public class RemindController {
	
	@Autowired
	private IRemindService iRemindService;
	
	@Autowired
	private IRemindUsersService iRemindUsersService;
	
	@Autowired
	private IUserService iuserService;
	
	/**
	 * 
		 * 此方法描述的是：提醒保存数据
		 * @author: cwftalus@163.com
		 * @version: 2016年3月31日 下午3:33:56
	 */
	@RequestMapping(value="save")
	@ResponseBody
	public Response<String> addNotice(String jsonPara,
			@RequestParam(value = "attachment",required = false)  MultipartFile fileO,
			@RequestParam(value = "voice",required = false)  MultipartFile fileT,
			HttpServletRequest request){
		Response<String> data = new Response<String>();
		Gson gson = new Gson();
		
	    RemindDto remindDto =  gson.fromJson(jsonPara, RemindDto.class);
	    if(Objects.equal(remindDto, null)){
			data.setMessage("无数据接收,无法提交");
			data.setStatus(DataStatus.HTTP_FAILE);	    	
	    	return data;
	    }
	    
		
		/*UserDto dto = getLoginUser();
		noticeDto.setCreateUserId(dto.getId());
		noticeDto.setCreateUserName(dto.getMobile());*/
		//noticeDto.setCreateUserId("a3efc072-0142-4fff-b951-3adc659a70c9");
		if(StringUtils.isEmpty(remindDto.getUserId())){//发送者用户Id
			data.setMessage("发布公告者Id为空");
			data.setStatus(DataStatus.HTTP_FAILE);
			return data;
		}
				
		UserDto userDto = iuserService.getUserById(remindDto.getUserId());
		if(Objects.equal(userDto, null)){
			data.setMessage("发布公告者Id为空");
			data.setStatus(DataStatus.HTTP_FAILE);
			return data;
		}
		
		//对象处理
		remindDto = copyBeanUtils(remindDto,userDto,fileO,fileT);
		
		//保存
		try {
			insertRemindAndList(remindDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		UserDto userDto = userService.getUserById(noticeDto.getCreateUserId());
//		if(userDto != null){
//			noticeDto.setCreateUserName(userDto.getNickName());
//		}
//		if (file != null) {
//        	Map<String, String> map = uploadImages(file , request);
//        	noticeDto.setPic(map.get("fileUrl"));
//        }
//		noticeDto.setId(UUIDGenerator.getUUID());
//		int flag = noticeService.addNotice(noticeDto);
//		List<remindUserDto> list = new ArrayList<remindUserDto>();
//		if(flag > 0){
//			String [] userIds = noticeDto.getUserIds();
//			if(userIds.length > 0){
//				for(String id : userIds){
//					remindUserDto remindUserDto = new remindUserDto();
//					remindUserDto.setId(UUIDGenerator.getUUID());
//					remindUserDto.setReadstat(DataStatus.DISABLED);      //默认未读
//					remindUserDto.setCreateTime(new Date());
//					remindUserDto.setStat(DataStatus.ENABLED);
//					remindUserDto.setNoticeId(noticeDto.getId());
//					remindUserDto.setUserId(id);
//					list.add(remindUserDto);
//				}
//			}
//		}
//		int releaseFlag = noticeUsersService.releaseNotice(list);
//		if(flag > 0 && releaseFlag > 0){
//			data.setMessage("发布公告成功");
//			data.setStatus(DataStatus.HTTP_SUCCESS);
//		}else{
//			data.setMessage("发布公告失败");
//			data.setStatus(DataStatus.HTTP_FAILE);
//		}	   	
		return data;
	}

	@Transactional
	private void insertRemindAndList(RemindDto remindDto) throws Exception {
		// TODO Auto-generated method stub
		String remindId = iRemindService.save(remindDto);

		List<RemindUsersDto> list = new ArrayList<RemindUsersDto>();
		String [] userIds = remindDto.getUserIds();
		if(userIds!=null && userIds.length > 0){
			for(String id : userIds){
				RemindUsersDto remindUserDto = new RemindUsersDto();
				remindUserDto.setId(UUIDGenerator.getUUID());
				remindUserDto.setReadstat(DataStatus.DISABLED);      //默认未读
				remindUserDto.setCreateTime(new Date());
				remindUserDto.setStat(DataStatus.ENABLED);
				remindUserDto.setRemindId(remindId);
				remindUserDto.setUserId(id);
				list.add(remindUserDto);
			}
		}
		
		iRemindUsersService.insertList(list);
	}

	private RemindDto copyBeanUtils(RemindDto remindDto,UserDto userDto,MultipartFile fileO,MultipartFile fileT) {
		if(!StringUtils.isEmpty(userDto.getNickName())){
			remindDto.setToUserName("来自"+userDto.getNickName()+"的消息");	
		}else{
			remindDto.setToUserName("来自"+userDto.getMobile()+"的消息");
		}
		
		//附件
		if (fileO != null) {
			try {
				remindDto.setAttachment(FileUtils.uploadImage(fileO));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		if (fileO != null) {
			try {
				remindDto.setVoice(FileUtils.uploadImage(fileT));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return remindDto;
	}
	
	
}

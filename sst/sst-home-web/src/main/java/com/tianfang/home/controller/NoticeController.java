package com.tianfang.home.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.PropertiesUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.home.dto.AppNoticeDatas;
import com.tianfang.message.dto.NoticeDto;
import com.tianfang.message.dto.NoticeUsersDto;
import com.tianfang.message.service.INoticeService;
import com.tianfang.message.service.INoticeUsersService;
import com.tianfang.user.dto.UserDto;
import com.tianfang.user.service.IUserService;

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
	
	@Autowired
	private IUserService userService;
	
	/**
	 * 公告显示页面-分页
	 * @author YIn
	 * @time:2016年2月2日 下午4:54:25
	 * @param noticeDto
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findNoticeView")
	public Response<PageResult<NoticeDto>> findNoticeViewByPage(NoticeDto noticeDto, PageQuery query){
		logger.info("NoticeDto  : " + noticeDto);
		Response<PageResult<NoticeDto>> response = new Response<PageResult<NoticeDto>>();
		PageResult<NoticeDto> result = noticeService.findNoticeViewByPage(noticeDto, query);
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
	 *  公告详情
	 * @author YIn
	 * @time:2016年3月28日 上午9:50:12
	 * @param noticeDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findNotice")
	public Response<NoticeDto> findNotice(NoticeDto noticeDto){
		logger.info("NoticeDto  : " + noticeDto);
		Response<NoticeDto> response = new Response<NoticeDto>(); 
		List<NoticeDto> list = noticeService.findNotice(noticeDto);
		
		//打开后状态改变
		NoticeUsersDto noticeUsersDto = new NoticeUsersDto();
		noticeUsersDto.setUserId(noticeDto.getUserId());;
		noticeUsersDto.setNoticeId(noticeDto.getId());
		List<NoticeUsersDto> noticeUsersDtoList = noticeUsersService.findNoticeUsers(noticeUsersDto);
		if(noticeUsersDtoList != null && noticeUsersDtoList.size() > 0){
			noticeUsersDtoList.get(0).setReadstat(1);
			noticeUsersService.updateNotice(noticeUsersDtoList.get(0));
		}
		if(list != null && list.size() > 0){
			//查询发送总人数
			int mount = noticeUsersService.findMount(list.get(0).getId());
			//查询已读人数
			int read =  noticeUsersService.findRead(list.get(0).getId());
			//未读人数
		    int unRead = mount - read;
		    list.get(0).setMount(mount);
		    list.get(0).setRead(read);
		    list.get(0).setUnRead(unRead);
		   
			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setData(list.get(0));
			response.setMessage("查询成功");
		}else{
			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setMessage("没有查询到相关记录");
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
			data.setStatus(DataStatus.HTTP_SUCCESS);
		}else{
			data.setMessage("添加公告失败");
			data.setStatus(DataStatus.HTTP_FAILE);
		}	   	
		return data;
	}
	
	/**
	 * 发布公告 - 对某些人发布 (废弃的方法)
	 * @author YIn
	 * @time:2016年3月24日 下午1:35:33
	 * @param 传入参数为 公告内容content 和接收者数组userIds[] 组成的json 格式的字符串
	 * @return
	 */
	@Deprecated
	@ResponseBody
	@RequestMapping(value="/releaseNotice")
	public Response<String> releaseNotice(String jsonString){
		Response<String> result = new Response<String>();
		AppNoticeDatas datas = JSON.parseObject(jsonString, AppNoticeDatas.class);
		if(datas.getNoticeDto() == null){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("公告内容为空");
			return result;
		}
		if(datas.getUserIds().length == 0){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("未选择公告接收人");
			return result;
		}
		UserDto dto = getLoginUser();
		NoticeDto noticeDto = new NoticeDto();
		noticeDto.setCreateUserId(dto.getId());
		noticeDto.setCreateUserName(dto.getMobile());
		
		StringBuffer sb = new StringBuffer();
		for(String s : datas.getUserIds()){
			sb.append(s).append(",");
		}
		noticeDto.setToUsers(sb.toString());
		int flag = noticeService.addNotice(noticeDto);
		if(flag > 0){
			result.setMessage("添加公告成功");
			result.setStatus(DataStatus.HTTP_SUCCESS);
		}else{
			result.setMessage("添加公告失败");
			result.setStatus(DataStatus.HTTP_FAILE);
		}	   	
		return result;
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
			data.setStatus(DataStatus.HTTP_FAILE);
		}else{
			data.setMessage("编辑公告失败");
			data.setStatus(DataStatus.HTTP_SUCCESS);
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
			data.setStatus(DataStatus.HTTP_SUCCESS);
		}else{
			data.setMessage("删除公告失败");
			data.setStatus(DataStatus.HTTP_FAILE);
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
			data.setStatus(DataStatus.HTTP_FAILE);
			return data;
	    }
	    Integer resObject =(Integer) noticeService.delByIds(ids);
	    if (resObject == 1) {
	    	data.setMessage("批量删除成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
        }
	    if (resObject == 0) {
	    	data.setMessage("批量删除失败");
			data.setStatus(DataStatus.HTTP_FAILE);
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
	public Response<String> release(String jsonPara,@RequestParam(value = "file",required = false)  MultipartFile file,
			HttpServletRequest request){
		Gson gson = new Gson();
	    NoticeDto noticeDto =  gson.fromJson(jsonPara, NoticeDto.class);
		Response<String> data = new Response<String>();
		/*UserDto dto = getLoginUser();
		noticeDto.setCreateUserId(dto.getId());
		noticeDto.setCreateUserName(dto.getMobile());*/
		//noticeDto.setCreateUserId("a3efc072-0142-4fff-b951-3adc659a70c9");
		if(StringUtils.isEmpty(noticeDto.getCreateUserId())){
			data.setMessage("发布公告者Id为空");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
		UserDto userDto = userService.getUserById(noticeDto.getCreateUserId());
		if(userDto != null){
			noticeDto.setCreateUserName(userDto.getNickName());
		}
		if (file != null) {
        	Map<String, String> map = uploadImages(file , request);
        	noticeDto.setPic(map.get("fileUrl"));
        }
		noticeDto.setId(UUIDGenerator.getUUID());
		int flag = noticeService.addNotice(noticeDto);
		List<NoticeUsersDto> list = new ArrayList<NoticeUsersDto>();
		if(flag > 0){
			String [] userIds = noticeDto.getUserIds();
			if(userIds.length > 0){
				for(String id : userIds){
					NoticeUsersDto noticeUsersDto = new NoticeUsersDto();
					noticeUsersDto.setId(UUIDGenerator.getUUID());
					noticeUsersDto.setReadstat(DataStatus.DISABLED);      //默认未读
					noticeUsersDto.setCreateTime(new Date());
					noticeUsersDto.setStat(DataStatus.ENABLED);
					noticeUsersDto.setNoticeId(noticeDto.getId());
					noticeUsersDto.setUserId(id);
					list.add(noticeUsersDto);
				}
			}
		}
		int releaseFlag = noticeUsersService.releaseNotice(list);
		if(flag > 0 && releaseFlag > 0){
			data.setMessage("发布公告成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		}else{
			data.setMessage("发布公告失败");
			data.setStatus(DataStatus.HTTP_FAILE);
		}	   	
		return data;
	}
	
    @ResponseBody
    @RequestMapping("/uploadImages.do"   )  
    public Map<String, String> uploadImages(@RequestParam("file") MultipartFile file,HttpServletRequest request){      	
    	//String context = "/upload";
		String realPath = PropertiesUtils.getProperty("upload.url");
		String fileDe = DateUtils.format(new Date(), DateUtils.YMD);
		String path = "";
		String filePath = "";
		String fileName = ""; //重新新命名
		String realName = "";
		Map<String, String> m = new HashMap<String, String>();
    	if(file.isEmpty()){
    		System.out.println("请选择需要上传的文件!");  
    		m.put("message", "请选择需要上传的文件!");
	       	return m;
    	}else{
    			realName = file.getOriginalFilename();
 	            System.out.println("fileName4---------->" + realName); 
 	            if(file.getSize()> DataStatus._FILESIZE_){
 	       		System.out.println("上传图片大小不允许超过1M");
 	       		m.put("message", "上传图片大小不允许超过1M");
 	       		return m;
 	            }
 	                int pre = (int) System.currentTimeMillis();  
 	                path = realPath + DataStatus._UPLOAD_ + fileDe;
 	                fileName = this.getUploadFileName(file.getOriginalFilename());
 	                //filePath = path  + "/" + fileName;
 	                filePath = DataStatus._UPLOAD_  +fileDe + File.separator +fileName;
 	                File f = new File(path);
 	                //如果文件夹不存在则创建    
 	                if(!f.exists() && !f.isDirectory()) {
 	                  f.mkdir();    
 	                }
 	                try {  
 	                	file.transferTo(new File(path + "/" + fileName));
 	                    int finaltime = (int) System.currentTimeMillis();  
 	                    System.out.println("上传3共耗时：" + (finaltime - pre) + "毫秒");  
 	                }catch (FileNotFoundException e) {
 	                    e.printStackTrace();
 	                }catch (IOException e) {  
 	                    e.printStackTrace();  
 	                }  
    	}
        System.out.println("上传成功"); 
        m.put("fileUrl", filePath);
        m.put("realName", realName);
        return m;  
    }
    
    public  String getUploadFileName(String fileName) {
  		String tempFile = fileName.substring(fileName.lastIndexOf(".")+1);
  		return UUIDGenerator.getUUID32Bit() + "." + tempFile;
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

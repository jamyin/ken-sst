package com.tianfang.home.controller;

import com.alibaba.fastjson.JSON;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.constants.SessionConstants;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.*;
import com.tianfang.home.dto.AppGroupDatas;
import com.tianfang.home.dto.MatchesDto;
import com.tianfang.home.utils.QRCodeUtil;
import com.tianfang.home.utils.TigaseUtil;
import com.tianfang.train.dto.CompetitionTeamDto;
import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.dto.TeamPlayerDto;
import com.tianfang.train.service.ICompetitionTeamService;
import com.tianfang.train.service.ITeamPlayerService;
import com.tianfang.user.app.FriendApp;
import com.tianfang.user.dto.*;
import com.tianfang.user.service.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * <p>Title: UserController </p>
 * <p>Description: 类描述:与用户操作相关接口</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang
 * @date 2016年1月22日上午9:50:02
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping(value = "user")
public class UserController extends BaseController{
	protected static final Log logger = LogFactory.getLog(UserController.class);
	private static final String Y_M_D_H_M = "yyyy-MM-dd HH:mm";

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private IUserService userService;	
	@Autowired
	private IPlanService planService;
	@Autowired
	private IMemoService memoService;
	@Autowired
	private IGroupService groupService;
	@Autowired
	private IUserFriendService userFriendService;
	@Autowired
	private ICompetitionTeamService iCompetitionTeamService;
    @Autowired
    private IUserInfoService userInfoService;
	@Autowired
	private ITeamPlayerService playerService;
	
	/**
	 * 用户注册
	 * @param session
	 * @param dto  注册用户数据封装
	 * @param code	验证码
	 * @return {"status":状态码(200-成功,500-失败),"message":"提示信息","data":UserDto(用户数据对象封装)}
	 * @author xiang_wang xx ee
	 * 2016年1月18日下午4:23:51
	 */
	@RequestMapping(value = "register")
	@ResponseBody
	public Response<String> register(HttpSession session, UserDto dto, @RequestParam(value = "code", required = false) String code) {
		Response<String> result = new Response<String>();

		String key = SMSController.SST_PHONE_NUMBER + dto.getMobile();
		
		result = checkCode(code, result, key);
		if(result.getStatus()==DataStatus.HTTP_FAILE){
			return result;
		}
//		if (checkCode(code, result, key) == 0){
//			return result;
//		}
		try {
			if (null != userService.checkMobile(dto.getMobile())) {
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("手机号码已经注册过！");
				return result;
			}
			dto.setNickName( "juju_" + RandomCode.getEnRandomCode(1)
			        + RandomCode.getRandomCodeByNumber(4)); 		//设置昵称
			String id = userService.save(dto);
			if (StringUtils.isBlank(id)) {
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("注册失败！");
				return result;
			} else {
				if (TigaseUtil.registered(dto.getMobile(), dto.getPassword())){
					UserDto user = userService.getUserById(id);
					// 生成二维码
					user.setQrcode(QRCodeUtil.createCode(user.getId()));
					userService.update(user);
					session.setAttribute(SessionConstants.LOGIN_USER_INFO, user);
					if(user != null){
						redisTemplate.opsForValue().set(DataStatus.SST_USER+id, user);
					}
					result.setData(user.getId());
					result.setStatus(DataStatus.HTTP_SUCCESS);
					result.setMessage("恭喜您注册成功！");
					return result;
				}else{
					result.setStatus(DataStatus.HTTP_FAILE);
					result.setMessage("对不起.注册失败！");
					return result;
				}
			}
		} catch (Exception e) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage(e.getMessage());
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 获取用户验证码图片地址
	 * @param userId
	 * @return
	 * @author xiang_wang
	 * 2016年3月8日上午11:46:36
	 */
	@RequestMapping(value = "qrcode")
	@ResponseBody
	public Response<String> qrcode(String userId){
		UserDto user = getUserByCache(userId);
    	Response<String> result = new Response<String>();
    	if (null != user){
			try {
				if (StringUtils.isBlank(user.getQrcode())){
					user.setQrcode(QRCodeUtil.createCode(userId));
					userService.update(user);
				}
				result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setData(user.getQrcode());
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
	    		result.setMessage("系统异常");
			}
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
       
        return result;
	}
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @param dto 用户数据封装
	 * @param session
	 * @return {"status":状态码(200-成功,500-失败),"message":"提示信息"}
	 * @author xiang_wang
	 * 2016年1月18日下午4:42:07
	 */
	@RequestMapping(value = "login")
	@ResponseBody
	public Response<String> login(HttpServletRequest request,
			HttpServletResponse response, UserDto dto,
			HttpSession session) {
		Response<String> result = new Response<String>();
		if ((StringUtils.isBlank(dto.getMobile()))){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("输入的用户帐户(手机)为空！");
			return result;
		}
		if (StringUtils.isBlank(dto.getPassword())){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("输入的用户密码为空！");
			return result;
		}

		UserDto user = null;
		try {
			user = userService.checkUser(dto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("系统繁忙~");
			return result;
		}
		//更新用户最后登录时间
		if (null == user){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("用户名或密码不正确,请重新输入！");
			return result;
		}

		//登录是检查用户是否存在
		Object object = redisTemplate.opsForValue().get(DataStatus.TIGASEACCOUNT+dto.getMobile());
		if(Objects.equals(object, null)){
			checkTigaseUser(dto);	
		}
		
		session.setAttribute(SessionConstants.LOGIN_USER_INFO, user);
		if(user != null){
			redisTemplate.opsForValue().set(DataStatus.SST_USER+user.getId(), user);
		}
		result.setData(user.getId());
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setMessage("用户登录成功！");
		return result;
	}
	
	/*
	 * 登录是检查用户是否存在 
	 */
	@Async
	private void checkTigaseUser(UserDto dto){
		if(!TigaseUtil.getUserByAccount(dto.getMobile())){
			TigaseUtil.registered(dto.getMobile(), dto.getPassword());
		};
		redisTemplate.opsForValue().set(DataStatus.TIGASEACCOUNT+dto.getMobile(), DataStatus.ENABLED);
	}

	/**
	 * 用户重置密码接口
	 * 
	 * @param session
	 * @param mobile
	 * @param code
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "resetPassword")
	@ResponseBody
	public Response<String> resetPassword(HttpSession session, String mobile, @RequestParam(value = "code", required = false) String code, String password) {
		Response<String> result = new Response<String>();

		if (StringUtils.isBlank(password)){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("密码不能为空！");
			return result;
		}
		String key = SMSController.SST_PHONE_NUMBER + mobile;
		result = checkCode(code, result, key);
		if(result.getStatus()==DataStatus.HTTP_FAILE){
			return result;
		}
//		if (checkCode(code, result, key) == 0){
//			return result;
//		};
		try {
			UserDto dto = new UserDto();
			dto.setMobile(mobile);
			List<UserDto> users = userService.findUserByParam(dto);
			if (null == users || users.size() == 0) {
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("手机号码未注册！");
				return result;
			}
			UserDto user = users.get(0);
			user.setPassword(password);
			int size = userService.update(user);
			if (size > 0) {
				if (TigaseUtil.resetPassword(user.getMobile(), user.getPassword())){
					session.setAttribute(SessionConstants.LOGIN_USER_INFO, user);
					if(user != null){
						redisTemplate.opsForValue().set(DataStatus.SST_USER+user.getId(), user);
					}
					result.setData(user.getId());
					result.setStatus(DataStatus.HTTP_SUCCESS);
					result.setMessage("恭喜您密码重置成功！");
					return result;
				}else{			
					result.setStatus(DataStatus.HTTP_FAILE);
					result.setMessage("对不起密码重置失败！");
					return result;
				}
			} else {
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("对不起密码重置失败！");
				return result;
			}
		} catch (Exception e) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage(e.getMessage());
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * 更换手机号码接口
	 * 
	 * @param session
	 * @param mobile 新手机号码
	 * @param oldMobile	原手机号码
	 * @param code	新手机短信验证码
	 * @param password	原手机密码
	 * @return
	 */
	@RequestMapping(value = "changeMobile")
	@ResponseBody
	public Response<String> changeMobile(HttpSession session, String mobile, String oldMobile, @RequestParam(value = "code", required = false) String code, String password) {
		Response<String> result = new Response<String>();

		if (StringUtils.isBlank(password)){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("密码不能为空！");
			return result;
		}
		String key = SMSController.SST_PHONE_NUMBER + mobile;
		result = checkCode(code, result, key);
		if(result.getStatus()==DataStatus.HTTP_FAILE){
			return result;
		}
//		if (checkCode(code, result, key) == 0){
//			return result;
//		};
		try {
			UserDto dto = new UserDto();
			dto.setMobile(oldMobile);
			List<UserDto> users = userService.findUserByParam(dto);
			if (null == users || users.size() == 0) {
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("原手机号码未注册！");
				return result;
			}
			dto.setMobile(mobile);
			users = userService.findUserByParam(dto);
			if (null != users && users.size() > 0) {
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("新手机号码已注册！");
				return result;
			}
			UserDto user = users.get(0);
			if (!StringUtils.equals(user.getPassword(), password)){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("密码输入错误！");
				return result;
			}
			user.setMobile(mobile);
			user.setPassword(password);
			int size = userService.update(user);
			if (size > 0) {
				if (TigaseUtil.registered(mobile, password)){
					session.setAttribute(SessionConstants.LOGIN_USER_INFO, user);
					if(user != null){
						redisTemplate.opsForValue().set(DataStatus.SST_USER+user.getId(), user);
					}
					result.setData(user.getId());
					result.setStatus(DataStatus.HTTP_SUCCESS);
					result.setMessage("恭喜您更换手机号成功！");
					return result;
				}else{
					user.setMobile(oldMobile);
					userService.update(user);
					result.setStatus(DataStatus.HTTP_FAILE);
					result.setMessage("对不起更换手机号失败！");
					return result;
				}
			} else {
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("对不起更换手机号失败！");
				return result;
			}
		} catch (Exception e) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage(e.getMessage());
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * @author YIn
	 * @time:2016年2月3日 上午9:46:24
	 * @param userDto
	 * @return
	 */
    @ResponseBody
    @RequestMapping("/edit") 
    public Response<String> edit(UserDto userDto,@RequestParam(value = "file",required = false)  MultipartFile file,
			HttpServletRequest request,HttpServletResponse response){
     Response<String> result = new Response<String>();
     if(userDto == null ){
    	 result.setStatus(DataStatus.HTTP_FAILE);
		 result.setMessage("用户信息为空");
		 return result;
     }
   	int stat = 0;
	try {
        if (file != null) {
//          Response<UploadDto> res = uploadPic(myfile, request, response);
        	Map<String, String> map = uploadImages(file , request);
        	userDto.setPic(map.get("fileUrl"));
        }
		stat = userService.update(userDto);
	} catch (Exception e) {
		e.printStackTrace();
	}
   	 if(stat == 0){
   		 result.setStatus(DataStatus.HTTP_FAILE);
		 result.setMessage("修改用户信息失败");
   	 }else{
   		 result.setStatus(DataStatus.HTTP_SUCCESS);
		 result.setMessage("修改用户信息成功");
   	 }
   	return result;
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
//	                filePath = path  + "/" + fileName;
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
        System.out.println("上传成功4"); 
        m.put("fileUrl", filePath);
        m.put("realName", realName);
        return m;  
    }
    
    public  String getUploadFileName(String fileName) {
  		String tempFile = fileName.substring(fileName.lastIndexOf(".")+1);
  		return UUIDGenerator.getUUID32Bit() + "." + tempFile;
  	}
    
	/**
	 * 根据条件查询用户信息接口
	 * @author YIn
	 * @time:2016年3月3日 下午3:36:45
	 * @param userDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findUser")
	public Response<UserDto> findUser(UserDto userDto) {
		Response<UserDto> result = new Response<UserDto>();
		if (userDto == null) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("查询条件为空");
			return result;
		}
		try {
			List<UserDto> list = userService.findUserByParam(userDto);
			if(list != null && list.size() > 0){
				TeamPlayerDto player = playerService.getTeamPlayeByUserId(list.get(0).getId());
				if (null != player){
					list.get(0).setTeamId(player.getTeamId());
				}else{
					list.get(0).setTeamId(null);
				}
				result.setData(list.get(0));
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("系统异常!");
		}

		result.setStatus(DataStatus.HTTP_FAILE);
		result.setMessage("没有查到此用户");
		return result;

	}
    
	/**
	 * 异步校验用户登录状态
	 * @return{"status":状态码(200-成功,500-失败),"message":"提示信息","data":UserDto(用户数据对象封装)}
	 * @author xiang_wang
	 * 2016年1月18日下午4:42:27
	 */
	@RequestMapping(value = "checkUser")
	@ResponseBody	
	public Response<UserDto> checkUser(){
		Response<UserDto> data = new Response<UserDto>();
		if(StringUtils.isEmpty(getSessionUserId())){
			data.setStatus(DataStatus.HTTP_FAILE);
			data.setMessage("用户不存在");
			return data;
		}
		UserDto dto = null;
		try {
			dto = userService.getUserById(getSessionUserId());
		} catch (Exception e) {
			e.printStackTrace();
			data.setStatus(DataStatus.HTTP_FAILE);
			data.setMessage("系统异常");
			logger.error(e.getMessage());
			return data;
		}
		data.setStatus(DataStatus.HTTP_SUCCESS);
		data.setData(dto);
		return data;
	}
	
	/**
	 * 用户退出操作
	 * @param session
	 * @param request
	 * @param response
	 * @return {"status":状态码(200-成功,500-失败),"message":"提示信息"}
	 * @author xiang_wang
	 * 2016年1月22日上午9:56:20
	 */
	@RequestMapping(value = "loginOut")
	@ResponseBody	
	public Response<String> loginout(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		session.removeAttribute(SessionConstants.LOGIN_USER_INFO);
		Response<String> result = new Response<String>();
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setMessage("用户退出成功!");
		return result;
	}
	
    /**
     * 移动端查询用户赛事好友
     * @return {"status":状态码(200-成功,500-失败),"message":"提示信息","data":List<FriendApp>}
     * @author xiang_wang
     * 2016年1月19日上午10:13:04
     */
    @RequestMapping(value="friend/list")
    @ResponseBody
    public Response<List<FriendApp>> findFriends(String userId) {
    	UserDto dto = getUserByCache(userId);
    	Response<List<FriendApp>> result = new Response<List<FriendApp>>();
    	if (null != dto){
			try {
				result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setData(userService.findFriendsByUserId(dto.getId()));
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
	    		result.setMessage("系统异常");
			}
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
       
        return result;
    }
    
    /**
     * 添加好友
     * @param userId
     * @param friendId
     * @return
     * @author xiang_wang
     * 2016年3月3日下午3:24:56
     */
    @RequestMapping(value="friend/add")
    @ResponseBody
    public Response<String> appendFriends(String userId, String friendId) {
    	UserDto user = getUserByCache(userId);
    	Response<String> result = new Response<String>();
    	if (null != user){
			try {

				UserFriendDto uf = new UserFriendDto();
                uf.setUserId(userId);
                uf.setFriendId(friendId);
				List<UserFriendDto> userFriend = userFriendService.findUserFriendByParam(uf);
				if (null != userFriend && userFriend.size() > 0){
					result.setStatus(DataStatus.HTTP_FAILE);
					result.setMessage("好友已添加!");
					return result;
				}

				userFriendService.save(uf);
				result.setMessage("添加成功");
				result.setStatus(DataStatus.HTTP_SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
	    		result.setMessage("系统异常");
			}
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
       
        return result;
    }
    
    /**
     * 添加好友关心
     * @param userId
     * @param id
     * @return
     * @author xiang_wang
     * 2016年3月3日下午3:36:46
     */
    @RequestMapping(value="friend/care")
    @ResponseBody
    public Response<String> careFriends(String userId, String id) {
    	UserDto user = getUserByCache(userId);
    	Response<String> result = new Response<String>();
    	if (null != user){
			try {
				UserFriendDto uf = userFriendService.getUserFriendById(id);
				if (null != uf){
					if (null == uf.getCare() || uf.getCare().intValue() == DataStatus.DISABLED){
						uf.setCare(DataStatus.ENABLED);
						result.setMessage("已关心");
					}else{
						uf.setCare(DataStatus.DISABLED);
						result.setMessage("已取消");
					}
				}
				userFriendService.update(uf);
				result.setStatus(DataStatus.HTTP_SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
	    		result.setMessage("系统异常");
			}
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
       
        return result;
    }
    
    
    /**
     * 获取日期用户训练计划
     * @param planTimeStr 查询时间
     * @return {"status":状态码(200-成功,500-失败),"message":"提示信息","data":List<PlanDto>}
     * @author xiang_wang
     * 2016年1月19日上午10:28:38
     */
    @RequestMapping(value="plan/byDate")
    @ResponseBody
    public Response<List<PlanDto>> findPlansByDate(String planTimeStr, String userId){
    	UserDto dto = getUserByCache(userId);
    	Response<List<PlanDto>> result = new Response<List<PlanDto>>();
    	if (null != dto){
    		try {
    			result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setData(planService.findPlanByUserId(dto.getId(), planTimeStr));
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
	    		result.setMessage("系统异常");
			}	
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
    	
    	return result;
    }
    
    /**
     * 根据参数查询用户训练计划
     * @param dto
     * @param query
     * @return
     * @author xiang_wang
     * 2016年2月3日下午1:33:45
     */
    @RequestMapping(value="plan/byParams")
    @ResponseBody
    public Response<PageResult<PlanDto>> findPlansByParams(PlanDto dto, PageQuery query, String userId){
    	UserDto user = getUserByCache(userId);
    	Response<PageResult<PlanDto>> result = new Response<PageResult<PlanDto>>();
    	if (null != user){
    		try {
    			dto.setUserId(user.getId());
    			PageResult<PlanDto> datas = planService.findPlanByParam(dto, query);
    			result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setData(datas);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
	    		result.setMessage("系统异常");
			}	
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
    	
    	return result;
    }
    
    /**
     * 用户训练计划详情
     * @param id
     * @return
     * @author xiang_wang
     * 2016年2月3日上午11:35:34
     */
    @RequestMapping(value="plan/detail")
    @ResponseBody
    public Response<PlanDto> getPlan(String id, String userId){
    	UserDto user = getUserByCache(userId);
    	Response<PlanDto> result = new Response<PlanDto>();
    	if (null != user){
	    	try {
				result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setData(planService.getPlanById(id));
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
	    		result.setMessage("系统异常");
			}	
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
    	return result;
    }
    
    /**
     * 新增用户训练计划
     * @param dto
     * @return
     * @author xiang_wang
     * 2016年2月3日上午11:43:12
     */
    @RequestMapping(value="plan/add")
    @ResponseBody
    public Response<String> addPlan(PlanDto dto, String userId){
    	UserDto user = getUserByCache(userId);
    	Response<String> result = new Response<String>();
    	if (null != user){
    		try {
    			dto.setUserId(user.getId());
    			dto.setUserName(user.getNickName());
    			if (StringUtils.isBlank(dto.getPlanTimeStr())){
    				result.setStatus(DataStatus.HTTP_FAILE);
    	    		result.setMessage("时间参数异常!");
    	    		return result;
    			}
    			Date planTime = DateUtils.parse(dto.getPlanTimeStr(), Y_M_D_H_M);
    			dto.setPlanTime(planTime);
    			String id = planService.save(dto);
    			result.setStatus(DataStatus.HTTP_SUCCESS);
    			result.setData(id);
    			result.setMessage("添加成功!");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
	    		result.setMessage("系统异常");
			}	
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
    	
    	return result;
    }
    
    /**
     * 更新用户训练计划
     * @param dto
     * @return
     * @author xiang_wang
     * 2016年2月3日上午11:43:12
     */
    @RequestMapping(value="plan/update")
    @ResponseBody
    public Response<String> updatePlan(PlanDto dto, String userId){
    	UserDto user = getUserByCache(userId);
    	Response<String> result = new Response<String>();
    	if (null != user){
    		try {
    			if (StringUtils.isBlank(dto.getId())){
    				result.setStatus(DataStatus.HTTP_FAILE);
    	    		result.setMessage("参数异常!");
    	    		return result;
    			}
    			dto.setUserId(user.getId());
    			dto.setUserName(user.getNickName());
    			if (StringUtils.isNotBlank(dto.getPlanTimeStr())){
    	    		Date planTime = DateUtils.parse(dto.getPlanTimeStr(), Y_M_D_H_M);
    	    		dto.setPlanTime(planTime);
    			}
    			planService.update(dto);
    			result.setStatus(DataStatus.HTTP_SUCCESS);
    			result.setMessage("更新成功!");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
	    		result.setMessage("系统异常");
			}	
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
    	
    	return result;
    }
    
    /**
     * 删除用户训练计划
     * @param id
     * @return
     * @author xiang_wang
     * 2016年2月3日上午11:56:19
     */
    @RequestMapping(value="plan/del")
    @ResponseBody
    public Response<String> delPlan(String id, String userId){
    	UserDto user = getUserByCache(userId);
    	Response<String> result = new Response<String>();
    	if (null != user){
    		try {
    			if (StringUtils.isBlank(id)){
    				result.setStatus(DataStatus.HTTP_FAILE);
    	    		result.setMessage("参数异常!");
    	    		return result;
    			}
    			planService.del(id);
    			result.setStatus(DataStatus.HTTP_SUCCESS);
    			result.setMessage("删除成功!");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
	    		result.setMessage("系统异常");
			}	
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
    	
    	return result;
    }
    
    /**
     * 根据参数清空所有的训练计划
     * @param isFinish
     * @param year
     * @return
     * @author xiang_wang
     * 2016年2月3日下午2:42:06
     */
    @RequestMapping(value="plan/delAll")
    @ResponseBody
    public Response<String> delAllPlan(Integer isFinish, String year, String userId){
    	UserDto user = getUserByCache(userId);
    	Response<String> result = new Response<String>();
    	if (null != user){
    		try {
    			PlanDto dto = new PlanDto();
    			dto.setUserId(user.getId());
    			if (null != isFinish){
    				dto.setIsFinish(isFinish);	
    			}
    			if (StringUtils.isNotBlank(year)){
    				dto.setYear(year);
    			}
    			planService.delAll(dto);
    			result.setStatus(DataStatus.HTTP_SUCCESS);
    			result.setMessage("删除成功!");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
	    		result.setMessage("系统异常");
			}	
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
    	
    	return result;
    }
    
    
    /**
     * 查询用户备忘录
     * @return {"status":状态码(200-成功,500-失败),"message":"提示信息","data":List<MemoDto>}
     * @author xiang_wang
     * 2016年1月19日上午10:36:51
     */
    @RequestMapping(value="findMemos")
    @ResponseBody
    public Response<List<MemoDto>> findMemos(String userId){
    	UserDto user = getUserByCache(userId);
    	Response<List<MemoDto>> result = new Response<List<MemoDto>>();
    	if (null != user){
    		try {
    			result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setData(memoService.findMemoByUserId(user.getId()));
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
	    		result.setMessage("系统异常");
			}	
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
    	
    	return result;
    }
    
    /**
     * 查询用户备忘录 -分页
     * @author YIn
     * @time:2016年2月18日 下午4:49:36
     * @param dto
     * @param page
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "list")
	@ResponseBody
    public Response<PageResult<MemoDto>> findpage(MemoDto dto, ExtPageQuery page) throws Exception {
		Response<PageResult<MemoDto>> result = new Response<PageResult<MemoDto>>();
		UserDto user = getUserByCache(dto.getUserId());
		if (null != user){
			PageResult<MemoDto> data = memoService.findMemoByParam(dto, page.changeToPageQuery());
			if(data != null){
				result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setMessage("查询成功");
				result.setData(data);
			}else{
				result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setMessage("无数据");
			}
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
        return result;
    }
    
    /**
     * 增加用户备忘录
     * @author YIn
     * @time:2016年2月3日 下午3:31:58
     * @return
     */
    @RequestMapping(value="addMemos")
    @ResponseBody
    public Response<String> addMemos(MemoDto memoDto){
    	Response<String> result = new Response<String>();
    	UserDto user = getUserByCache(memoDto.getUserId());
    	String id = "";
    	if (null != user){
    		memoDto.setUserName(user.getNickName());
    		try {
				id = memoService.save(memoDto);
			} catch (Exception e) {
				e.printStackTrace();
			}
    		if(id == ""){
    			result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("增加失败");
    		}else{
    			result.setStatus(DataStatus.HTTP_SUCCESS);
    			result.setData(id);
				result.setMessage("增加成功");
    		}
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
    	return result;
    }
    
    /**
     * 修改用户备忘录
     * @author YIn
     * @time:2016年2月3日 下午3:31:58
     * @return
     */
    @RequestMapping(value="updateMemos")
    @ResponseBody
    public Response<String> updateMemos(MemoDto memoDto){
    	Response<String> result = new Response<String>();
    	UserDto user = getUserByCache(memoDto.getUserId());
    	if (null != user){
    		try {
				memoService.update(memoDto);
				result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setMessage("更新成功");
			} catch (Exception e) {
				e.printStackTrace();
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("更新失败");
			}
    			
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
    	return result;
    }
    
    /**
     * 删除户备忘录
     * @author YIn
     * @time:2016年2月3日 下午3:31:58
     * @return
     */
    @RequestMapping(value="delMemos")
    @ResponseBody
    public Response<String> delMemos(String id, String userId){
    	Response<String> result = new Response<String>();
    	UserDto user = getUserByCache(userId);
    	if (null != user){
    		try {
				memoService.del(id);
				result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setMessage("删除成功");
			} catch (Exception e) {
				e.printStackTrace();
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("删除失败");
			}
    			
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
    	return result;
    }

	/**
	 * 创建群组接口
	 * @param jsonString
	 * @author xiang_wang
     * @return
     */
	@RequestMapping(value ="createGroup")
	@ResponseBody
	public Response<String> createGroup(String jsonString){
		AppGroupDatas datas = JSON.parseObject(jsonString, AppGroupDatas.class);
		Response<String> result = new Response<String>();
		UserDto user = getUserByCache(datas.getUserId());
		if (null == datas.getFriendIds() || datas.getFriendIds().length == 0){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("未选择好友!");
			return result;
		}
		if (null != user){
			try {
				String groupId = UUIDGenerator.getUUID();
				List<GroupUserDto> gus = new ArrayList<>(datas.getFriendIds().length);
				String name = getGroupName(datas.getFriendIds(), user, gus, groupId);

				GroupDto dto = new GroupDto();
				dto.setId(groupId);
				dto.setStat(DataStatus.ENABLED);
				dto.setCreateTime(new Date());
				dto.setCreateUserId(user.getId());
				dto.setCreateUserName(user.getNickName());
				dto.setName(name);

				groupService.save(dto, gus);
				result.setMessage("创建成功!");
				result.setStatus(DataStatus.HTTP_SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("系统异常");
			}
		}else{
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("用户不存在");
		}

		return result;
	}

	/**
     * 我的群组
     * @return {"status":状态码(200-成功,500-失败),"message":"提示信息","data":List<GroupDto>}
     * @author xiang_wang
     * 2016年1月20日下午1:10:38
     */
    @RequestMapping(value="myGroups")
    @ResponseBody
    public Response<List<GroupDto>> myGroups(String userId){
    	UserDto user = getUserByCache(userId);
    	Response<List<GroupDto>> result = new Response<>();
    	if (null != user){
    		try {
    			result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setData(groupService.findGroupByUserId(userId));
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
	    		result.setMessage("系统异常");
			}	
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
    	
    	return result;
    }
    
    /**
     * 根据群组Id查询用户信息 
     * @author YIn
     * @time:2016年3月10日 下午4:45:57
     * @param groupId
     * @return
     */
    @RequestMapping(value="findUserByGroupId")
    @ResponseBody
    public List<String> findUserByGroupId(String groupId ,String mobile){
    	List<String> result = new ArrayList<String>();
    	if(StringUtils.isEmpty(groupId)){
    		return result;
    	}
    	List<UserDto> userList = userService.findUserByGroupId(groupId);
    	if(userList == null || userList.size() == 0){
    		return null;
    	}
    	List<String> mobileList = new ArrayList<String>();
    	for(UserDto dto:userList){
    		 if(StringUtils.isNotEmpty(mobile) && !mobile.equals(dto.getMobile())){
    			 mobileList.add(dto.getMobile());
    		 }
    	}
    	return mobileList;
    }
    
    /**
     * 根据群组Id查询用户信息  -加后缀
     * @author YIn
     * @time:2016年3月10日 下午4:45:57
     * @param groupId
     * @return
     */
    @RequestMapping(value="findUsersByGroupId")
    @ResponseBody
    public List<String> findUsersByGroupId(String groupId ,String mobile){
    	List<String> result = new ArrayList<String>();
    	if(StringUtils.isEmpty(groupId)){
    		return result;
    	}
    	List<UserDto> userList = userService.findUserByGroupId(groupId);
    	if(userList == null || userList.size() == 0){
    		return null;
    	}
    	List<String> mobileList = new ArrayList<String>();
    	for(UserDto dto:userList){
    		 if(StringUtils.isNotEmpty(mobile) && !mobile.equals(dto.getMobile())){
    			 mobileList.add(dto.getMobile() + "#" + PropertiesUtils.getProperty("project"));
    		 }
    	}
    	return mobileList;
    }
    
    /**
     * 查询群组下所有用户信息
     * @param userId
     * @param id
     * @return
     * @author xiang_wang
     * 2016年3月4日下午5:18:29
     */
    @RequestMapping(value="getGroup")
    @ResponseBody
    public Response<GroupDto> getGroup(String userId, String id){
    	UserDto user = getUserByCache(userId);
    	Response<GroupDto> result = new Response<GroupDto>();
    	if (null != user){
    		try {
    			result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setData(groupService.getGroupById(id, true));
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
	    		result.setMessage("系统异常");
			}	
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
    	
    	return result;
    }
    
    /**
     * 我的特别关注
     * @return {"status":状态码(200-成功,500-失败),"message":"提示信息","data":List<FriendApp>}
     * @author xiang_wang
     * 2016年1月20日下午1:47:19
     */
    @RequestMapping(value="myCares")
    @ResponseBody
    public Response<List<FriendApp>> myCares(String userId){
    	UserDto user = getUserByCache(userId);
    	Response<List<FriendApp>> result = new Response<List<FriendApp>>();
    	if (null != user){
    		try {
    			result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setData(userService.findCareFriends(userId));
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
	    		result.setMessage("系统异常");
			}	
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
    	
    	return result;
    }
    
    /**
     * 联盟通讯录 按照赛事分组
     * @return {"status":状态码(200-成功,500-失败),"message":"提示信息","data":List<TeamDto>}
     * @author xiang_wang
     * 2016年1月20日下午2:10:25
     */
    @RequestMapping(value="raceTeam")
    @ResponseBody
    public Response<Map<String, Object>> raceTeam(String userId){
    	UserDto user = getUserByCache(userId);
		Response<Map<String, Object>> result = new Response<Map<String, Object>>();
    	if (null != user){
    		List<CompetitionTeamDto> dataList = iCompetitionTeamService.selectCompeTeamList(userId);
    		    		
    		HashMap<String,List<TeamDto>> mapObj = changeListToMap(dataList);
    		
    		List<MatchesDto> resultList = changeMapToList(mapObj);
    		
    		try {
    			result.setStatus(DataStatus.HTTP_SUCCESS);

				Map<String, Object> map = new HashMap<>(2);
				map.put("teams", resultList);
				TeamPlayerDto player = playerService.getTeamPlayeByUserId(userId);
				if (null != player){
					map.put("myTeam", player);
				}
				result.setData(map);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
	    		result.setMessage("系统异常");
			}	
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
    	return result;
    }
    
    private List<MatchesDto> changeMapToList(HashMap<String,List<TeamDto>> mapSList){
    	List<MatchesDto> objList = new ArrayList<MatchesDto>();
    	for (Map.Entry<String,List<TeamDto>> entry : mapSList.entrySet()) {
    		MatchesDto matchDto = new MatchesDto();
    		matchDto.setTitle(entry.getKey());
    		matchDto.setList(entry.getValue());
    		objList.add(matchDto);
    	}
    	return objList;
    }
    
    private HashMap<String,List<TeamDto>> changeListToMap(List<CompetitionTeamDto> dataList){
    	HashMap<String,List<TeamDto>> mapObj = new HashMap<String, List<TeamDto>>();
    	List<TeamDto> teamDtoList = null;
		for(CompetitionTeamDto ctDto : dataList){
			TeamDto teamDto = new TeamDto();
			teamDto.setId(ctDto.getTeamId());teamDto.setName(ctDto.getTeamName());teamDto.setIcon(ctDto.getTeamIcon());
			
			if(mapObj.containsKey(ctDto.getCompName())){
				teamDtoList = mapObj.get(ctDto.getCompName());
			}else{
				teamDtoList = new ArrayList<TeamDto>();
			}
			teamDtoList.add(teamDto);
			mapObj.put(ctDto.getCompName(), teamDtoList);
		}
		return mapObj;
    	
    }
    
    /**
	 * 校验短信验证码
	 * 
	 * @param code
	 * @param result
	 * @param key
	 * @return
	 */
	private Response<String> checkCode(String code, Response<String> result, String key) {
		if(StringUtils.isNotBlank(code)){
			if (redisTemplate.opsForValue().get(key) == null) {
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("没有点击获取验证码！");
			}else{
				int randNum = (int) redisTemplate.opsForValue().get(key);
				if(!String.valueOf(randNum).equals(code)){
					result.setStatus(DataStatus.HTTP_FAILE);
					result.setMessage("手机验证码输入错误！");
				}else{
					result.setStatus(DataStatus.HTTP_SUCCESS);
				}
			} 			
		}else{
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("验证未输入！");
		}
//		if(StringUtils.isNotBlank(code)){
//			int num = 0;
//			try {
//				num = Integer.parseInt(code);
//			} catch (NumberFormatException e) {
//				e.printStackTrace();
//				result.setStatus(DataStatus.HTTP_FAILE);
//				result.setMessage("输入的短信验证码不是4位数字！");
//			}
//			if (redisTemplate.opsForValue().get(key) == null) {
//				result.setStatus(DataStatus.HTTP_FAILE);
//				result.setMessage("没有点击获取验证码！");
//			}else{
//				if(!redisTemplate.opsForValue().get(key).equals(num)){
//					result.setStatus(DataStatus.HTTP_FAILE);
//					result.setMessage("手机验证码输入错误！");
//					return 0;
//				}
//			} 
//			return num;
//		}else{
//			result.setStatus(DataStatus.HTTP_FAILE);
//			result.setMessage("验证未输入！");
//		}
		return result;
	}

	/**
	 * 组装群组名称,并组装群组下用户集合
	 * @param friendIds
	 * @param user
	 * @param gus
	 * @param groupId
	 * @return
	 */
	private String getGroupName(String[] friendIds, UserDto user, List<GroupUserDto> gus, String groupId) throws Exception {
		GroupUserDto gu;
		StringBuilder sb = new StringBuilder();
		for (int i = 0, len = friendIds.length; i < len; i++){
			if (i < 2){
				UserDto friend = userService.getUserById(friendIds[i]);
				if (null == friend){
					throw new RuntimeException("好友数据异常!");
				}
				sb.append(friend.getNickName()).append("、");
			}
			gu = new GroupUserDto();
			gu.setGroupId(groupId);
			gu.setUserId(friendIds[i]);
			gu.setCreateTime(new Date());
			gu.setStat(DataStatus.ENABLED);
			gu.setId(UUIDGenerator.getUUID());

			gus.add(gu);
		}
		gu = new GroupUserDto();
		gu.setGroupId(groupId);
		gu.setUserId(user.getId());
		gu.setCreateTime(new Date());
		gu.setStat(DataStatus.ENABLED);
		gu.setId(UUIDGenerator.getUUID());

		gus.add(gu);

		sb.append(user.getNickName());
		if (friendIds.length >= 2){
			sb.append("...");
		}
		return sb.toString();
	}
	
	
	/**
	 * 
		 * 此方法描述的是：我所属的群
		 * @author: cwftalus@163.com
		 * @version: 2016年3月15日 上午9:47:25
	 */
    @RequestMapping(value="belongGroups")
    @ResponseBody
    public Response<List<GroupDto>> myBelongGroups(String userId){
    	UserDto user = getUserByCache(userId);
    	Response<List<GroupDto>> result = new Response<>();
    	if (null != user){
    		try {
    			result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setData(groupService.findGroupByUserId(userId));
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
	    		result.setMessage("系统异常");
			}	
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
    	
    	return result;
    }
    
    /**
     * 查询用户赛事信息接口
     */
	@RequestMapping(value="findUserInfo")
	@ResponseBody
	public Response<UserInfoDto> findUserInfo(UserInfoDto userInfoDto){
		Response<UserInfoDto> data = new Response<UserInfoDto>();
		if(userInfoDto == null || StringUtils.isEmpty(userInfoDto.getUserId())){
			data.setMessage("查询参数为空");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
		List<UserInfoDto> result = userInfoService.findUserInfo(userInfoDto);
		if(result.size() > 0){
			data.setMessage("查询参赛信息成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
			data.setData(result.get(0));
		}else{
			data.setMessage("未查到此用户参赛信息");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		}	   	
		return data;
	}
    
	
	/**
	 * 增加用户参赛信息
	 * @author YIn
	 * @time:2016年4月9日 上午10:11:14
	 * @param userInfoDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="addUserInfo")
	public Response<String> addUserInfo(UserInfoDto userInfoDto ,@RequestParam(value = "file",required = false)  MultipartFile file
			,HttpServletRequest request){
		Response<String> data = new Response<String>();
		int flag = 0;
		try {
	        if (file != null) {
	        	Map<String, String> map = uploadImages(file , request);
	        	userInfoDto.setPhoto(map.get("fileUrl"));
	        }
	        flag = userInfoService.addUserInfo(userInfoDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(flag > 0){
			data.setMessage("添加参赛信息成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		}else{
			data.setMessage("添加参赛信息失败");
			data.setStatus(DataStatus.HTTP_FAILE);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id编辑用户参赛信息
	 * @author YIn
	  * @time:2016年4月9日 上午10:11:14
	 * @param userInfoDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateUserInfo")
	public Response<String> updateUserInfo(UserInfoDto userInfoDto,@RequestParam(value = "file",required = false)  MultipartFile file
			,HttpServletRequest request){
		Response<String> data = new Response<String>();
		if(userInfoDto == null || StringUtils.isEmpty(userInfoDto.getUserId())){
			data.setMessage("请求参数为空");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
		
		
		try {
	        if (file != null) {
	        	Map<String, String> map = uploadImages(file , request);
	        	userInfoDto.setPhoto(map.get("fileUrl"));
	        }
	        List<UserInfoDto> result = userInfoService.findUserInfo(userInfoDto);
	        if(result.size() <= 0){
	        	int addFlag = 0;
	 	        addFlag = userInfoService.addUserInfo(userInfoDto);
	 	        if(addFlag > 0){
	 				data.setMessage("新增赛事信息成功");
	 				data.setStatus(DataStatus.HTTP_SUCCESS);
	 			}else{
	 				data.setMessage("新增赛事信息失败");
	 				data.setStatus(DataStatus.HTTP_FAILE);
	 			}	
	        }else{
		        int editFlag = 0;
		        userInfoDto.setId(result.get(0).getId());
		        editFlag = userInfoService.updateUserInfo(userInfoDto);
		        if(editFlag > 0){
					data.setMessage("编辑赛事信息成功");
					data.setStatus(DataStatus.HTTP_SUCCESS);
				}else{
					data.setMessage("编辑赛事信息失败");
					data.setStatus(DataStatus.HTTP_FAILE);
				}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
}

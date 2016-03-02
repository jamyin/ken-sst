package com.tianfang.home.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.constants.SessionConstants;
import com.tianfang.common.digest.MD5Coder;
import com.tianfang.common.exception.SystemException;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.service.ITeamService;
import com.tianfang.user.app.FriendApp;
import com.tianfang.user.dto.GroupDto;
import com.tianfang.user.dto.LoginUserDto;
import com.tianfang.user.dto.MemoDto;
import com.tianfang.user.dto.PlanDto;
import com.tianfang.user.dto.UserDto;
import com.tianfang.user.service.IEmailSendService;
import com.tianfang.user.service.IGroupService;
import com.tianfang.user.service.IMemoService;
import com.tianfang.user.service.IPlanService;
import com.tianfang.user.service.ISmsSendService;
import com.tianfang.user.service.IUserService;

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
	private ISmsSendService smsSendService;
	@Autowired
	private IEmailSendService emailSendService;
	@Autowired
	private IPlanService planService;
	@Autowired
	private IMemoService memoService;
	@Autowired
	private IGroupService groupService;
	@Autowired
	private ITeamService teamService;
	
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
		/* 移动端将密码加密后,传给服务器
		 * String md5oldPwd;// 获取页面上输入的密码并加密校验
		try {
			md5oldPwd = MD5Coder.encodeMD5Hex(dto.getPassword());
			dto.setPassword(md5oldPwd);
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}*/
		String key = SMSController.SST_PHONE_NUMBER + dto.getMobile();
		if(!StringUtils.isBlank(code)){
			int num;
			try {
				num = Integer.parseInt(code);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("输入的短信验证码不是4位数字！");
				return result;
			}
			if (redisTemplate.opsForValue().get(key) == null) {
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("没有点击获取验证码！");
				return result;
			}if(!redisTemplate.opsForValue().get(key).equals(num)){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("手机验证码输入错误！");
				return result;
			}
		}
		try {
			if (null != userService.checkMobile(dto.getMobile())) {
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("手机号码已经注册过！");
				return result;
			}
			String id = userService.save(dto);
			if (StringUtils.isBlank(id)) {
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("注册失败！");
				return result;
			} else {
				UserDto user = userService.getUserById(id);
				LoginUserDto loginUserDto = new LoginUserDto(id, user.getNickName(), user.getPic(), user.getTeamId(), user.getMobile());
				session.setAttribute(SessionConstants.LOGIN_USER_INFO, loginUserDto);
				if(loginUserDto != null){
					redisTemplate.opsForValue().set(id, loginUserDto);
				}
				result.setData(user.getId());
				result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setMessage("恭喜您注册成功！");
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
		String md5oldPwd;// 获取页面上输入的密码并加密校验
		try {
			md5oldPwd = MD5Coder.encodeMD5Hex(dto.getPassword());
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}
		dto.setPassword(md5oldPwd);
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

		LoginUserDto loginUserDto = new LoginUserDto(user.getId(), user.getNickName(), user.getPic(), user.getTeamId(), user.getMobile());
		session.setAttribute(SessionConstants.LOGIN_USER_INFO, loginUserDto);
		if(user != null){
			redisTemplate.opsForValue().set(dto.getId(), loginUserDto);
		}
		result.setData(loginUserDto.getId());
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setMessage("用户登录成功！");
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
    public Response<String> edit(UserDto userDto){
     Response<String> result = new Response<String>();
     if(userDto == null ){
    	 result.setStatus(DataStatus.HTTP_FAILE);
		 result.setMessage("用户信息为空");
		 return result;
     }
   	int stat = 0;
	try {
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

	/**
	 * 异步校验用户登录状态
	 * @return{"status":状态码(200-成功,500-失败),"message":"提示信息","data":UserDto(用户数据对象封装)}
	 * @author xiang_wang
	 * 2016年1月18日下午4:42:27
	 */
	@RequestMapping(value = "checkUser")
	@ResponseBody	
	public Response<LoginUserDto> checkUser(){
		Response<LoginUserDto> data = new Response<LoginUserDto>();
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
		LoginUserDto userDto = new LoginUserDto(dto.getId(), dto.getNickName(), dto.getPic(), dto.getTeamId(), dto.getMobile());
		data.setStatus(DataStatus.HTTP_SUCCESS);
		data.setData(userDto);
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
     * @param session
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
     * 获取日期用户训练计划
     * @param date 查询时间
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
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("查询失败");
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
     * 我的群组
     * @return {"status":状态码(200-成功,500-失败),"message":"提示信息","data":List<GroupDto>}
     * @author xiang_wang
     * 2016年1月20日下午1:10:38
     */
    @RequestMapping(value="myGroups")
    @ResponseBody
    public Response<List<GroupDto>> myGroups(String userId){
    	UserDto user = getUserByCache(userId);
    	Response<List<GroupDto>> result = new Response<List<GroupDto>>();
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
     * 联盟通讯录
     * @return {"status":状态码(200-成功,500-失败),"message":"提示信息","data":List<TeamDto>}
     * @author xiang_wang
     * 2016年1月20日下午2:10:25
     */
    @RequestMapping(value="teamBook")
    @ResponseBody
    public Response<List<TeamDto>> teamBook(String userId){
    	UserDto user = getUserByCache(userId);
    	Response<List<TeamDto>> result = new Response<List<TeamDto>>();
    	if (null != user){
    		try {
    			result.setStatus(DataStatus.HTTP_SUCCESS);
    			List<TeamDto> list = teamService.findAll();
    			if (StringUtils.isNotBlank(user.getTeamId())){
    				for (TeamDto team : list){
        				if (user.getTeamId().equals(team.getId())){
        					team.setChecked(true);
        					break;
        				}
        			}
    			}
				result.setData(list);
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
}

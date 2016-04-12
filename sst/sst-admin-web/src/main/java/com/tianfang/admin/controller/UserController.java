package com.tianfang.admin.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.digest.MD5Coder;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.service.ITeamService;
import com.tianfang.user.dto.UserDto;
import com.tianfang.user.dto.UserInfoDto;
import com.tianfang.user.service.IUserInfoService;
import com.tianfang.user.service.IUserService;

@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController{
	protected static final Log logger = LogFactory.getLog(UserController.class);
	    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IUserInfoService userInfoService;
    
    @Autowired
    private ITeamService teamService;
    
    
    @RequestMapping(value = "list")
    public ModelAndView findpage(UserDto dto, ExtPageQuery page) throws Exception {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PageResult<UserDto> result = userService.findUserByParam(dto, page.changeToPageQuery());
        mv.setViewName("/user/list");
        mv.addObject("pageList", result);
        mv.addObject("params", dto);
        return mv;
    }
    
    @RequestMapping(value = "add")
    public ModelAndView add() {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        try {
            mv.setViewName("/user/add");
        } catch (Exception e) {
            logger.error("add 抛出异常:"+e.getMessage());
        }                       
        return mv;
    }
    
    @RequestMapping(value = "save")
    @ResponseBody
    public Map<String, Object> save(UserDto dto) throws Exception{
        if (StringUtils.isBlank(dto.getMobile()) || StringUtils.isBlank(dto.getPassword())) {
            return MessageResp.getMessage(false, "请求参数不能为空~");
        }
        String md5passwd = MD5Coder.encodeMD5Hex(dto.getPassword());
        dto.setPassword(md5passwd);
        Map<String, Object> result;
        try {
			userService.save(dto);
			result = MessageResp.getMessage(true, "新增成功~");
		} catch (Exception e) {
			result = MessageResp.getMessage(false, e.getMessage());
			e.printStackTrace();
			logger.error("save方法抛出异常:"+e.getMessage());
		}
        return result;
    }
    
    @RequestMapping(value = "edit")
    public ModelAndView edit(String id) throws Exception {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        UserDto dto = userService.getUserById(id);
        mv.setViewName("/user/edit");
        mv.addObject("datas", dto);
        return mv;
    }
    
    @RequestMapping(value = "update")
    @ResponseBody
    public Map<String, Object> update(UserDto dto) throws Exception{
		if (StringUtils.isBlank(dto.getId())) {
	        return MessageResp.getMessage(false, "主键ID为空~");
	    }
        Map<String, Object> result;
        try {
			userService.update(dto);
			result = MessageResp.getMessage(true, "编辑成功~");
		} catch (Exception e) {
			e.printStackTrace();
			result = MessageResp.getMessage(false, e.getMessage());
			logger.error("update方法抛出异常:"+e.getMessage());
		}
        return result;
    }
    
    @RequestMapping(value = "editPwd")
    public ModelAndView editPassword(String id) throws Exception {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        mv.setViewName("/user/editPwd");
        mv.addObject("id", id);
        return mv;
    }
    
    @RequestMapping(value = "resetPwd")
    @ResponseBody
    public Map<String, Object> resetPassword(UserDto dto) throws Exception{
        if (StringUtils.isBlank(dto.getId())) {
            return MessageResp.getMessage(false, "主键ID为空~");
        }
        if (StringUtils.isBlank(dto.getPassword())) {
        	 return MessageResp.getMessage(false, "密码为空~");
        }
        if (StringUtils.isNotBlank(dto.getPassword())) {
            String md5passwd = MD5Coder.encodeMD5Hex(dto.getPassword());
            dto.setPassword(md5passwd);
        }     
        Map<String, Object> result;
        try {
			userService.update(dto);
			result = MessageResp.getMessage(true, "密码修改成功~");
		} catch (Exception e) {
			e.printStackTrace();
			result = MessageResp.getMessage(false, e.getMessage());
			logger.error("resetPwd方法抛出异常:"+e.getMessage());
		}
        return result;
    }
    
    @RequestMapping(value = "delAdIds")
    @ResponseBody
    public Map<String, Object> delAdIds(String ids){
        if (StringUtils.isBlank(ids)) {
            return MessageResp.getMessage(false, "请求参数不能为空~");
        }
        Map<String, Object> result;
        try {
			userService.del(ids);
			result = MessageResp.getMessage(true, "删除成功~");
		} catch (Exception e) {
			e.printStackTrace();
			result = MessageResp.getMessage(false, e.getMessage());
		}
        return result;
    }
    
    /**
     * 打开球队关联
     * @param id
     * @return
     * @author xiang_wang
     * 2016年1月13日上午10:00:41
     * @throws Exception 
     */
    @RequestMapping(value = "team")
    public ModelAndView auth(String id, String teamId) throws Exception {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());   
        List<TeamDto> teamList = teamService.findAll();
        List<JsonTeam> jsonTeams = assemblyTreeNodes(teamId, teamList);
        String jsonStr = JSON.toJSONString(jsonTeams);
        logger.info("jsonStr="+jsonStr);
        mv.setViewName("/user/teams");
        mv.addObject("userId", id);
        mv.addObject("zTreeNodes", jsonStr);
        return mv;
    }
    
    @RequestMapping(value="joinTeam")
    @ResponseBody
    public Map<String, Object> joinTeam(String userId, String teamId) {
    	Map<String, Object> result;
        try {
			userService.joinTeam(userId, teamId);
			result = MessageResp.getMessage(true, "加入成功~");
		} catch (Exception e) {
			e.printStackTrace();
			result = MessageResp.getMessage(false, e.getMessage());
		}
        return result;
    }
    
	private List<JsonTeam> assemblyTreeNodes(String teamId, List<TeamDto> teamList) {
		if (null != teamList && teamList.size() > 0){
            List<JsonTeam> datas = new ArrayList<>(teamList.size());
            JsonTeam data;
        	for (TeamDto dto : teamList){
                data = new JsonTeam(dto.getId(), dto.getName());
                if (null != teamId && !"".equals(teamId.trim()) && dto.getId().equals(teamId)){
                    data.setChecked(true);
                }
                datas.add(data);
        	}
            return datas;
        }

        return null;
	}
	
	   /**
		 * 跳转至新增页面
		 * @author Yin
		 */
		@RequestMapping(value = "goUserInfoAdd")
		public ModelAndView goUserInfoAdd(HttpSession session) {
			logger.info("去用户参赛信息新增页面");
			ModelAndView mv = this.getModelAndView(this.getSessionUserId());
			List<UserDto> userList = userService.findUserByParam(new UserDto());
			mv.addObject("userList", userList);
			mv.setViewName("/userInfo/add");
			return mv;
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
		public Response<String> addUserInfo(UserInfoDto userInfoDto, HttpSession session){
			Response<String> data = new Response<String>();
			/*AdminDto admin = (AdminDto) session.getAttribute(Const.SESSION_USER);
			if(admin != null && StringUtils.isNotEmpty(admin.getId())){
				userInfoDto.setUserId(admin.getId());
			}*/
			if(StringUtils.isNotEmpty(userInfoDto.getUserId())){
				List<UserInfoDto> list = userInfoService.findUserInfo(userInfoDto);
				if(list != null && list.size() > 0){
					data.setMessage("此用户已经填写参赛信息");
					data.setStatus(DataStatus.HTTP_FAILE);
					return data;
				}
			}
			int flag = userInfoService.addUserInfo(userInfoDto);
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
		 * 跳转至编辑页面
		 * @return
		 */
		@RequestMapping(value = "goUserInfoEdit")
		public ModelAndView goUserInfoEdit(String userInfoId) {
			logger.info("去用户赛事信息修改页面");
			ModelAndView mv = this.getModelAndView(this.getSessionUserId());
			UserInfoDto userInfoDto = new UserInfoDto();
			userInfoDto.setId(userInfoId);
			List<UserInfoDto> list =userInfoService.findUserInfo(userInfoDto);
			List<UserDto> userList = userService.findUserByParam(new UserDto());
			mv.addObject("userList", userList);
			try {
				mv.setViewName("/userInfo/edit");
				mv.addObject("msg", "edit");
				mv.addObject("userInfoDto", list.get(0));
			} catch (Exception e) {
				logger.error(e.toString(), e);
			}						
			return mv;
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
		public Response<String> updateUserInfo(UserInfoDto userInfoDto){
			Response<String> data = new Response<String>();
			int flag = userInfoService.updateUserInfo(userInfoDto);
			if(flag > 0){
				data.setMessage("编辑赛事信息成功");
				data.setStatus(DataStatus.HTTP_SUCCESS);
			}else{
				data.setMessage("编辑赛事信息失败");
				data.setStatus(DataStatus.HTTP_FAILE);
			}	   	
			return data;
		}
		
		/**
		 * 根据主键Id删除 -逻辑删除用户参赛信息
		 * @author YIn
		 * @time:2016年4月9日 上午10:11:14
		 * @param userInfoDto
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value="delUserInfo")
		public Map<String, Object> delUserInfo(UserInfoDto userInfoDto){
			logger.info("UserInfoDto"+userInfoDto);
			userInfoDto.setStat(0); //逻辑删除
			int status = userInfoService.updateUserInfo(userInfoDto);
			if(status > 0){
				return MessageResp.getMessage(true,"删除赛事信息成功");
			}
				return MessageResp.getMessage(false,"删除赛事信息失败");
			
		}
		
		/**
		 * 批量删除用户参赛信息
		 * @author YIn
		 * @time:2016年4月9日 上午10:11:14
		 * @param ids
		 * @return
		 * @throws Exception
		 */
		@ResponseBody
		@RequestMapping(value="delUserInfoByIds")
	    public Map<String, Object> delUserInfoByIds(String  ids) throws Exception{
		    if (StringUtils.isEmpty(ids)) {
		        return MessageResp.getMessage(false, "请选择需要删除的项！");
		    }
		    Integer resObject =(Integer) userInfoService.delByIds(ids);
		    if (resObject == 0) {
	            return MessageResp.getMessage(false, "批量删除失败");
	        }
		    if (resObject == 1) {
	            return MessageResp.getMessage(true, "批量删除成功");
	        }
		    return MessageResp.getMessage(false, "删除异常");
		}
		
		@RequestMapping(value="findUserInfo")
		@ResponseBody
		public Response<List<UserInfoDto>> findUserInfo(UserInfoDto userInfoDto){
			Response<List<UserInfoDto>> data = new Response<List<UserInfoDto>>();
			
			List<UserInfoDto> result = userInfoService.findUserInfo(userInfoDto);
			if(result.size() > 0){
				data.setMessage("查询参赛信息成功");
				data.setStatus(DataStatus.HTTP_SUCCESS);
				data.setData(result);
			}else{
				data.setMessage("未查到此用户参赛信息");
				data.setStatus(DataStatus.HTTP_SUCCESS);
			}	   	
			return data;
		}
		
		/**
		 * 后台用户参赛信息显示页面-分页
		 * @author YIn
		 * @time:2016年4月9日 上午10:11:14
		 * @param userInfoDto
		 * @param page
		 * @return
		 */
		@RequestMapping(value = "findUserInfoView")
		public ModelAndView findUserInfoView(UserInfoDto userInfoDto, ExtPageQuery page){
			logger.info("userInfoDto  : " + userInfoDto);
			ModelAndView mv = this.getModelAndView(this.getSessionUserId());
			PageResult<UserInfoDto> result = userInfoService.findUserInfoViewByPage(userInfoDto, page.changeToPageQuery());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			if(result.getResults() != null && result.getResults().size() > 0){
				for(UserInfoDto dto:result.getResults()){
					if(dto.getCreateTime() != null){
					dto.setCreateTimeStr(sdf.format(dto.getCreateTime()));
					}
					if(dto.getLastUpdateTime() != null){
						dto.setLastUpdateTimeStr(sdf.format(dto.getLastUpdateTime()));
					}
				}
			}
			mv.addObject("pageList", result);
			mv.addObject("userInfoDto", userInfoDto);
			mv.setViewName("/userInfo/list");
			return mv;
		}
	
    @RequestMapping(value="auditObj")
    @ResponseBody
    public Map<String, Object> auditObj(String userId, Integer isAudit) {
    	Map<String, Object> result;
        try {
        	UserDto userDto = userService.getUserById(userId);
        	userDto.setAudit(isAudit);
        	userService.update(userDto);
			
			result = MessageResp.getMessage(true, "加入成功~");
		} catch (Exception e) {
			e.printStackTrace();
			result = MessageResp.getMessage(false, e.getMessage());
		}
        return result;
    }
	
}
class JsonTeam implements Serializable{

	private static final long serialVersionUID = -837711745585130582L;

	@Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String name;			// 球队名称

    @Getter
    @Setter
    private boolean checked = false; 		// 是否选中(用于用户球队关联操作树展示)

    public JsonTeam() {
    }

    public JsonTeam(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
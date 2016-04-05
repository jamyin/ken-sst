package com.tianfang.admin.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.tianfang.common.digest.MD5Coder;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.service.ITeamService;
import com.tianfang.user.dto.UserDto;
import com.tianfang.user.service.IUserService;

@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController{
	protected static final Log logger = LogFactory.getLog(UserController.class);
	    
    @Autowired
    private IUserService userService;
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
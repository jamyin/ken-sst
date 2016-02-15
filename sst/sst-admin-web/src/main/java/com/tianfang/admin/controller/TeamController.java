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
import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.service.ITeamService;

/**
 * 球队Controller
 * @author YIn
 * @time:2016年1月23日 上午11:28:36
 * @ClassName: TeamController
 * @
 */

@Controller
@RequestMapping("/team")
public class TeamController extends BaseController {

	@Autowired
	private ITeamService teamService;
	
	/**
	 * 跳转至新增页面
	 * @return
	 */
	@RequestMapping(value = "goAdd.do")
	public ModelAndView goAdd() {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		mv.setViewName("/team/team_add");
		return mv;
	}

	/**
	 * 增加球队
	 * @author YIn
	 * @time:2016年1月23日 上午11:29:12
	 * @param teamDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add")
	public Response<String> addTeam(TeamDto teamDto){
		Response<String> data = new Response<String>();
		AdminDto admin = getSessionAdmin();
		teamDto.setCreateUserId(admin.getId());
		teamDto.setCreateUserName(admin.getAccount());
		int flag = teamService.addTeam(teamDto);
		if(flag > 0){
			data.setMessage("添加球队成功");
			data.setStatus(200);
		}else{
			data.setMessage("添加球队失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 跳转至编辑页面
	 * @return
	 */
	@RequestMapping(value = "goEdit.do")
	public ModelAndView goEdit(String teamId) {
		logger.info("去球队修改页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		TeamDto teamDto = new TeamDto();
		teamDto.setId(teamId);
		List<TeamDto> list =teamService.findTeam(teamDto);
		try {
			mv.setViewName("/team/team_edit");
			mv.addObject("msg", "edit");
			mv.addObject("teamDto", list.get(0));
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 根据主键Id编辑球队
	 * @author YIn
	 * @time:2016年1月23日 上午11:30:11
	 * @param teamDto
	 * @return
	 */
	 
	@ResponseBody
	@RequestMapping(value="/update")
	public Response<String> updateTeam(TeamDto teamDto){
		Response<String> data = new Response<String>();
		int flag = teamService.updateTeam(teamDto);
		if(flag > 0){
			data.setMessage("编辑球队成功");
			data.setStatus(200);
		}else{
			data.setMessage("编辑球队失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月13日 上午11:28:05
	 * @param teamDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/del")
	public Response<String> delteamLogic(TeamDto teamDto){
		Response<String> data = new Response<String>();
		int flag = teamService.delTeam(teamDto);
		if(flag > 0){
			data.setMessage("删除球队成功");
			data.setStatus(200);
		}else{
			data.setMessage("删除球队失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id删除 -逻辑删除
	 * @author YIn
	 * @time:2016年1月23日 上午11:31:05
	 * @param teamDto
	 * @return
	 */
	 
	@ResponseBody
	@RequestMapping("/delete.do")
	public Map<String, Object> delTeam(TeamDto teamDto){
		logger.info("teamDto"+teamDto);
		teamDto.setStat(0); //逻辑删除
		int status = teamService.updateTeam(teamDto);
		if(status > 0){
			return MessageResp.getMessage(true,"删除球队成功");
		}
			return MessageResp.getMessage(false,"删除球队失败");
		
	}
	
	/**
	 * 批量删除
	 * @author YIn
	 * @time:2016年1月23日 上午11:31:29
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
	    Integer resObject =(Integer) teamService.delByIds(ids);
	    if (resObject == 0) {
            return MessageResp.getMessage(false, "批量删除失败");
        }
	    if (resObject == 1) {
            return MessageResp.getMessage(true, "批量删除成功");
        }
	    return MessageResp.getMessage(false, "删除异常");
	}
	
	/**
	 * 查询球队-不分页
	 * @author YIn
	 * @time:2016年1月23日 上午11:31:50
	 * @param teamDto
	 * @return
	 */
	@RequestMapping(value="/find")
	@ResponseBody
	public Response<List<TeamDto>> findTeam(TeamDto teamDto){
		Response<List<TeamDto>> data = new Response<List<TeamDto>>();
		List<TeamDto> result = teamService.findTeam(teamDto);
		if(result.size() > 0){
			data.setMessage("查询球队成功");
			data.setStatus(200);
			data.setData(result);
		}else{
			data.setMessage("查询球队失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 后台球队显示页面-分页
	 * @author YIn
	 * @time:2016年1月13日 下午2:17:03
	 * @param teamDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findTeamView")
	public ModelAndView findTeamViewByPage(TeamDto teamDto, ExtPageQuery page){
		logger.info("teamDto  : " + teamDto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<TeamDto> result = teamService.findTeamViewByPage(teamDto, page.changeToPageQuery());
		mv.addObject("pageList", result);
		mv.addObject("teamDto", teamDto);
		mv.setViewName("/team/team_list");
		return mv;
	}
	
	
}
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
import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.dto.TeamPlayerDto;
import com.tianfang.train.service.ITeamPlayerService;
import com.tianfang.train.service.ITeamService;

/**
 * 球队成员Controller
 * @author YIn
 * @time:2016年1月27日 下午5:15:29
 * @ClassName: TeamPlayerController
 * @Description: TODO
 * @
 */

@Controller
@RequestMapping("/team/player")
public class TeamPlayerController extends BaseController {

	@Autowired
	private ITeamPlayerService teamPlayerService;
	
	@Autowired
	private ITeamService teamService;
	
	/**
	 * 跳转至新增页面
	 * @return
	 */
	@RequestMapping(value = "goAdd.do")
	public ModelAndView goAdd() {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		List<TeamDto> teamList = teamService.findTeam(new TeamDto());
		mv.addObject("allTeam", teamList);
		mv.setViewName("/team/player/player_add");
		return mv;
	}

	/**
	 * 增加球队队员
	 * @author YIn
	 * @time:2016年1月27日 上午10:11:14
	 * @param teamPlayerDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add")
	public Response<String> addTeamPlayer(TeamPlayerDto teamPlayerDto){
		Response<String> data = new Response<String>();
		int flag = teamPlayerService.addTeamPlayer(teamPlayerDto);
		if(flag > 0){
			data.setMessage("添加球队队员成功");
			data.setStatus(200);
		}else{
			data.setMessage("添加球队队员失败");
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
		logger.info("去球队队员修改页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		TeamPlayerDto teamPlayerDto = new TeamPlayerDto();
		teamPlayerDto.setId(id);
		List<TeamPlayerDto> list =teamPlayerService.findTeamPlayer(teamPlayerDto);
		List<TeamDto> teamList = teamService.findTeam(new TeamDto());
		mv.addObject("allTeam", teamList);
		try {
			mv.setViewName("/Team/player/player_edit");
			mv.addObject("msg", "edit");
			mv.addObject("teamPlayerDto", list.get(0));
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 根据主键Id编辑球队队员
	 * @author YIn
	 * @time:2016年1月27日 上午11:28:05
	 * @param teamPlayerDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/update")
	public Response<String> updateTeamPlayer(TeamPlayerDto teamPlayerDto){
		Response<String> data = new Response<String>();
		int flag = teamPlayerService.updateTeamPlayer(teamPlayerDto);
		if(flag > 0){
			data.setMessage("编辑球队队员成功");
			data.setStatus(200);
		}else{
			data.setMessage("编辑球队队员失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月27日 上午11:28:05
	 * @param teamPlayerDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/del")
	public Response<String> delTeamPlayerLogic(TeamPlayerDto teamPlayerDto){
		Response<String> data = new Response<String>();
		
		int flag = teamPlayerService.delTeamPlayer(teamPlayerDto);
		if(flag > 0){
			data.setMessage("删除球队队员成功");
			data.setStatus(200);
		}else{
			data.setMessage("删除球队队员失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id删除 -逻辑删除
	 * @author YIn
	 * @time:2016年1月27日 下午5:54:33
	 * @param teamPlayerDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete.do")
	public Map<String, Object> delTeamPlayer(TeamPlayerDto teamPlayerDto){
		logger.info("TeamPlayerDto"+teamPlayerDto);
		teamPlayerDto.setStat(0); //逻辑删除
		int status = teamPlayerService.updateTeamPlayer(teamPlayerDto);
		if(status > 0){
			return MessageResp.getMessage(true,"删除球队队员成功");
		}
			return MessageResp.getMessage(false,"删除球队队员失败");
		
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
	    Integer resObject =(Integer) teamPlayerService.delByIds(ids);
	    if (resObject == 0) {
            return MessageResp.getMessage(false, "批量删除失败");
        }
	    if (resObject == 1) {
            return MessageResp.getMessage(true, "批量删除成功");
        }
	    return MessageResp.getMessage(false, "删除异常");
	}
	
	/**
	 * 查询球队队员-不分页
	 * @author YIn
	 * @time:2016年1月27日 上午11:28:05
	 * @param teamPlayerDto
	 * @return
	 */
	@RequestMapping(value="/find")
	@ResponseBody
	public Response<List<TeamPlayerDto>> findTeamPlayer(TeamPlayerDto teamPlayerDto){
		Response<List<TeamPlayerDto>> data = new Response<List<TeamPlayerDto>>();
		
		List<TeamPlayerDto> result = teamPlayerService.findTeamPlayer(teamPlayerDto);
		if(result.size() > 0){
			data.setMessage("查询球队队员成功");
			data.setStatus(200);
			data.setData(result);
		}else{
			data.setMessage("查询球队队员失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 后台球队队员显示页面-分页
	 * @author YIn
	 * @time:2016年1月27日 下午2:17:03
	 * @param teamPlayerDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findTeamPlayerView")
	public ModelAndView findTeamPlayerViewByPage(TeamPlayerDto teamPlayerDto, ExtPageQuery page){
		logger.info("TeamPlayerDto  : " + teamPlayerDto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<TeamPlayerDto> result = teamPlayerService.findTeamPlayerViewByPage(teamPlayerDto, page.changeToPageQuery());
		List<TeamDto> teamList = teamService.findTeam(new TeamDto());
		mv.addObject("allTeam", teamList);
		mv.addObject("pageList", result);
		mv.addObject("teamPlayerDto", teamPlayerDto);
		mv.setViewName("/team/player/player_list");
		return mv;
	}
}
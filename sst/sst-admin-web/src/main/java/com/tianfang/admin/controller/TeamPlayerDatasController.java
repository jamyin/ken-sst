package com.tianfang.admin.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.dto.AdminDto;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.train.dto.CompetitionDto;
import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.dto.TeamPlayerDatasDto;
import com.tianfang.train.dto.TeamPlayerDto;
import com.tianfang.train.service.ICompetitionService;
import com.tianfang.train.service.ITeamPlayerDatasService;
import com.tianfang.train.service.ITeamPlayerService;
import com.tianfang.train.service.ITeamService;

@Controller
@RequestMapping(value = "team/player/datas")
public class TeamPlayerDatasController extends BaseController{
	protected static final Log logger = LogFactory.getLog(TeamPlayerDatasController.class);
	
	@Autowired
	private ITeamPlayerDatasService datasService;
	@Autowired
	private ITeamService teamService;
	@Autowired
	private ICompetitionService compService;
	@Autowired
	private ITeamPlayerService playerService;
	
	@RequestMapping(value = "list")
    public ModelAndView findpage(TeamPlayerDatasDto dto, ExtPageQuery page) throws Exception {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PageResult<TeamPlayerDatasDto> result = datasService.findTeamPlayerDatasByParam(dto, page.changeToPageQuery());
        mv.setViewName("/team/player/datas/list");
        mv.addObject("pageList", result);
        mv.addObject("params", dto);
        return mv;
    }
	
	/**
	 * 新增球员数据
	 * @return
	 * @author xiang_wang
	 * 2016年1月27日下午3:31:58
	 */
	@RequestMapping(value = "add")
    public ModelAndView add() {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        try {
			List<CompetitionDto> comps = compService.findAllCompetition();
			mv.addObject("comps", comps);
			mv.setViewName("/team/player/datas/add");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			mv.setViewName("500");
		}
        return mv;
    }
	
	@ResponseBody
	@RequestMapping(value="/save")
	public Response<String> save(TeamPlayerDatasDto dto){
		Response<String> data = new Response<String>();
		try {
			assemblyDatas(dto);
			datasService.save(dto);
			data.setMessage("新增成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			data.setMessage("新增失败");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
		return data;
	}
	
	@RequestMapping(value = "edit")
    public ModelAndView edit(String id) {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        try {
			TeamPlayerDatasDto datas = datasService.getTeamPlayerDatasById(id);
			List<CompetitionDto> comps = compService.findAllCompetition();
			mv.addObject("comps", comps);
			mv.addObject("datas", datas);
			mv.setViewName("/team/player/datas/edit");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			mv.setViewName("500");
		}
        return mv;
    }
	
	@ResponseBody
	@RequestMapping(value="/update")
	public Response<String> update(TeamPlayerDatasDto dto){
		Response<String> data = new Response<String>();
		try {
			assemblyDatas(dto);
			datasService.update(dto);
			data.setMessage("编辑成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			data.setMessage("编辑失败");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
		return data;
	}
	
	/**
	 * ajax-查询赛事下的球队
	 * @param compId
	 * @return
	 * @author xiang_wang
	 * 2016年1月27日下午3:31:29
	 */
	@RequestMapping(value = "selectTeam")
    @ResponseBody
    public Response<List<TeamDto>> selectTeam(String compId){
		Response<List<TeamDto>> data = new Response<List<TeamDto>>();
        if (StringUtils.isBlank(compId)) {
        	data.setMessage("请求参数不能为空~");
			data.setStatus(DataStatus.HTTP_FAILE);
            return data;
        }
        try {
			List<TeamDto> teams = teamService.findAllByCompId(compId);
			data.setData(teams);
			data.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			data.setMessage("系统异常~");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
        return data;
    }
	
	/**
	 * ajax-查询球队下的球员
	 * @param teamId
	 * @return
	 * @author xiang_wang
	 * 2016年1月27日下午3:31:41
	 */
	@RequestMapping(value = "selectPlayer")
    @ResponseBody
    public Response<List<TeamPlayerDto>> selectPlayer(String teamId){
		Response<List<TeamPlayerDto>> data = new Response<List<TeamPlayerDto>>();
        if (StringUtils.isBlank(teamId)) {
        	data.setMessage("请求参数不能为空~");
			data.setStatus(DataStatus.HTTP_FAILE);
            return data;
        }
        try {
			List<TeamPlayerDto> teams = playerService.findTeamPlayerByTeamId(teamId);
			data.setData(teams);
			data.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			data.setMessage("系统异常~");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
        return data;
    }
    
	@RequestMapping(value = "delIds")
    @ResponseBody
    public Response<String> delAdIds(String ids){
		Response<String> data = new Response<String>();
        if (StringUtils.isBlank(ids)) {
        	data.setMessage("请求参数不能为空~");
			data.setStatus(DataStatus.HTTP_FAILE);
            return data;
        }
        try {
			datasService.del(ids);
			data.setMessage("删除成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			data.setMessage("删除失败~");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
        return data;
    }
	
	private void assemblyDatas(TeamPlayerDatasDto dto) {
		AdminDto admin = getSessionAdmin();
		dto.setCreateAdminId(admin.getId());
		dto.setCreateAdminName(admin.getAccount());
		TeamDto team = teamService.getTeamById(dto.getTeamId());
		dto.setTeamName(team.getName());
		dto.setTeamIcon(team.getIcon());
		CompetitionDto comp = compService.getCompById(dto.getCompId());
		dto.setCompName(comp.getTitle());
		TeamPlayerDto player = playerService.getTeamPlayerById(dto.getPlayerId());
		dto.setPlayerName(player.getName());
	}
}
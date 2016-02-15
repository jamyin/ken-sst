package com.tianfang.admin.controller;

import java.util.List;

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
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.CompetitionDto;
import com.tianfang.train.dto.CompetitionMatchDto;
import com.tianfang.train.dto.CompetitionRoundDto;
import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.enums.TeamType;
import com.tianfang.train.service.ICompetitionMatchService;
import com.tianfang.train.service.ICompetitionRoundService;
import com.tianfang.train.service.ICompetitionService;
import com.tianfang.train.service.ITeamService;

/**
 * 比赛Controller
 * @author YIn
 * @time:2016年1月15日 上午9:39:09
 * @ClassName: competitionMatchController
 * @Description: TODO
 * @
 */
@Controller
@RequestMapping("/competition/match")
public class CompetitionMatchController extends BaseController {

	@Autowired
	private ICompetitionMatchService matchService;
	@Autowired
	private ICompetitionService compService;
	@Autowired
	private ICompetitionRoundService roundService;
	@Autowired
	private ITeamService teamService;
	
	/**
	 * 跳转至新增比赛页面
	 * @return
	 * @author xiang_wang
	 * 2016年1月26日下午2:10:23
	 */
	@RequestMapping(value = "createMatch")
	public ModelAndView createMatch(String croundId) {
		if (StringUtils.isBlank(croundId)){
			return selectComp();
		}
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		CompetitionRoundDto round = roundService.getRoundById(croundId);
		List<TeamDto> teams = teamService.findAllByCompId(round.getCompId());
		mv.addObject("round", round);
		mv.addObject("teams", teams);
		mv.addObject("types", TeamType.values());
		mv.setViewName("/competition/match/match_add");
		return mv;
	}
	
	/**
	 * 先选择赛事
	 * @return
	 * @author xiang_wang
	 * 2016年1月26日上午11:57:39
	 */
	@RequestMapping(value = "selectComp")
	public ModelAndView selectComp() {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		List<CompetitionDto> comps = compService.findAllCompetition();
		mv.addObject("comps", comps);
		mv.setViewName("/competition/match/selectComp");
		return mv;
	}
	
	/**
	 * 根据赛事id获取场次记录
	 * @param compId
	 * @return
	 * @author xiang_wang
	 * 2016年1月26日下午1:13:30
	 */
	@ResponseBody
	@RequestMapping(value="/selectRound")
	public Response<List<CompetitionRoundDto>> selectRound(String compId){
		Response<List<CompetitionRoundDto>> data = new Response<List<CompetitionRoundDto>>();
		List<CompetitionRoundDto> dtos = roundService.findRoundByCompId(compId);
		data.setStatus(DataStatus.HTTP_SUCCESS);
		data.setData(dtos);
		return data;
	}

	/**
	 * 保存比赛
	 * @param dto
	 * @return
	 * @author xiang_wang
	 * 2016年1月26日下午3:27:56
	 */
	@ResponseBody
	@RequestMapping(value="/save")
	public Response<String> addCompetitionMatch(CompetitionMatchDto dto){
		Response<String> data = new Response<String>();
		TeamDto homeTeam = teamService.getTeamById(dto.getHomeTeamId());
		TeamDto visitingTeam = teamService.getTeamById(dto.getVisitingTeamId());
		AdminDto admin = getSessionAdmin();
		try {
			matchService.saveMatch(dto, homeTeam, visitingTeam, admin.getId(), admin.getAccount());
			data.setMessage("新增成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			data.setMessage("新增失败");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
		return data;
	}
	
	/**
	 * 编辑
	 * @param competitionMatchId
	 * @return
	 * @author xiang_wang
	 * 2016年1月26日下午3:33:27
	 */
	@RequestMapping(value = "edit")
	public ModelAndView goEdit(String id) {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		CompetitionMatchDto match = matchService.getMatchById(id);
		List<TeamDto> teams = teamService.findAllByCompId(match.getCompId());
		mv.addObject("match", match);
		mv.addObject("teams", teams);
		mv.addObject("types", TeamType.values());
		mv.setViewName("/competition/match/match_edit");
		return mv;
	}
	
	/**
	 * 根据主键Id编辑比赛
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:05
	 * @param competitionMatchDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="update")
	public Response<String> updateCompetitionMatch(CompetitionMatchDto dto){
		Response<String> data = new Response<String>();
		TeamDto homeTeam = teamService.getTeamById(dto.getHomeTeamId());
		TeamDto visitingTeam = teamService.getTeamById(dto.getVisitingTeamId());
		AdminDto admin = getSessionAdmin();
		try {
			matchService.updateMatch(dto, homeTeam, visitingTeam, admin.getId(), admin.getAccount());
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
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:05
	 * @param competitionMatchDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="del")
	public Response<String> delCompetitionMatchLogic(CompetitionMatchDto competitionMatchDto){
		Response<String> data = new Response<String>();
		int flag = matchService.delCompetitionMatch(competitionMatchDto);
		if(flag > 0){
			data.setMessage("删除成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		}else{
			data.setMessage("删除失败");
			data.setStatus(DataStatus.HTTP_FAILE);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id删除 -逻辑删除
	 * @author YIn
	 * @time:2016年1月15日 下午5:54:33
	 * @param competitionMatchDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public Response<String> delCompetitionMatch(CompetitionMatchDto competitionMatchDto){
		Response<String> data = new Response<String>();
		competitionMatchDto.setStat(0); //逻辑删除
		int status = matchService.updateCompetitionMatch(competitionMatchDto);
		if(status > 0){
			data.setMessage("删除成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		}else{
			data.setMessage("删除失败");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
		return data;
		
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
	@RequestMapping(value="delByIds")
    public Response<String> delByIds(String  ids) throws Exception{
		Response<String> data = new Response<String>();
		
		if (StringUtils.isEmpty(ids)) {
			data.setMessage("请选择需要删除的项！");
			data.setStatus(DataStatus.HTTP_FAILE);
	        return data;
	    }
	    Integer resObject =(Integer) matchService.delByIds(ids);
	    if (resObject == 0) {
	    	data.setMessage("批量删除失败！");
			data.setStatus(DataStatus.HTTP_FAILE);
            return data;
        }
	    if (resObject == 1) {
	    	data.setMessage("批量删除成功！");
			data.setStatus(DataStatus.HTTP_SUCCESS);
            return data;
        }
	    data.setMessage("删除异常！");
		data.setStatus(DataStatus.HTTP_FAILE);
	    return data;
	}
	
	/**
	 * 查询比赛-不分页
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:05
	 * @param competitionMatchDto
	 * @return
	 */
	@RequestMapping(value="find")
	@ResponseBody
	public Response<List<CompetitionMatchDto>> findCompetitionMatch(CompetitionMatchDto competitionMatchDto){
		Response<List<CompetitionMatchDto>> data = new Response<List<CompetitionMatchDto>>();
		
		List<CompetitionMatchDto> result = matchService.findCompetitionMatch(competitionMatchDto);
		if(result.size() > 0){
			data.setMessage("查询比赛成功");
			data.setStatus(200);
			data.setData(result);
		}else{
			data.setMessage("查询比赛失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 后台比赛显示页面-分页
	 * @author YIn
	 * @time:2016年1月15日 下午2:17:03
	 * @param competitionMatchDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="list")
	public ModelAndView list(CompetitionMatchDto dto, ExtPageQuery page){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<CompetitionMatchDto> result = matchService.findCompetitionMatchViewByPage(dto, page.changeToPageQuery());
		mv.addObject("pageList", result);
		mv.addObject("matchTypes", TeamType.values());
		mv.addObject("params", dto);
		mv.setViewName("/competition/match/match_list");
		return mv;
	}
}
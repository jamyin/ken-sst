package com.tianfang.admin.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.train.dto.CompetitionDto;
import com.tianfang.train.dto.CompetitionMatchDto;
import com.tianfang.train.dto.CompetitionTeamDto;
import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.enums.TeamType;
import com.tianfang.train.service.ICompetitionMatchService;
import com.tianfang.train.service.ICompetitionService;
import com.tianfang.train.service.ICompetitionTeamService;
import com.tianfang.train.service.ITeamService;

@Controller
@RequestMapping(value = "comp/team")
public class CompetitionTeamController extends BaseController{
	 protected static final Log logger = LogFactory.getLog(CompetitionTeamController.class);
	 
	@Autowired
	private ICompetitionTeamService cTeamsService;
	@Autowired
	private ICompetitionService compService;
	@Autowired
	private ITeamService teamService;
	@Autowired
	private ICompetitionMatchService matchService;
	
	@RequestMapping(value="list")
	public ModelAndView findTeams(CompetitionTeamDto dto, ExtPageQuery page){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		List<CompetitionDto> comps = compService.findAllCompetition();
		PageResult<CompetitionTeamDto> result = cTeamsService.findCompetitionTeamByParam(dto, page.changeToPageQuery());
		mv.addObject("comps", comps);
		mv.addObject("pageList", result);
		mv.addObject("params", dto);
		mv.addObject("types", TeamType.values());
		mv.setViewName("/competition/team/list");
		return mv;
	}
	
	@RequestMapping(value="add")
	public ModelAndView add(){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		List<CompetitionDto> comps = compService.findAllCompetition();
		mv.addObject("comps", comps);
		mv.setViewName("/competition/team/add");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="save")
	public Response<String> save(CompetitionTeamDto dto){
		Response<String> data = new Response<String>();
		try {
			if (null != cTeamsService.checkTeam(dto.getCompId(), dto.getTeamId())){
				data.setMessage("对不起,该赛事下已经存在该球队!");
				data.setStatus(DataStatus.HTTP_FAILE);
				return data;
			}
			cTeamsService.save(dto);
			data.setMessage("新增成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			data.setMessage("新增失败");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
		return data;
	}
	
	@RequestMapping(value="edit")
	public ModelAndView edit(String id){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		List<CompetitionDto> comps = compService.findAllCompetition();
		CompetitionTeamDto cTeam = cTeamsService.getCompetitionTeamById(id);
		mv.addObject("comps", comps);
		mv.addObject("data", cTeam);
		mv.setViewName("/competition/team/edit");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="update")
	public Response<String> update(CompetitionTeamDto dto){
		Response<String> data = new Response<String>();
		try {
			CompetitionTeamDto cteam = cTeamsService.checkTeam(dto.getCompId(), dto.getTeamId());
			if (null != cteam && !cteam.getId().equals(dto.getId())){
				data.setMessage("对不起,该赛事下已经存在该球队!");
				data.setStatus(DataStatus.HTTP_FAILE);
				return data;
			}
			cTeamsService.update(dto);
			data.setMessage("编辑成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			data.setMessage("编辑失败");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
		return data;
	}
	
	@RequestMapping(value = "delByIds")
    @ResponseBody
    public Response<String> delAdIds(String ids){
		Response<String> data = new Response<String>();
        if (StringUtils.isBlank(ids)) {
        	data.setMessage("请求参数不能为空~");
			data.setStatus(DataStatus.HTTP_FAILE);
            return data;
        }
        try {
        	cTeamsService.del(ids);
			data.setMessage("删除成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			data.setMessage("删除失败~");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
        return data;
    }
	
	/**
	 * ajax-查询球队(已过滤已参加该赛事的球队)
	 * @param compId
	 * @return
	 * @author xiang_wang
	 * 2016年1月28日下午3:18:15
	 */
	@RequestMapping(value="selectTeams")
	@ResponseBody
	public Response<List<TeamDto>> selectTeams(String compId){
		Response<List<TeamDto>> response = new Response<List<TeamDto>>();
		if (StringUtils.isBlank(compId)){
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("对不起,请求参数异常~");
			return response;
		}
		try {
			List<TeamDto> teams = teamService.findAll();
			if (null != teams && teams.size() > 0){
				List<CompetitionTeamDto> cteams = cTeamsService.findCompetitionTeamByCompId(compId);
				if (null != cteams && cteams.size() > 0){
					teams.removeAll(cteams);
				}
			}
			response.setData(teams);
			response.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("对不起,系统异常~");
		}
		return response;
	}
	
	/**
	 * 根据比赛数据统计赛事球队的胜负平,进/失球数据
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2016年2月1日下午2:02:43
	 */
	@RequestMapping(value = "utd")
    @ResponseBody
    public Response<String> updateTeamDatas(String id){
		Response<String> data = new Response<String>();
        if (StringUtils.isBlank(id)) {
        	data.setMessage("请求参数不能为空~");
			data.setStatus(DataStatus.HTTP_FAILE);
            return data;
        }
        CompetitionTeamDto cTeam = cTeamsService.getCompetitionTeamById(id);
    	try {
    		List<CompetitionMatchDto> matchs = matchService.findMatchByTeamIdAndCompId(cTeam.getTeamId(), cTeam.getCompId());
    		if (null != matchs && matchs.size() > 0){
	         	cTeamsService.updateTeamDatas(cTeam, matchs);
	 			data.setMessage("更新成功");
	 			data.setStatus(DataStatus.HTTP_SUCCESS);
    		 }else{
	        	data.setMessage("暂无比赛数据");
	 			data.setStatus(DataStatus.HTTP_FAILE);
    	     }
 		} catch (Exception e) {
 			e.printStackTrace();
 			data.setMessage("更新失败~");
 			data.setStatus(DataStatus.HTTP_FAILE);
 		}
       
        return data;
    }
}
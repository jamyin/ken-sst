package com.tianfang.admin.controller.comp;

import java.util.List;
import java.util.Map;

import com.tianfang.admin.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.dto.AdminDto;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.CompetitionDto;
import com.tianfang.train.dto.CompetitionMatchDto;
import com.tianfang.train.dto.CompetitionRoundDto;
import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.enums.PeopleType;
import com.tianfang.train.enums.TeamType;
import com.tianfang.train.service.ICompetitionMatchService;
import com.tianfang.train.service.ICompetitionRoundService;
import com.tianfang.train.service.ICompetitionService;
import com.tianfang.train.service.ITeamService;

@Controller
@RequestMapping("competition/round")
public class CompetitionRoundController extends BaseController {
	protected static final Log logger = LogFactory.getLog(CompetitionRoundController.class); 
	
	@Autowired
	private ICompetitionRoundService roundService;
	@Autowired
	private ICompetitionService competitionService;
	@Autowired
	private ITeamService teamService;
	@Autowired
	private ICompetitionMatchService matchService;
	
	@RequestMapping("list")
	public ModelAndView list(CompetitionRoundDto dto, ExtPageQuery page){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        try {
			PageResult<CompetitionRoundDto> result = roundService.findRoundByParam(dto, page.changeToPageQuery());
			mv.setViewName("/competition/round/list");
			mv.addObject("pageList", result);
			mv.addObject("params", dto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			mv.setViewName("500");
		}
        return mv;
	}
	
	@RequestMapping(value = "selectComp")
    public ModelAndView selectComp() {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        try {
			List<CompetitionDto> comps = competitionService.findAllCompetition();
			mv.addObject("comps", comps);
			mv.setViewName("/competition/round/selectComp");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			mv.setViewName("500");
		}
        return mv;
    }
	
	@RequestMapping(value = "selectTeam")
    public ModelAndView selectTeam(String compId) {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        try {
			CompetitionDto comp = competitionService.getCompById(compId);
			List<TeamDto> teams = teamService.findAllByCompId(compId);
			mv.addObject("comp", comp);
			mv.addObject("teams", teams);
			mv.addObject("types", TeamType.values());
			mv.addObject("peopleTypes", PeopleType.values());
			mv.setViewName("/competition/round/selectTeam");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			mv.setViewName("500");
		}
        return mv;
    }
	
	@RequestMapping(value = "save")
    @ResponseBody
    public Map<String, Object> save(CompetitionRoundDto dto) throws Exception{
        Map<String, Object> result;
        try {
        	AdminDto admin = getSessionAdmin();
			roundService.saveRoundAndMatch(dto, admin.getId(), admin.getAccount());
			result = MessageResp.getMessage(true, "新增成功~");
		} catch (Exception e) {
			result = MessageResp.getMessage(false, e.getMessage());
			e.printStackTrace();
			logger.error("save方法抛出异常:"+e.getMessage());
		}
        return result;
    }
	
	@RequestMapping(value = "edit")
    public ModelAndView edit(String id) {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        try {
        	CompetitionRoundDto round = roundService.getRoundById(id);
        	List<CompetitionMatchDto> matchs = matchService.findMatchByRoundId(id);
        	List<TeamDto> teams = teamService.findAllByCompId(round.getCompId());
			mv.addObject("round", round);
			mv.addObject("teams", teams);
			mv.addObject("matchs", matchs);
			mv.addObject("types", TeamType.values());
			mv.addObject("peopleTypes", PeopleType.values());
			if (null != matchs && matchs.size() > 0){
				mv.addObject("ptselected", matchs.get(0).getPeopleType());
			}
			mv.setViewName("/competition/round/edit");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			mv.setViewName("500");
		}
        return mv;
    }
	
	@RequestMapping(value = "update")
    @ResponseBody
    public Map<String, Object> update(CompetitionRoundDto dto) throws Exception{
        Map<String, Object> result;
        try {
        	AdminDto admin = getSessionAdmin();
			roundService.updateRoundAndMatch(dto, admin.getId(), admin.getAccount());
			result = MessageResp.getMessage(true, "编辑成功~");
		} catch (Exception e) {
			result = MessageResp.getMessage(false, e.getMessage());
			e.printStackTrace();
			logger.error("update方法抛出异常:"+e.getMessage());
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
        	roundService.del(ids);
			result = MessageResp.getMessage(true, "删除成功~");
		} catch (Exception e) {
			e.printStackTrace();
			result = MessageResp.getMessage(false, e.getMessage());
		}
        return result;
    }
	
	/**
	 * 修改场次排序
	 * @param id
	 * @param pr
	 * @return
	 * @throws Exception
	 * @author xiang_wang
	 * 2016年2月2日下午2:15:14
	 */
	@RequestMapping(value = "ranking")
    @ResponseBody
    public Map<String, Object> ranking(String id,int pr) throws Exception{
        Map<String, Object> result;
        try {
			roundService.updatePageRanking(id, pr);
			result = MessageResp.getMessage(true, "操作成功~");
		} catch (Exception e) {
			result = MessageResp.getMessage(false, "操作失败~");
			e.printStackTrace();
			logger.error("ranking方法抛出异常:"+e.getMessage());
		}
        return result;
    }
}
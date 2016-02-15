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
import com.tianfang.train.dto.CompetitionApplyDto;
import com.tianfang.train.dto.CompetitionDto;
import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.service.ICompetitionApplyService;
import com.tianfang.train.service.ICompetitionService;
import com.tianfang.train.service.ITeamService;

/**
 * 比赛报名比赛报名Controller
 * @author YIn
 * @time:2016年1月20日 上午8:59:49
 * @ClassName: CompetitionApplyController
 * @Description: TODO
 * @
 */
@Controller
@RequestMapping("/competition/apply")
public class CompetitionApplyController extends BaseController {

	@Autowired
	private ICompetitionApplyService competitionApplyService;
	
	@Autowired
	private ICompetitionService competitionService;
	
	@Autowired
	private ITeamService teamService;
	
	/**
	 * 跳转至新增页面
	 * @return
	 */
	@RequestMapping(value = "goAdd.do")
	public ModelAndView goAdd() {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		List<CompetitionDto> compList =  competitionService.findCompetition(new CompetitionDto());
		mv.addObject("allComp", compList);
		List<TeamDto> teamList = teamService.findTeam(new TeamDto());
		mv.addObject("allTeam", teamList);
		mv.setViewName("/competition/apply/apply_add");
		return mv;
	}

	/**
	 * 增加比赛报名
	 * @author YIn
	 * @time:2016年1月20日 上午10:11:14
	 * @param competitionApplyDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add")
	public Response<String> addCompetitionApply(CompetitionApplyDto competitionApplyDto){
		Response<String> data = new Response<String>();
		AdminDto admin = getSessionAdmin();
		competitionApplyDto.setCreateUserId(admin.getId());
		competitionApplyDto.setCreateUserName(admin.getAccount());
		int flag = competitionApplyService.addCompetitionApply(competitionApplyDto);
		if(flag > 0){
			data.setMessage("添加比赛报名成功");
			data.setStatus(200);
		}else{
			data.setMessage("添加比赛报名失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 跳转至编辑页面
	 * @return
	 */
	@RequestMapping(value = "goEdit")
	public ModelAndView goEdit(String id) {
		logger.info("去比赛报名修改页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		CompetitionApplyDto dto = competitionApplyService.getCompetitionApplyById(id);
		List<CompetitionDto> compList =  competitionService.findCompetition(new CompetitionDto());
		mv.addObject("allComp", compList);
		List<TeamDto> teamList = teamService.findTeam(new TeamDto());
		mv.addObject("allTeam", teamList);
		try {
			mv.setViewName("/competition/apply/apply_edit");
			mv.addObject("competitionApplyDto", dto);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 根据主键Id编辑比赛报名
	 * @author YIn
	 * @time:2016年1月20日 上午11:28:05
	 * @param competitionApplyDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/update")
	public Response<String> updateCompetitionApply(CompetitionApplyDto competitionApplyDto){
		Response<String> data = new Response<String>();
		
		int flag = competitionApplyService.updateCompetitionApply(competitionApplyDto);
		if(flag > 0){
			data.setMessage("编辑比赛报名成功");
			data.setStatus(200);
		}else{
			data.setMessage("编辑比赛报名失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月20日 上午11:28:05
	 * @param competitionApplyDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/del")
	public Response<String> delCompetitionApplyLogic(CompetitionApplyDto competitionApplyDto){
		Response<String> data = new Response<String>();
		int flag = competitionApplyService.delCompetitionApply(competitionApplyDto);
		if(flag > 0){
			data.setMessage("删除比赛报名成功");
			data.setStatus(200);
		}else{
			data.setMessage("删除比赛报名失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id删除 -逻辑删除
	 * @author YIn
	 * @time:2016年1月20日 下午5:54:33
	 * @param competitionapplyDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete.do")
	public Map<String, Object> delCompetitionApply(CompetitionApplyDto competitionApplyDto){
		logger.info("competitionApplyDto"+competitionApplyDto);
		competitionApplyDto.setStat(0); //逻辑删除
		int status = competitionApplyService.updateCompetitionApply(competitionApplyDto);
		if(status > 0){
			return MessageResp.getMessage(true,"删除比赛报名成功");
		}
			return MessageResp.getMessage(false,"删除比赛报名失败");
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
	    Integer resObject =(Integer) competitionApplyService.delByIds(ids);
	    if (resObject == 0) {
            return MessageResp.getMessage(false, "批量删除失败");
        }
	    if (resObject == 1) {
            return MessageResp.getMessage(true, "批量删除成功");
        }
	    return MessageResp.getMessage(false, "删除异常");
	}
	
	/**
	 * 查询比赛报名-不分页
	 * @author YIn
	 * @time:2016年1月20日 上午11:28:05
	 * @param competitionApplyDto
	 * @return
	 */
	@RequestMapping(value="/find")
	@ResponseBody
	public Response<List<CompetitionApplyDto>> findCompetitionApply(CompetitionApplyDto competitionApplyDto){
		Response<List<CompetitionApplyDto>> data = new Response<List<CompetitionApplyDto>>();
		
		List<CompetitionApplyDto> result = competitionApplyService.findCompetitionApply(competitionApplyDto);
		if(result.size() > 0){
			data.setMessage("查询比赛报名成功");
			data.setStatus(200);
			data.setData(result);
		}else{
			data.setMessage("查询比赛报名失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 后台比赛报名显示页面-分页
	 * @author YIn
	 * @time:2016年1月23日 上午10:11:49
	 * @param competitionApplyDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findCompApplyView")
	public ModelAndView findCompApplyViewByPage(CompetitionApplyDto competitionApplyDto, ExtPageQuery page){
		logger.info("competitionApplyDto  : " + competitionApplyDto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<CompetitionApplyDto> result = competitionApplyService.findCompetitionApplyViewByPage(competitionApplyDto, page.changeToPageQuery());
		List<CompetitionDto> compList =  competitionService.findCompetition(new CompetitionDto());
		mv.addObject("allComp", compList);
		List<TeamDto> teamList = teamService.findTeam(new TeamDto());
		mv.addObject("allTeam", teamList);
		mv.addObject("pageList", result);
		mv.addObject("params", competitionApplyDto);
		mv.setViewName("/competition/apply/apply_list");
		return mv;
	}
	
	
	/**
	 * 审核球队
	 * @param id
	 * @param status
	 * @return
	 * @author xiang_wang
	 * 2016年1月23日上午11:14:01
	 */
	@RequestMapping(value="/audit")
	@ResponseBody
	public Map<String, Object> audit(String id, Integer status){
		if (StringUtils.isEmpty(id)) {
			return MessageResp.getMessage(false, "主键id为空！");
		}
		if (null == status){
			return MessageResp.getMessage(false, "审核状态异常！");
		}
	    Integer resObject =(Integer) competitionApplyService.auditCompetitionApply(id, status);
	    if (resObject == 0) {
            return MessageResp.getMessage(false, "审核失败");
        }
	    if (resObject == 1) {
            return MessageResp.getMessage(true, "审核成功");
        }
	    return MessageResp.getMessage(false, "审核异常");
	}
}
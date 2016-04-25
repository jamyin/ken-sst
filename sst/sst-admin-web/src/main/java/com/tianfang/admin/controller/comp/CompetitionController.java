package com.tianfang.admin.controller.comp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.tianfang.admin.controller.BaseController;
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
import com.tianfang.train.dto.CompetitionDto;
import com.tianfang.train.service.ICompetitionService;

/**
 * 赛事Controller
 * @author YIn
 * @time:2016年1月14日 上午9:39:09
 * @ClassName: competitionController
 * @Description: TODO
 * @
 */
@Controller
@RequestMapping("/competition")
public class CompetitionController extends BaseController {

	@Autowired
	private ICompetitionService competitionService;
	
	/**
	 * 跳转至新增页面
	 * @return
	 */
	@RequestMapping(value = "goAdd.do")
	public ModelAndView goAdd() {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		mv.setViewName("/competition/competition_add");
		return mv;
	}

	/**
	 * 增加赛事
	 * @author YIn
	 * @time:2016年1月14日 上午10:11:14
	 * @param albumPicDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add")
	public Response<String> addcompetition(CompetitionDto competitionDto){
		Response<String> data = new Response<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		if(StringUtils.isNotEmpty(competitionDto.getStartDateStr())){
			try {
				competitionDto.setStartDate(sdf.parse(competitionDto.getStartDateStr()));
			} catch (ParseException e) {
				e.printStackTrace();
			}  
		}
		if(StringUtils.isNotEmpty(competitionDto.getEndDateStr())){
			try {
				competitionDto.setEndDate(sdf.parse(competitionDto.getEndDateStr()));
			} catch (ParseException e) {
				e.printStackTrace();
			}  
		}
		AdminDto admin = getSessionAdmin();
		competitionDto.setCreateAdminId(admin.getId());
		competitionDto.setCreateAdminName(admin.getAccount());
		int flag = competitionService.addCompetition(competitionDto);
		if(flag > 0){
			data.setMessage("添加赛事成功");
			data.setStatus(200);
		}else{
			data.setMessage("添加赛事失败");
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
		logger.info("去赛事修改页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		CompetitionDto competitionDto = new CompetitionDto();
		competitionDto.setId(id);
		List<CompetitionDto> list =competitionService.findCompetition(competitionDto);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		for(CompetitionDto dto:list){
			if(dto.getStartDate() != null){
			dto.setStartDateStr(sdf.format(dto.getStartDate()));
			}
			if(dto.getEndDate() != null){
				dto.setEndDateStr(sdf.format(dto.getEndDate()));
			}
		}
		try {
			mv.setViewName("/competition/competition_edit");
			mv.addObject("msg", "edit");
			mv.addObject("competitionDto", list.get(0));
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 根据主键Id编辑赛事
	 * @author YIn
	 * @time:2016年1月14日 上午11:28:05
	 * @param competitionDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/update")
	public Response<String> updatecompetition(CompetitionDto competitionDto){
		Response<String> data = new Response<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		if(StringUtils.isNotEmpty(competitionDto.getStartDateStr())){
			try {
				competitionDto.setStartDate(sdf.parse(competitionDto.getStartDateStr()));
			} catch (ParseException e) {
				e.printStackTrace();
			}  
		}
		if(StringUtils.isNotEmpty(competitionDto.getEndDateStr())){
			try {
				competitionDto.setEndDate(sdf.parse(competitionDto.getEndDateStr()));
			} catch (ParseException e) {
				e.printStackTrace();
			}  
		}
		int flag = competitionService.updateCompetition(competitionDto);
		if(flag > 0){
			data.setMessage("编辑赛事成功");
			data.setStatus(200);
		}else{
			data.setMessage("编辑赛事失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id编辑赛事
	 * @author YIn
	 * @time:2016年1月14日 上午11:28:05
	 * @param competitionDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/audit")
	public Response<String> audit(CompetitionDto competitionDto){
		Response<String> data = new Response<String>();
		int flag = competitionService.updateCompetition(competitionDto);
		if(flag > 0){
			data.setMessage("更改报名状态成功");
			data.setStatus(200);
		}else{
			data.setMessage("更改报名状态失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月14日 上午11:28:05
	 * @param competitionDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/del")
	public Response<String> delcompetitionLogic(CompetitionDto competitionDto){
		Response<String> data = new Response<String>();
		
		int flag = competitionService.delCompetition(competitionDto);
		if(flag > 0){
			data.setMessage("删除赛事成功");
			data.setStatus(200);
		}else{
			data.setMessage("删除赛事失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id删除 -逻辑删除
	 * @author YIn
	 * @time:2016年1月14日 下午5:54:33
	 * @param competitionDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete.do")
	public Map<String, Object> delcompetition(CompetitionDto competitionDto){
		logger.info("competitionDto"+competitionDto);
		competitionDto.setStat(0); //逻辑删除
		int status = competitionService.updateCompetition(competitionDto);
		if(status > 0){
			return MessageResp.getMessage(true,"删除赛事成功");
		}
			return MessageResp.getMessage(false,"删除赛事失败");
		
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
	    Integer resObject =(Integer) competitionService.delByIds(ids);
	    if (resObject == 0) {
            return MessageResp.getMessage(false, "批量删除失败");
        }
	    if (resObject == 1) {
            return MessageResp.getMessage(true, "批量删除成功");
        }
	    return MessageResp.getMessage(false, "删除异常");
	}
	
	/**
	 * 查询赛事-不分页
	 * @author YIn
	 * @time:2016年1月14日 上午11:28:05
	 * @param competitionDto
	 * @return
	 */
	@RequestMapping(value="/find")
	@ResponseBody
	public Response<List<CompetitionDto>> findCompetition(CompetitionDto competitionDto){
		Response<List<CompetitionDto>> data = new Response<List<CompetitionDto>>();
		
		List<CompetitionDto> result = competitionService.findCompetition(competitionDto);
		if(result.size() > 0){
			data.setMessage("查询赛事成功");
			data.setStatus(200);
			data.setData(result);
		}else{
			data.setMessage("查询赛事失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 后台赛事显示页面-分页
	 * @author YIn
	 * @time:2016年1月14日 下午2:17:03
	 * @param competitionDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findCompetitionView")
	public ModelAndView findCompetitionViewByPage(CompetitionDto competitionDto, ExtPageQuery page){
		logger.info("competitionDto  : " + competitionDto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<CompetitionDto> result = competitionService.findCompetitionViewByPage(competitionDto, page.changeToPageQuery());
		mv.addObject("pageList", result);
		mv.addObject("competitionDto", competitionDto);
		mv.setViewName("/competition/competition_list");
		return mv;
	}
	
	
}
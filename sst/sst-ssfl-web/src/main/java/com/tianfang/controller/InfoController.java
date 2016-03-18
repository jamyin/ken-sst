package com.tianfang.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.controller.IndexController.InfoType;
import com.tianfang.message.dto.InformationDto;
import com.tianfang.message.service.IInformationService;
import com.tianfang.train.dto.CompetitionTeamDto;
import com.tianfang.train.service.ICompetitionTeamService;

@Controller
@RequestMapping(value="info")
public class InfoController extends BaseController{

	@Autowired
	private ICompetitionTeamService iCompetitionTeamService;
	
	/**
	 * 
		 * 此方法描述的是：新闻资讯
		 * @author: cwftalus@163.com
		 * @version: 2016年3月14日 下午2:59:50
	 */
	@Autowired
	private IInformationService iInformationService;
	
	@RequestMapping(value="index")
	public ModelAndView index(InformationDto dto,PageQuery query){
		ModelAndView mv = getModelAndView();
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		map.put("pageList",getInfomatation(dto,query));
		map.put("raceRecord",getRecord());
		mv.addObject("dataMap", map);
		mv.setViewName("/info/index");
		return mv;
	}
	
	@RequestMapping(value="details")
	public ModelAndView details(String infoId){
		ModelAndView mv = getModelAndView();
		HashMap<String,Object> map = new HashMap<String,Object>();
		InformationDto dataInfo = iInformationService.getInformationById(infoId);
		mv.addObject("dataInfo", dataInfo);
		mv.setViewName("/info/details");
		return mv;
	}
	
	/**
	 * 获取滚动条相关的信息
	 */
	public PageResult<InformationDto> getInfomatation(InformationDto dto,PageQuery query){
		query.setPageSize(10);
		dto.setParentType(InfoType.ZERO.value());
		return iInformationService.findInformationByParam(dto, query);
//		return iAlbumService.findalbumByTop(TOPNUM_FOUR, DataStatus.ENABLED);
	}
	
	/**
	 * 获取积分展示信息
	 */
	public List<CompetitionTeamDto> getRecord(){
		PageQuery query = new PageQuery(10);
		CompetitionTeamDto dto = new CompetitionTeamDto();
		PageResult<CompetitionTeamDto> datas = iCompetitionTeamService.findCompetitionTeamByParam(dto, query);
		return datas.getResults();
	}
	
}

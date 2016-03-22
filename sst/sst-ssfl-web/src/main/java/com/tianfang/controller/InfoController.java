package com.tianfang.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.controller.IndexController.InfoType;
import com.tianfang.message.dto.InformationDto;
import com.tianfang.message.dto.NoticeDto;
import com.tianfang.message.service.IInformationService;
import com.tianfang.message.service.INoticeService;
import com.tianfang.train.dto.CompetitionNoticeDto;
import com.tianfang.train.dto.CompetitionTeamDto;
import com.tianfang.train.service.ICompetitionNoticeService;
import com.tianfang.train.service.ICompetitionTeamService;

@Controller
@RequestMapping(value="info")
public class InfoController extends BaseController{
	
	enum InfoTitle{
		ZERO("官方信息"),ONE("赛事新闻"),TWO("联赛公告");
		private String value = "官方信息";
	    private InfoTitle(String value) {    //    必须是private的，否则编译错误
	        this.value = value;
	    }
	    public String value() {
	        return this.value;
	    }	    
	}

	@Autowired
	private ICompetitionTeamService iCompetitionTeamService;
	
	@Autowired
	private INoticeService iNoticeService;
	/**
	 * 
		 * 此方法描述的是：新闻资讯
		 * @author: cwftalus@163.com
		 * @version: 2016年3月14日 下午2:59:50
	 */
	@Autowired
	private IInformationService iInformationService;
	
	/**
	 * 联赛公告展示信息
	 */
	@Autowired
	private ICompetitionNoticeService iCompetitionNoticeService;
	
	@RequestMapping(value="index")
	public ModelAndView index(InformationDto dto,PageQuery query){
		ModelAndView mv = getModelAndView();
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("pageList",getInfomatation(dto,query));
		map.put("raceRecord",getRecord());
		map.put("infoTitle", InfoTitle.ONE.value);
		mv.addObject("dataMap", map);
		mv.setViewName("/info/index");
		return mv;
	}
	
	@RequestMapping(value="details")
	public ModelAndView details(String infoId){
		ModelAndView mv = getModelAndView();
		InformationDto dataInfo = iInformationService.getInformationById(infoId);
		mv.addObject("dataInfo", dataInfo);
		mv.setViewName("/info/details");
		return mv;
	}
	
	/**
	 * 获取官方展示信息
	 */
	@RequestMapping(value="notice")
	public ModelAndView getAuthInfo(PageQuery query){
		ModelAndView mv = getModelAndView();
		NoticeDto dto = new NoticeDto();
		HashMap<String,Object> map = new HashMap<String,Object>();
		query.setPageSize(10);
		PageResult<NoticeDto> datas = iNoticeService.findNoticeViewByPage(dto, query);
		map.put("infoTitle", InfoTitle.ZERO.value);
		map.put("pageList",datas);
		mv.addObject("dataMap", map);
		mv.setViewName("/info/index");
		return mv;
	}
	
	/**
	 * 获取联赛公告展示信息
	 */
	@RequestMapping(value="race")
	public ModelAndView raceNotice(PageQuery query){
		ModelAndView mv = getModelAndView();
		CompetitionNoticeDto dto = new CompetitionNoticeDto();
		query.setPageSize(10);
		PageResult<CompetitionNoticeDto> datas = iCompetitionNoticeService.findCompNoticeViewByPage(dto, query);
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("infoTitle", InfoTitle.TWO.value);
		map.put("pageList",datas);
		mv.addObject("dataMap", map);
		mv.setViewName("/info/index");
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

package com.tianfang.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Objects;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.HtmlRegexpUtil;
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
		map.put("infoType", InfoTitle.ONE);
		mv.addObject("dataMap", map);
		mv.setViewName("/info/index");
		return mv;
	}
	
	@RequestMapping(value="details")
	public ModelAndView details(String infoType,String infoId){
		ModelAndView mv = getModelAndView();
		Object dataInfo = null;
		if(Objects.equal(infoType, InfoTitle.ZERO.toString())){
			dataInfo = iNoticeService.findNoticeById(infoId);
			mv.addObject("infoTitle", InfoTitle.ZERO.value);
		}else if(Objects.equal(infoType, InfoTitle.ONE.toString())){
			dataInfo = iInformationService.getInformationById(infoId);
			mv.addObject("infoTitle", InfoTitle.ONE.value);
		}else if(Objects.equal(infoType, InfoTitle.TWO.toString())){
			dataInfo = iCompetitionNoticeService.getNoticeById(infoId);
			mv.addObject("infoTitle", InfoTitle.TWO.value);
		}
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
		PageResult<NoticeDto> datas = iNoticeService.queryNoticeViewByPage(dto, query);
		map.put("infoTitle", InfoTitle.ZERO.value);
		map.put("infoType", InfoTitle.ZERO);
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
		map.put("infoType", InfoTitle.TWO);
		map.put("pageList",datas);
		mv.addObject("dataMap", map);
		mv.setViewName("/info/index");
		return mv;
	}
	
	public void changeProperty(PageResult<Object> objects){
		Iterator<Object> its = objects.getResults().iterator();
		while(its.hasNext()){
			Object obj = its.next();
			if(obj instanceof NoticeDto){
				((NoticeDto) obj).setContent(HtmlRegexpUtil.filterHtml(((NoticeDto) obj).getContent()));
			}else if (obj instanceof CompetitionNoticeDto){
				((CompetitionNoticeDto) obj).setContent(HtmlRegexpUtil.filterHtml(((CompetitionNoticeDto) obj).getContent()));
			}
		}
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

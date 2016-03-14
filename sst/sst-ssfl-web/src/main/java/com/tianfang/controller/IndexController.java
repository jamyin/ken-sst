package com.tianfang.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.business.dto.AlbumDto;
import com.tianfang.business.dto.VideoDto;
import com.tianfang.business.service.IAlbumService;
import com.tianfang.business.service.IVideoService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.message.dto.InformationDto;
import com.tianfang.message.dto.NoticeDto;
import com.tianfang.message.service.IInformationService;
import com.tianfang.message.service.INoticeService;
import com.tianfang.train.dto.CompetitionMatchDto;
import com.tianfang.train.dto.CompetitionNoticeDto;
import com.tianfang.train.dto.CompetitionTeamDto;
import com.tianfang.train.service.ICompetitionMatchService;
import com.tianfang.train.service.ICompetitionNoticeService;
import com.tianfang.train.service.ICompetitionRoundService;
import com.tianfang.train.service.ICompetitionTeamService;

/**
 * @author wk.s
 *
 */
@Controller
public class IndexController extends BaseController{
	
	private static Integer TOPNUM = 9 ;
	
	private static Integer TOPNUM_FOUR = 4 ;
	
	private static Integer TOPNUM_NINETEEN = 19 ;
	
	@Autowired
	private ICompetitionTeamService iCompetitionTeamService;
	
	/**
	 * 联赛公告展示信息
	 */
	@Autowired
	private ICompetitionNoticeService iCompetitionNoticeService;
	
	/**
	 * 官方信息
	 */
	@Autowired
	private INoticeService iNoticeService;
	
	@Autowired
	private ICompetitionRoundService roundSerivce;
	@Autowired
	private ICompetitionMatchService matchService;
	
	/**
	 * 视频
	**/
	@Autowired
	private IVideoService iVideoService;
	/**
	 * 相册
	 */
	@Autowired
	private IAlbumService iAlbumService;
	
	/**
	 * 
		 * 此方法描述的是：新闻资讯
		 * @author: cwftalus@163.com
		 * @version: 2016年3月14日 下午2:59:50
	 */
	@Autowired
	private IInformationService iInformationService;
	
	@RequestMapping(value="index")
	public ModelAndView index(){
		ModelAndView mv = getModelAndView();
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("authInfo", getAuthInfo());
		map.put("raceNoteice", getRaceNotice());
		map.put("newRace",getnewRace());
		map.put("newRaceResult",getnewRaceResult());
		map.put("raceRecord",getRecord());
		map.put("videos",getVideos());
		map.put("pictures",getPictures());
		map.put("infomation",getInfomatation());
		mv.addObject("dataMap", map);
		mv.setViewName("/index");
		return mv;
	}
	
	/**
	 * 获取滚动条相关的信息
	 */
	public List<InformationDto> getInfomatation(){
		return iInformationService.findInformationByTop(TOPNUM_NINETEEN, DataStatus.ENABLED);
//		return iAlbumService.findalbumByTop(TOPNUM_FOUR, DataStatus.ENABLED);
	}
	
	/**
	 * 获取官方展示信息
	 */
	public List<NoticeDto> getAuthInfo(){
		PageQuery query = new PageQuery(6); // 公告条数限制为6条
		NoticeDto dto = new NoticeDto();
		PageResult<NoticeDto> datas = iNoticeService.findNoticeViewByPage(dto, query);
		return datas.getResults();
	}
	
	/**
	 * 获取联赛公告展示信息
	 */
	public List<CompetitionNoticeDto> getRaceNotice(){
		PageQuery query = new PageQuery(6); // 公告条数限制为6条
		CompetitionNoticeDto dto = new CompetitionNoticeDto();
		PageResult<CompetitionNoticeDto> datas = iCompetitionNoticeService.findCompNoticeViewByPage(dto, query);
		return datas.getResults();
	}
	
	/**
	 * 获取最新赛程
	 */
	public List<CompetitionMatchDto> getnewRace(){
		int limit = 10;
		return matchService.findMatch(limit,0);//为开始的最新赛事
	}
	/*
	 * 最新赛果
	 */
	public List<CompetitionMatchDto> getnewRaceResult(){
		int limit = 10;
		return matchService.findMatch(limit,2);//为开始的最新赛事
	}
	/**
	 * 获取精彩图集
	 */
	public List<AlbumDto> getPictures(){
		return iAlbumService.findalbumByTop(TOPNUM_FOUR, DataStatus.ENABLED);
	}
	
	/**
	 * 获取视频展示信息
	 */
	public List<VideoDto> getVideos(){
		return iVideoService.findVideoByTop(TOPNUM, DataStatus.ENABLED);
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

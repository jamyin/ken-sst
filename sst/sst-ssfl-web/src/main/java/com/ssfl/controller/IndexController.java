package com.ssfl.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.train.dto.CompetitionNoticeDto;
import com.tianfang.train.dto.CompetitionTeamDto;
import com.tianfang.train.service.ICompetitionNoticeService;
import com.tianfang.train.service.ICompetitionTeamService;

/**
 * @author wk.s
 *
 */
@Controller
public class IndexController {

	@Autowired
	private ICompetitionTeamService cTeamService;
	
	@Autowired
	private ICompetitionNoticeService noticeService;
	
	@RequestMapping("index")
	public String index(){
		
		return "/WEB-INF/page/test";
	}
	
	/**
	 * 获取滚动条相关的信息
	 */
	public void getRollCon(){
		
	}
	
	/**
	 * 获取官方展示信息
	 */
	public void getAuthInfo(){
		
	}
	
	/**
	 * 获取联赛公告展示信息
	 */
	public Response<PageResult<CompetitionNoticeDto>> getRaceNotice(){
		
		Response<PageResult<CompetitionNoticeDto>> response = new Response<PageResult<CompetitionNoticeDto>>();
		PageQuery query = new PageQuery(6); // 公告条数限制为6条
		CompetitionNoticeDto dto = new CompetitionNoticeDto();
		PageResult<CompetitionNoticeDto> datas = noticeService.findCompNoticeViewByPage(dto, query);
		response.setStatus(DataStatus.HTTP_SUCCESS);
		response.setData(datas);
		return response;
	}
	
	/**
	 * 获取最新赛程/最新赛果
	 */
	public void getRaceResult(){
		
	}
	
	/**
	 * 获取精彩图集
	 */
	public void getPictures(){
		
	}
	
	/**
	 * 获取视频展示信息
	 */
	public void getVideos(){
		
	}
	
	/**
	 * 获取积分展示信息
	 */
	public Response<PageResult<CompetitionTeamDto>> getRecord(){
	
		Response<PageResult<CompetitionTeamDto>> response = new Response<PageResult<CompetitionTeamDto>>();
		PageQuery query = new PageQuery(10);
		CompetitionTeamDto dto = new CompetitionTeamDto();
		PageResult<CompetitionTeamDto> datas = cTeamService.findCompetitionTeamByParam(dto, query);
		response.setStatus(DataStatus.HTTP_SUCCESS);
		response.setData(datas);
		
		return response;
	}
	
}

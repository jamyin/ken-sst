package com.tianfang.home.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.business.dto.VideoDto;
import com.tianfang.business.service.IVideoService;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.StringUtils;

@Controller
@RequestMapping("/video")
public class VideoColltroller extends BaseController {
	protected static final Log logger = LogFactory.getLog(VideoColltroller.class);
	
	@Autowired
	private IVideoService videoService;
	
	/**
	 * 上超联赛首页视屏接口
	 * @author YIn
	 * @time:2016年3月2日 下午3:50:34
	 * @param page
	 * @param videoDto
	 * @return
	 */
	@RequestMapping(value="/findAlbumList")
	public PageResult<VideoDto> getVideoPage(ExtPageQuery page,VideoDto videoDto){
		PageResult<VideoDto> pageList = new PageResult<VideoDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		byCriteriaPage(videoDto,map);
		pageList = videoService.getCriteriaPage(page.changeToPageQuery(),map);
		return pageList;
	}
	

	/**
	 * 跳转到视频播页面
	 * @param id  videoId
	 * @return
	 */
	@RequestMapping("/toVideo")
	public ModelAndView toVideo(String id,HttpServletRequest request){
		ModelAndView mv = getModelAndView();
		Map<String,Object> map = new  HashMap<String, Object>();
		map.put("id", id);
		VideoDto Video = videoService.selectById(map);
		Video.setVideo(Video.getVideo().replace("\\", "/"));
		mv.addObject("result", Video);
		mv.setViewName("video/video_details");
		return mv;
	}
	
	/**
	 * 检测条件
	 * @param teamDto
	 * @param map
	 */
	public void byCriteriaPage(VideoDto videoDto,Map<String,Object> map){
		if(!StringUtils.isEmpty(videoDto.getVideoName())){
			map.put("videoName", videoDto.getVideoName());
		}
		if(!StringUtils.isEmpty(videoDto.getPublishPeople())){
			map.put("publishPeople", videoDto.getPublishPeople());
		}
		if(!StringUtils.isEmpty(videoDto.getTeamId())){
			map.put("teamId", videoDto.getTeamId());
		}
		if(!StringUtils.isEmpty(videoDto.getVideoStatus()+"")){
			map.put("videoStatus", videoDto.getVideoStatus());
		}
		if(videoDto.getVideoType()!=null){
			map.put("videoType", videoDto.getVideoType());
		}
	}
	
}

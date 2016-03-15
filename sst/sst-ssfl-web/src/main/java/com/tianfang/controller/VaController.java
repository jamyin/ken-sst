package com.tianfang.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@Controller
@RequestMapping(value = "va")
public class VaController extends BaseController {
	
	private static Integer TOPNUM_EIGHT = 8 ;
	
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
	
	@RequestMapping(value = "index")
	public ModelAndView index() {
		ModelAndView mv = getModelAndView();
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("videos",getVideos());
		map.put("pictures",getPictures());
		
		mv.addObject("dataMap", map);
		mv.setViewName("/va");
		return mv;
	}
	
	@RequestMapping(value = "/v/more")
	public ModelAndView videos(PageQuery page) {
		ModelAndView mv = getModelAndView();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		PageResult<VideoDto> pageList = iVideoService.getCriteriaPage(page, paramMap);
		
		mv.addObject("pageList", pageList);
		mv.setViewName("/va/videos_more");
		return mv;
	}

	@RequestMapping(value = "/a/more")
	public ModelAndView pictures() {
		ModelAndView mv = getModelAndView();
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("videos",getVideos());
		map.put("pictures",getPictures());
		
		mv.addObject("dataMap", map);
		mv.setViewName("/va/pics_more");
		return mv;
	}
	/**
	 * 获取精彩图集
	 */
	public List<AlbumDto> getPictures(){
		return iAlbumService.findalbumByTop(TOPNUM_EIGHT, DataStatus.ENABLED);
	}
	
	/**
	 * 获取视频展示信息
	 */
	public List<VideoDto> getVideos(){
		return iVideoService.findVideoByTop(TOPNUM_EIGHT, DataStatus.ENABLED);
	}
	
}

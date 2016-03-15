package com.tianfang.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.business.dto.AlbumDto;
import com.tianfang.business.dto.AlbumPictureDto;
import com.tianfang.business.dto.VideoDto;
import com.tianfang.business.service.IAlbumPicService;
import com.tianfang.business.service.IAlbumService;
import com.tianfang.business.service.IVideoService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.constants.FuturestarCons;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.dto.PhotoDto;
import com.tianfang.dto.PhotosDto;

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
	
	@Autowired
	private IAlbumPicService iAlbumPicService;
	
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
		page.setPageSize(16);
		PageResult<VideoDto> pageList = iVideoService.getCriteriaPage(page, paramMap);
		
		mv.addObject("pageList", pageList);
		mv.setViewName("/va/videos_more");
		return mv;
	}

	@RequestMapping(value = "/a/more")
	public ModelAndView pictures(PageQuery page) {
		ModelAndView mv = getModelAndView();

		AlbumDto albumDto = new AlbumDto();
		page.setPageSize(16);
		PageResult<AlbumDto> pageList = iAlbumService.findAlbumByPage(albumDto, page);
		
		mv.addObject("pageList", pageList);
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
	
	@RequestMapping("/fpicList")
	@ResponseBody
	public PhotosDto fpicList(String albumId){
		PhotosDto photos = new PhotosDto();
		AlbumDto albumDto = iAlbumService.getAlbumById(albumId);
		photos.setTitle(albumDto.getTitle());

		AlbumPictureDto albumPictureDto = new AlbumPictureDto();
		albumPictureDto.setPicType(FuturestarCons.TRAINTYPE);
		albumPictureDto.setPicStatus(DataStatus.ENABLED);
		albumPictureDto.setAlbumId(albumId);
		List<AlbumPictureDto> dataList = iAlbumPicService.findTeamAlbumPic(albumPictureDto);
		Iterator<AlbumPictureDto> its = dataList.iterator();
		List<PhotoDto> photoList = new ArrayList<PhotoDto>();
		while(its.hasNext()){
			PhotoDto pDto = new PhotoDto();
			AlbumPictureDto to = its.next();
			pDto.setPid(to.getId());
			pDto.setSrc(to.getPic());
			pDto.setThumb(to.getPic());
			photoList.add(pDto);
		}
		photos.setData(photoList);
		return photos; 
	}
	
	
}

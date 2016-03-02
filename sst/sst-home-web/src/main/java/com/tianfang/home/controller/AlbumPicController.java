package com.tianfang.home.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tianfang.business.dto.AlbumPictureDto;
import com.tianfang.business.service.IAlbumPicService;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageResult;

/**
 * @author YIn
 * @time:2016年2月29日 下午3:22:32
 * @ClassName: AlbumPicController
 * @Description: 相片控制器
 * @
 */
@Controller
@RequestMapping(value = "/albumPic")
public class AlbumPicController extends BaseController{
	protected static final Log logger = LogFactory.getLog(AlbumPicController.class);
	
	@Autowired
	private IAlbumPicService iAlbumPicService;
	
	/**
	 * 上超联赛首页查询相册对应相片接口
	 * @author YIn
	 * @time:2015年11月13日 下午5:53:30
	 */
	@RequestMapping(value = "findAlbumPicByPage.do")
	public PageResult<AlbumPictureDto> findAlbumPicByPage(AlbumPictureDto query, ExtPageQuery page) {
		logger.info("AlbumPictureDto query : " + query);
		PageResult<AlbumPictureDto> result = iAlbumPicService.findAlbumPicByPage(query,
				page.changeToPageQuery());
		return result;
	}
}

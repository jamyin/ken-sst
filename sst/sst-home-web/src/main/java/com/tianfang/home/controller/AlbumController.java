package com.tianfang.home.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianfang.business.dto.AlbumDto;
import com.tianfang.business.service.IAlbumService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;

/**
 * @author YIn
 * @time:2016年2月29日 上午11:47:50
 * @ClassName: AlbumController
 * @Description: 相册控制器
 * @
 */
@Controller
@RequestMapping(value = "/album")
public class AlbumController extends BaseController{
	protected static final Log logger = LogFactory.getLog(AlbumController.class);
	
	@Autowired
	private IAlbumService iAlbumService;
	
	/**
	 * 上超联赛首页相册接口-查所有
	 * @author YIn
	 * @time:2016年3月1日 下午3:31:01
	 * @return
	 */
	@RequestMapping(value = "findAlbumList.do")
	@ResponseBody
	public Response<List<AlbumDto>> findAlbumList(AlbumDto dto){
	Response<List<AlbumDto>> result = new Response<List<AlbumDto>>();
	List<AlbumDto> albumList = iAlbumService.findAlbum(dto);
	if(albumList.size()>0){
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setMessage("查询相册图片成功！");
		result.setData(albumList);
		return result;
	}else{
		result.setStatus(DataStatus.HTTP_FAILE);
		result.setMessage("查询相册图片失败！");
		return result;
	}
	}
	
	/**
	 * 上超联赛首页相册接口-分页
	 * @author YIn
	 * @time:2016年3月2日 下午4:02:01
	 * @param query
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "findAlbumByPage.do")
	@ResponseBody
	public Response<PageResult<AlbumDto>> findAlbumByPage(AlbumDto query, ExtPageQuery page) {
		logger.info("AlbumDto query : " + query);
		Response<PageResult<AlbumDto>> res = new Response<PageResult<AlbumDto>>();
		PageResult<AlbumDto> result = iAlbumService.findAlbumByPage(query,
				page.changeToPageQuery());
		if(result != null){
			res.setData(result);
			res.setMessage("查询成功");
			res.setStatus(DataStatus.HTTP_SUCCESS);
			return res;
		}
		res.setMessage("查询失败");
		res.setStatus(DataStatus.HTTP_FAILE);
		return res;
	}
}
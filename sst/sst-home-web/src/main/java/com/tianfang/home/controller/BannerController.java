package com.tianfang.home.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianfang.admin.dto.BannerDto;
import com.tianfang.admin.service.IBannerService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;

/**
 * 轮播图接口
 * @author YIn
 * @time:2016年2月26日 上午10:48:00
 * @ClassName: BannerController
 * @Description: TODO
 * @
 */
@Controller
@ResponseBody
@RequestMapping(value = "/banner")
public class BannerController extends BaseController {
	protected static final Log logger = LogFactory.getLog(BannerController.class);

	@Autowired
	private IBannerService bannerService;
	
	/**
	 * 公告显示页面-分页
	 * @author YIn
	 * @time:2016年2月2日 下午4:54:25
	 * @param BannerDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findBannerView")
    @ResponseBody
	public Response<PageResult<BannerDto>> findBannerViewByPage(BannerDto bannerDto, ExtPageQuery page){
		logger.info("BannerDto  : " + bannerDto);
		Response<PageResult<BannerDto>> response = new Response<PageResult<BannerDto>>();
		PageResult<BannerDto> result = bannerService.findBannerViewByPage(bannerDto, page.changeToPageQuery());
		if(result != null){
			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setData(result);
			response.setMessage("查询成功");
		}else{
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("查询失败");
		}
		return response;
	}
	

	

	
}
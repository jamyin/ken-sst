package com.tianfang.home.controller;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.constants.ShareType;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.message.dto.ActivityDto;
import com.tianfang.message.dto.InformationDto;
import com.tianfang.message.enums.InformationType;
import com.tianfang.message.service.IActivityService;
import com.tianfang.message.service.IInformationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**		
 * <p>Title: InformationController </p>
 * <p>Description: 类描述:新闻/活动/健康食谱接口</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年2月25日下午2:13:48
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping(value = "info")
public class InformationController extends BaseController {
	protected static final Log logger = LogFactory.getLog(InformationController.class);
	
	@Autowired
	private IInformationService infoService;
	@Autowired
	private IActivityService activityService;

	/**
	 * 分享路径
	 */
	private static final String SHARE_URL = "share.do";
	
	/**
	 * 新闻分页列表
	 * @param dto
	 * @param query
	 * @return
	 * @author xiang_wang
	 * 2016年2月25日下午2:23:09
	 */
	@RequestMapping(value = "news")
	@ResponseBody
	public Response<PageResult<InformationDto>> findNews(InformationDto dto, PageQuery query){
		Response<PageResult<InformationDto>> response = new Response<PageResult<InformationDto>>();
		try {
			dto.setParentType(InformationType.NEWS.getIndex());
			PageResult<InformationDto> datas = infoService.findInformationByParam(dto, query);
			appendShareUrl_news(datas);
			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setData(datas);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("系统异常");
			logger.error(e.getMessage());
		}
		
		return response;
	}

	/**
	 * 新闻详情
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2016年2月25日下午2:29:21
	 */
	@RequestMapping(value = "getNews")
	@ResponseBody
	public Response<InformationDto> getNews(String id){
		Response<InformationDto> response = new Response<InformationDto>();
		try {
			InformationDto news = infoService.getInformationById(id);
			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setData(news);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("系统异常");
			logger.error(e.getMessage());
		}
		return response;
	}
	
	/**
	 * 活动分页列表
	 * @param dto
	 * @param query
	 * @return
	 * @author xiang_wang
	 * 2016年2月25日下午2:32:41
	 */
	@RequestMapping(value = "activities")
	@ResponseBody
	public Response<PageResult<ActivityDto>> findActivity(ActivityDto dto, PageQuery query){
		Response<PageResult<ActivityDto>> response = new Response<PageResult<ActivityDto>>();
		try {
			PageResult<ActivityDto> datas = activityService.findActivityByParam(dto, query);
			appendShareUrl_active(datas);
			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setData(datas);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("系统异常");
			logger.error(e.getMessage());
		}
		
		return response;
	}

	/**
	 * 活动详情
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2016年2月25日下午2:32:51
	 */
	@RequestMapping(value = "getActivity")
	@ResponseBody
	public Response<ActivityDto> getActivity(String id){
		Response<ActivityDto> response = new Response<ActivityDto>();
		try {
			ActivityDto news = activityService.getActivityById(id);
			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setData(news);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("系统异常");
			logger.error(e.getMessage());
		}
		return response;
	}
	
	/**
	 * 健康食谱分页列表
	 * @param dto
	 * @param query
	 * @return
	 * @author xiang_wang
	 * 2016年2月25日下午2:36:04
	 */
	@RequestMapping(value = "books")
	@ResponseBody
	public Response<PageResult<InformationDto>> findBooks(InformationDto dto, PageQuery query){
		Response<PageResult<InformationDto>> response = new Response<PageResult<InformationDto>>();
		try {
			dto.setParentType(InformationType.COOKBOOK.getIndex());
			PageResult<InformationDto> datas = infoService.findInformationByParam(dto, query);
			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setData(datas);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("系统异常");
			logger.error(e.getMessage());
		}
		
		return response;
	}
	
	/**
	 * 健康食谱详情
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2016年2月25日下午2:36:25
	 */
	@RequestMapping(value = "getBook")
	@ResponseBody
	public Response<InformationDto> getBook(String id){
		Response<InformationDto> response = new Response<InformationDto>();
		try {
			InformationDto news = infoService.getInformationById(id);
			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setData(news);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("系统异常");
			logger.error(e.getMessage());
		}
		return response;
	}

	private void appendShareUrl_news(PageResult<InformationDto> datas) {
		if (null != datas && null != datas.getResults() && datas.getResults().size() > 0){
			for (InformationDto info : datas.getResults()){
				StringBuilder url = new StringBuilder(SHARE_URL);
				info.setShareUrl(url.append("id="+info.getId()).append("&type="+ ShareType.NEWS.getIndex()).toString());
			}
		}
	}

	private void appendShareUrl_active(PageResult<ActivityDto> datas) {
		if (null != datas && null != datas.getResults() && datas.getResults().size() > 0){
			for (ActivityDto info : datas.getResults()){
				StringBuilder url = new StringBuilder(SHARE_URL);
				info.setShareUrl(url.append("id="+info.getId()).append("&type="+ ShareType.ACTIVE.getIndex()).toString());
			}
		}
	}
}
package com.tianfang.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.dto.AdminDto;
import com.tianfang.admin.dto.BannerDto;
import com.tianfang.admin.service.IBannerService;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.StringUtils;

/**
 * 轮播图Controller
 * @author J. YIn
 * @time:2016年1月15日 上午9:39:09
 * @ClassName: bannerController
 * @Description: TODO
 * @
 */
@Controller
@RequestMapping("/banner")
public class BannerController extends BaseController {

	@Autowired
	private IBannerService bannerService;
	
	/**
	 * 跳转至新增页面
	 * @return
	 */
	@RequestMapping(value = "goAdd.do")
	public ModelAndView goAdd() {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		mv.setViewName("/banner/banner_add");
		return mv;
	}

	/**
	 * 增加轮播图
	 * @author YIn
	 * @time:2016年1月15日 上午10:11:14
	 * @param albumPicDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add")
	public Response<String> addBanner(BannerDto bannerDto){
		Response<String> data = new Response<String>();
		AdminDto admin = getSessionAdmin();
		bannerDto.setCreateAdminId(admin.getId());
		bannerDto.setCreateAdminName(admin.getAccount());
		int flag = bannerService.addBanner(bannerDto);
		if(flag > 0){
			data.setMessage("添加轮播图成功");
			data.setStatus(200);
		}else{
			data.setMessage("添加轮播图失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 跳转至编辑页面
	 * @return
	 */
	@RequestMapping(value = "goEdit.do")
	public ModelAndView goEdit(String id) {
		logger.info("去轮播图修改页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		BannerDto bannerDto = new BannerDto();
		bannerDto.setId(id);
		List<BannerDto> list =bannerService.findBanner(bannerDto);
		try {
			mv.setViewName("/banner/banner_edit");
			mv.addObject("msg", "edit");
			mv.addObject("bannerDto", list.get(0));
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 根据主键Id编辑轮播图
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:05
	 * @param bannerDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/update")
	public Response<String> updateBanner(BannerDto bannerDto){
		Response<String> data = new Response<String>();
		
		int flag = bannerService.updateBanner(bannerDto);
		if(flag > 0){
			data.setMessage("编辑轮播图成功");
			data.setStatus(200);
		}else{
			data.setMessage("编辑轮播图失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:05
	 * @param bannerDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/del")
	public Response<String> delBannerLogic(BannerDto bannerDto){
		Response<String> data = new Response<String>();
		
		int flag = bannerService.delBanner(bannerDto);
		if(flag > 0){
			data.setMessage("删除轮播图成功");
			data.setStatus(200);
		}else{
			data.setMessage("删除轮播图失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id删除 -逻辑删除
	 * @author YIn
	 * @time:2016年1月15日 下午5:54:33
	 * @param bannerDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete.do")
	public Map<String, Object> delBanner(BannerDto bannerDto){
		logger.info("bannerDto"+bannerDto);
		bannerDto.setStat(0); //逻辑删除
		int status = bannerService.updateBanner(bannerDto);
		if(status > 0){
			return MessageResp.getMessage(true,"删除轮播图成功");
		}
			return MessageResp.getMessage(false,"删除轮播图失败");
		
	}
	
	/**
	 * 批量删除
	 * @author YIn
	 * @time:2015年11月16日 下午3:14:51
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/delByIds.do")
    public Map<String, Object> delByIds(String  ids) throws Exception{
	    if (StringUtils.isEmpty(ids)) {
	        return MessageResp.getMessage(false, "请选择需要删除的项！");
	    }
	    Integer resObject =(Integer) bannerService.delByIds(ids);
	    if (resObject == 0) {
            return MessageResp.getMessage(false, "批量删除失败");
        }
	    if (resObject == 1) {
            return MessageResp.getMessage(true, "批量删除成功");
        }
	    return MessageResp.getMessage(false, "删除异常");
	}
	
	/**
	 * 查询轮播图-不分页
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:05
	 * @param bannerDto
	 * @return
	 */
	@RequestMapping(value="/find")
	@ResponseBody
	public Response<List<BannerDto>> findBanner(BannerDto bannerDto){
		Response<List<BannerDto>> data = new Response<List<BannerDto>>();
		
		List<BannerDto> result = bannerService.findBanner(bannerDto);
		if(result.size() > 0){
			data.setMessage("查询轮播图成功");
			data.setStatus(200);
			data.setData(result);
		}else{
			data.setMessage("查询轮播图失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 后台轮播图显示页面-分页
	 * @author YIn
	 * @time:2016年1月15日 下午2:17:03
	 * @param bannerDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findBannerView")
	public ModelAndView findBannerViewByPage(BannerDto bannerDto, ExtPageQuery page){
		logger.info("bannerDto  : " + bannerDto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<BannerDto> result = bannerService.findBannerViewByPage(bannerDto, page.changeToPageQuery());
		mv.addObject("pageList", result);
		mv.addObject("bannerDto", bannerDto);
		mv.setViewName("/banner/banner_list");
		return mv;
	}
	
	
}
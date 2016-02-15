package com.tianfang.admin.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.dto.AdminDto;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.message.dto.InformationDto;
import com.tianfang.message.enums.CookBookType;
import com.tianfang.message.enums.InformationType;
import com.tianfang.message.service.IInformationService;

@Controller
@RequestMapping(value = "info")
public class InformationController extends BaseController{
	protected static final Log logger = LogFactory.getLog(InformationController.class);
	@Autowired
	private IInformationService infoService;
	
	@RequestMapping(value = "list")
	public ModelAndView list(InformationDto dto, ExtPageQuery page){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PageResult<InformationDto> result = infoService.findInformationByParam(dto, page.changeToPageQuery());
        mv.setViewName("/info/list");
        mv.addObject("pageList", result);
        mv.addObject("parentTypes", InformationType.values());
        mv.addObject("subTypes", CookBookType.values());
        mv.addObject("params", dto);
        return mv;
	}
	
	@RequestMapping(value = "add")
	public ModelAndView add(){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		mv.addObject("parentTypes", InformationType.values());
	    mv.addObject("subTypes", CookBookType.values());
        mv.setViewName("/info/add");
        return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="save")
	public Response<String> save(InformationDto dto){
		Response<String> data = new Response<String>();
		try {
			assemblyDatas(dto);
			infoService.save(dto);
			data.setMessage("新增成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			data.setMessage("新增失败");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
		return data;
	}
	
	@RequestMapping(value = "edit")
    public ModelAndView edit(String id) {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        try {
			InformationDto info = infoService.getInformationById(id);
			mv.addObject("info", info);
			mv.addObject("parentTypes", InformationType.values());
		    mv.addObject("subTypes", CookBookType.values());
			mv.setViewName("/info/edit");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			mv.setViewName("500");
		}
        return mv;
    }
	
	@ResponseBody
	@RequestMapping(value="update")
	public Response<String> update(InformationDto dto){
		Response<String> data = new Response<String>();
		try {
			assemblyDatas(dto);
			infoService.update(dto);
			data.setMessage("编辑成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			data.setMessage("编辑失败");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
		return data;
	}
	
	@RequestMapping(value = "delByIds")
    @ResponseBody
    public Response<String> delAdIds(String ids){
		Response<String> data = new Response<String>();
        if (StringUtils.isBlank(ids)) {
        	data.setMessage("请求参数不能为空~");
			data.setStatus(DataStatus.HTTP_FAILE);
            return data;
        }
        try {
			infoService.del(ids);
			data.setMessage("删除成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			data.setMessage("删除失败~");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
        return data;
    }
	
	private void assemblyDatas(InformationDto dto) {
		AdminDto admin = getSessionAdmin();
		dto.setCreateAdminId(admin.getId());
		dto.setCreateAdminName(admin.getAccount());
	}
}
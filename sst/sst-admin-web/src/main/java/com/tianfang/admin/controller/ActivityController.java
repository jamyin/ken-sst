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
import com.tianfang.message.dto.ActivityDto;
import com.tianfang.message.enums.ActivityStatus;
import com.tianfang.message.service.IActivityService;

@Controller
@RequestMapping(value = "activity")
public class ActivityController extends BaseController{
	protected static final Log logger = LogFactory.getLog(ActivityController.class);
	@Autowired
	private IActivityService activityService;
	
	@RequestMapping(value = "list")
	public ModelAndView list(ActivityDto dto, ExtPageQuery page){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PageResult<ActivityDto> result = activityService.findActivityByParam(dto, page.changeToPageQuery());
        mv.setViewName("/activity/list");
        mv.addObject("pageList", result);
        mv.addObject("status", ActivityStatus.values());
        mv.addObject("params", dto);
        return mv;
	}
	
	@RequestMapping(value = "add")
	public ModelAndView add(){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        mv.setViewName("/activity/add");
        mv.addObject("status", ActivityStatus.values());
        return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="save")
	public Response<String> save(ActivityDto dto){
		Response<String> data = new Response<String>();
		try {
			assemblyDatas(dto);
			activityService.save(dto);
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
			ActivityDto activity = activityService.getActivityById(id);
			mv.addObject("activity", activity);
			mv.addObject("status", ActivityStatus.values());
			mv.setViewName("/activity/edit");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			mv.setViewName("500");
		}
        return mv;
    }
	
	@ResponseBody
	@RequestMapping(value="update")
	public Response<String> update(ActivityDto dto){
		Response<String> data = new Response<String>();
		try {
			assemblyDatas(dto);
			activityService.update(dto);
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
			activityService.del(ids);
			data.setMessage("删除成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			data.setMessage("删除失败~");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
        return data;
    }
	
	private void assemblyDatas(ActivityDto dto) {
		AdminDto admin = getSessionAdmin();
		dto.setCreateAdminId(admin.getId());
		dto.setCreateAdminName(admin.getAccount());
	}
}
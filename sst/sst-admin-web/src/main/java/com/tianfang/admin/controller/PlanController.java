package com.tianfang.admin.controller;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.user.dto.PlanDto;
import com.tianfang.user.service.IPlanService;

@Controller
@RequestMapping(value = "plan")
public class PlanController extends BaseController{

	@Autowired
	private IPlanService planService;
	
	@RequestMapping(value = "list")
    public ModelAndView findpage(PlanDto dto, ExtPageQuery page) throws Exception {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PageResult<PlanDto> result = planService.findPlanByParam(dto, page.changeToPageQuery());
        mv.setViewName("/plan/list");
        mv.addObject("pageList", result);
        mv.addObject("params", dto);
        return mv;
    }
	
	@RequestMapping(value = "detail")
    public ModelAndView detail(String id) throws Exception {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PlanDto Plan = planService.getPlanById(id);
        mv.setViewName("/plan/detail");
        mv.addObject("datas", Plan);
        return mv;
    }
	
	@RequestMapping(value = "delAdIds")
    @ResponseBody
    public Map<String, Object> delAdIds(String ids){
        if (StringUtils.isBlank(ids)) {
            return MessageResp.getMessage(false, "请求参数不能为空~");
        }
        Map<String, Object> result;
        try {
        	planService.del(ids);
			result = MessageResp.getMessage(true, "删除成功~");
		} catch (Exception e) {
			e.printStackTrace();
			result = MessageResp.getMessage(false, e.getMessage());
		}
        return result;
    }
}
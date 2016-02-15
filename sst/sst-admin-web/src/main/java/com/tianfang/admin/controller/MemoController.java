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
import com.tianfang.user.dto.MemoDto;
import com.tianfang.user.service.IMemoService;

@Controller
@RequestMapping(value = "memo")
public class MemoController extends BaseController{

	@Autowired
	private IMemoService memoService;
	
	@RequestMapping(value = "list")
    public ModelAndView findpage(MemoDto dto, ExtPageQuery page) throws Exception {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PageResult<MemoDto> result = memoService.findMemoByParam(dto, page.changeToPageQuery());
        mv.setViewName("/memo/list");
        mv.addObject("pageList", result);
        mv.addObject("params", dto);
        return mv;
    }
	
	@RequestMapping(value = "detail")
    public ModelAndView detail(String id) throws Exception {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        MemoDto memo = memoService.getMemoById(id);
        mv.setViewName("/memo/detail");
        mv.addObject("datas", memo);
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
        	memoService.del(ids);
			result = MessageResp.getMessage(true, "删除成功~");
		} catch (Exception e) {
			e.printStackTrace();
			result = MessageResp.getMessage(false, e.getMessage());
		}
        return result;
    }
}
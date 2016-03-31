package com.tianfang.home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;
import com.tianfang.business.dto.SuggestionDto;
import com.tianfang.business.service.ISuggestionService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.Response;

@Controller
@RequestMapping(value = "suggie")
public class SuggestionController extends BaseController {
	
	@Autowired
	private ISuggestionService iSuggestionService;
	
	@RequestMapping(value = "index")
	@ResponseBody
	private Response<String> index(SuggestionDto dto) {
		Response<String> data = new Response<String>();
		
		int result = iSuggestionService.save(dto);
		if(!Objects.equal(result, DataStatus.ENABLED)){
			data.setStatus(DataStatus.HTTP_FAILE);
			data.setMessage("数据保存失败");
			return data;
		}
		data.setMessage("提交成功");
		return data;
	}

}

package com.tianfang.admin.controller.evalua;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.evaluat.dto.EvaluatQuestionDto;
import com.tianfang.evaluat.service.IEvaluatQuestionService;

@Controller
@RequestMapping(value = "/eva/question")
public class EvaluatingQuestionController extends BaseController {

	@Autowired
	private IEvaluatQuestionService iEvaluatQuestionService;

	/**
	 * 
	 * 此方法描述的是：新增页面
	 * 
	 * @author: cwftalus@163.com
	 * @version: 2016年1月29日 下午4:38:53
	 */
	@RequestMapping(value = "addQuestion")
	public ModelAndView addQuestion(EvaluatQuestionDto dto, ExtPageQuery page) {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());

		PageResult<EvaluatQuestionDto> pageList = iEvaluatQuestionService
				.findEvaluatQuesBySql(dto, page.changeToPageQuery());
		mv.addObject("pageList", pageList);
		// List<EvaluatQuestionDto> resultList =
		// iEvaluatQuestionService.findEvaluatQuesBySql(dto);
		// mv.addObject("resultList", resultList);
		mv.addObject("questionDto", dto);
		mv.setViewName("/eva/addQuestion");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "saveColumnBy")
	public Response<String> save(String id, String column_k, String column_v,
			String column_t) {
		Response<String> data = new Response<String>();
		try {
			iEvaluatQuestionService.modifyColumn(id, column_k, column_v,
					column_t);
			data.setMessage("新增成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			data.setMessage("新增失败");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
		return data;
	}

	/**
	 * 
	 * 此方法描述的是：列表
	 * 
	 * @author: cwftalus@163.com
	 * @version: 2016年1月29日 下午4:38:53
	 */

	@RequestMapping(value = "list")
	public ModelAndView list(EvaluatQuestionDto dto, ExtPageQuery page) {
		ModelAndView mv = getModelAndView(this.getSessionUserId());
		List<EvaluatQuestionDto> resultList = iEvaluatQuestionService
				.findEvaluatQuesBySql(dto);
		mv.addObject("resultList", resultList);
		mv.setViewName("/eva/list");
		return mv;
	}

	/**
	 * 
	 * 此方法描述的是：新增页面
	 * 
	 * @author: cwftalus@163.com
	 * @version: 2016年1月29日 下午4:38:53
	 */
	@RequestMapping(value = "add")
	public ModelAndView toAdd() {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());

		mv.setViewName("/eva/add");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "save")
	public Response<String> save(EvaluatQuestionDto dto) {
		Response<String> data = new Response<String>();
		try {
			iEvaluatQuestionService.save(dto);
			data.setMessage("新增成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			data.setMessage("新增失败");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
		return data;
	}

	/**
	 * 
	 * 此方法描述的是：修改页面
	 * 
	 * @author: cwftalus@163.com
	 * @version: 2016年1月29日 下午4:38:53
	 */
	@RequestMapping(value = "edit")
	public ModelAndView toEdit(EvaluatQuestionDto dto) {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());

		EvaluatQuestionDto evaluatQuestionDto = iEvaluatQuestionService
				.findEvaluatQuesById(dto.getId());

		mv.addObject("evaluatQuestionDto", evaluatQuestionDto);
		mv.setViewName("/eva/edit");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "modify")
	public Response<String> modify(EvaluatQuestionDto dto) {
		Response<String> data = new Response<String>();
		try {
			iEvaluatQuestionService.modify(dto);
			data.setMessage("修改成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			data.setMessage("修改失败");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
		return data;
	}

	@RequestMapping(value = "delById")
	@ResponseBody
	public Response<String> delById(String id, Integer svalue) {
		Response<String> data = new Response<String>();
		if (StringUtils.isBlank(id)) {
			data.setMessage("请求参数不能为空~");
			data.setStatus(DataStatus.HTTP_FAILE);
			return data;
		}
		try {
			iEvaluatQuestionService.del(id, svalue);
			data.setMessage("删除成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			data.setMessage("删除失败~");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
		return data;
	}
}

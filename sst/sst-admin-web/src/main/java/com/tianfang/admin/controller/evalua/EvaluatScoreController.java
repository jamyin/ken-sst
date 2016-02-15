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
import com.tianfang.common.model.Response;
import com.tianfang.evaluat.dto.EvaluatScoreDto;
import com.tianfang.evaluat.service.IEvaluatScoreService;

@Controller
@RequestMapping(value = "/eva/score")
public class EvaluatScoreController extends BaseController {
	
	
	@Autowired
	private IEvaluatScoreService iEvaluatScoreService;
	/**
	 * 
		 * 此方法描述的是：列表
		 * @author: cwftalus@163.com
		 * @version: 2016年1月29日 下午4:38:53
	 */
	
	@RequestMapping(value = "addScore")
	public ModelAndView list(EvaluatScoreDto dto, ExtPageQuery page){
		ModelAndView mv = getModelAndView(this.getSessionUserId());
		List<EvaluatScoreDto> resultList = iEvaluatScoreService.findEvaluatScoreBySql(dto);
		mv.addObject("resultList", resultList);
		mv.addObject("evaScoreDto", dto);
        mv.setViewName("/eva/scorelist");
        return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="saveColumnBy")
	public Response<String> save(String id,String column_k,String column_v,String column_t){
		Response<String> data = new Response<String>();
		try {
			iEvaluatScoreService.modifyColumn(id,column_k,column_v,column_t);
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
		 * 此方法描述的是：新增页面
		 * @author: cwftalus@163.com
		 * @version: 2016年1月29日 下午4:38:53
	 */
	@RequestMapping(value = "add")
	public ModelAndView toAdd(){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());

        mv.setViewName("/eva/add");
        return mv;
	}
	
	
	@ResponseBody
	@RequestMapping(value="save")
	public Response<String> save(EvaluatScoreDto dto){
		Response<String> data = new Response<String>();
		try {
			iEvaluatScoreService.save(dto);
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
		 * @author: cwftalus@163.com
		 * @version: 2016年1月29日 下午4:38:53
	 */
	@RequestMapping(value = "edit")
	public ModelAndView toEdit(EvaluatScoreDto dto){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());

		EvaluatScoreDto evaluatDao = iEvaluatScoreService.findEvaluatScoreById(dto.getId());
		
		mv.addObject("evaluatDao",evaluatDao);
        mv.setViewName("/eva/edit");
        return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="modify")
	public Response<String> modify(EvaluatScoreDto dto){
		Response<String> data = new Response<String>();
		try {
			iEvaluatScoreService.modify(dto);
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
    public Response<String> delById(String id,Integer svalue){
		Response<String> data = new Response<String>();
        if (StringUtils.isBlank(id)) {
        	data.setMessage("请求参数不能为空~");
			data.setStatus(DataStatus.HTTP_FAILE);
            return data;
        }
        try {
        	iEvaluatScoreService.del(id,svalue);
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

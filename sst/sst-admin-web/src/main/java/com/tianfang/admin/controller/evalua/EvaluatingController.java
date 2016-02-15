package com.tianfang.admin.controller.evalua;

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
import com.tianfang.evaluat.dto.EvaluatDto;
import com.tianfang.evaluat.service.IEvaluatService;


@Controller
@RequestMapping(value="/eva")
public class EvaluatingController extends BaseController{
	
	
	@Autowired
	private IEvaluatService iEvaluatService;
	/**
	 * 
		 * 此方法描述的是：列表
		 * @author: cwftalus@163.com
		 * @version: 2016年1月29日 下午4:38:53
	 */
	
	@RequestMapping(value = "list")
	public ModelAndView list(EvaluatDto dto, ExtPageQuery page){
		ModelAndView mv = getModelAndView(this.getSessionUserId());
		PageResult<EvaluatDto> pageList = iEvaluatService.findEvaluatBySql(dto,page.changeToPageQuery());
		mv.addObject("pageList", pageList);
		mv.addObject("evaluatDto", dto);
        mv.setViewName("/eva/list");
        return mv;
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
	public Response<String> save(EvaluatDto dto){
		Response<String> data = new Response<String>();
		try {
			iEvaluatService.save(dto);
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
	public ModelAndView toEdit(EvaluatDto dto){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());

		EvaluatDto evaluatDao = iEvaluatService.findEvaluatById(dto.getId());
		
		mv.addObject("evaluatDao",evaluatDao);
        mv.setViewName("/eva/edit");
        return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="modify")
	public Response<String> modify(EvaluatDto dto){
		Response<String> data = new Response<String>();
		try {
			iEvaluatService.modify(dto);
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
        	iEvaluatService.del(id,svalue);
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

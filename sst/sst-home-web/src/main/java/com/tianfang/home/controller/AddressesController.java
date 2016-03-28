package com.tianfang.home.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianfang.business.dto.AddressesDto;
import com.tianfang.business.service.IAddressesService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.Response;


/**
 * @author YIn
 * @time:2016年2月29日 上午11:47:50
 * @ClassName: AddressesController
 * @Description: 地址控制器
 * @
 */
@Controller
@RequestMapping(value = "/address")
public class AddressesController extends BaseController{
	protected static final Log logger = LogFactory.getLog(AddressesController.class);
	
	@Autowired
	private IAddressesService iAddressesService;
	
	/**
	 * 根据上级Id和 LEVEL查询
	 * @author YIn
	 * @time:2016年3月4日 上午10:07:05
	 * @param dto
	 * @describe  查询省:LEVEL = 1 AND parent_id ="1"(parent_id可不传) 
	 * @describe  查询市:LEVEL = 2 AND parent_id ="省份Id" 
	 * @describe  查询区:LEVEL = 3 AND parent_id ="市Id" 
	 * @return
	 */
	@RequestMapping(value = "findAddress")
	@ResponseBody
	public Response<List<AddressesDto>> findAddress(AddressesDto dto){
	Response<List<AddressesDto>> result = new Response<List<AddressesDto>>();
	List<AddressesDto> provinceList = iAddressesService.findAddressList(dto);
	if(provinceList.size()>0){
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setMessage("查询成功！");
		result.setData(provinceList);
		return result;
	}else{
		result.setStatus(DataStatus.HTTP_FAILE);
		result.setMessage("查询失败！");
		return result;
	}
	}
	
}

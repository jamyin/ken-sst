package com.tianfang.business.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tianfang.business.dto.AddressesDto;

/**
 * 
 * @author YIn
 * @time:2016年3月4日 上午10:33:58
 * @ClassName: IAddressesService
 * @Description: TODO
 * @
 */
@Service("iAddressesService")
public interface IAddressesService {

	/**
	 * 根据上级Id和 LEVEL查询
	 * @author YIn
	 * @time:2016年3月4日 上午10:34:45
	 * @describe  查询省:LEVEL = 1 AND parent_id ="1"(parent_id可不传) 
	 * @describe  查询市:LEVEL = 2 AND parent_id ="省份Id" 
	 * @describe  查询区:LEVEL = 3 AND parent_id ="市Id" 
	 * @param dto
	 * @return
	 */
	List<AddressesDto> findAddressList(AddressesDto dto);
	

}

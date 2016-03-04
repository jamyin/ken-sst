package com.tianfang.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.business.dao.AddressesDao;
import com.tianfang.business.dto.AddressesDto;
import com.tianfang.business.pojo.Addresses;
import com.tianfang.business.service.IAddressesService;
import com.tianfang.common.util.BeanUtils;


@Service
public class AddressesServiceImpl implements IAddressesService {

	@Autowired
	private AddressesDao addressesDao;

	/**
	 * @author YIn
	 * @time:2016年3月4日 上午10:39:17
	 */
	@Override
	public List<AddressesDto> findAddressList(AddressesDto dto) {
		Addresses addresses = BeanUtils.createBeanByTarget(dto,Addresses.class);
		List<Addresses> list  = addressesDao.selectByParameter(addresses);
		return BeanUtils.createBeanListByTarget(list, AddressesDto.class);
	}
}

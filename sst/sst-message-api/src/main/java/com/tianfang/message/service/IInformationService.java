package com.tianfang.message.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.message.dto.InformationDto;

public interface IInformationService {

	String save(InformationDto dto);
	
	void del(String id);
	
	void update(InformationDto dto);
	
	InformationDto getInformationById(String id);
	
	List<InformationDto> findInformationByParam(InformationDto dto);
	
	PageResult<InformationDto> findInformationByParam(InformationDto dto, PageQuery query);

	List<InformationDto> findInformationByTop(Integer topNum,Integer enabled,Integer parentType);
}
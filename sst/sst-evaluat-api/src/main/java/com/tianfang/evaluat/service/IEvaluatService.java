package com.tianfang.evaluat.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.evaluat.dto.EvaluatDto;


public interface IEvaluatService {

	PageResult<EvaluatDto> findEvaluatBySql(EvaluatDto dto, PageQuery page);

	int save(EvaluatDto dto);

	EvaluatDto findEvaluatById(String id);

	int modify(EvaluatDto dto);

	void del(String id,Integer svalue);
	
	List<EvaluatDto> findEvaluatBySql(EvaluatDto dto);

}

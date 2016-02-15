package com.tianfang.evaluat.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.evaluat.dto.EvaluatResultDto;


public interface IEvaluatResultService {

	PageResult<EvaluatResultDto> findEvaluatResultBySql(EvaluatResultDto dto, PageQuery page);
	
	List<EvaluatResultDto> findEvaluatResultBySql(EvaluatResultDto dto);

	int save(EvaluatResultDto dto);

	EvaluatResultDto findEvaluatResultById(String id);

	int modify(EvaluatResultDto dto);

	void del(String id,Integer svalue);
	
	void modifyColumn(String id, String column_k, String column_v,String column_t);

}

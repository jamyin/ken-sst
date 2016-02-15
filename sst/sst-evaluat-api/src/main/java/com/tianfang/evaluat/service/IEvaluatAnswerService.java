package com.tianfang.evaluat.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.evaluat.dto.EvaluatAnswerDto;


public interface IEvaluatAnswerService {

	PageResult<EvaluatAnswerDto> findEvaluatAnswerBySql(EvaluatAnswerDto dto, PageQuery page);

	List<EvaluatAnswerDto> findEvaluatAnswerBySql(EvaluatAnswerDto dto);
	
	int save(EvaluatAnswerDto dto);

	EvaluatAnswerDto findEvaluatAnswerById(String id);

	int modify(EvaluatAnswerDto dto);

	void del(String id,Integer svalue);

	void modifyColumn(String id, String column_k, String column_v,String column_t);

}

package com.tianfang.evaluat.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.evaluat.dto.EvaluatQuestionDto;


public interface IEvaluatQuestionService {

	PageResult<EvaluatQuestionDto> findEvaluatQuesBySql(EvaluatQuestionDto dto, PageQuery page);

	List<EvaluatQuestionDto> findEvaluatQuesBySql(EvaluatQuestionDto dto);
	
	int save(EvaluatQuestionDto dto);

	EvaluatQuestionDto findEvaluatQuesById(String id);

	int modify(EvaluatQuestionDto dto);

	void del(String id,Integer svalue);

	void modifyColumn(String id, String column_k, String column_v,String column_t);

}

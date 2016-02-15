package com.tianfang.evaluat.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.evaluat.dto.EvaluatScoreDto;


public interface IEvaluatScoreService {

	PageResult<EvaluatScoreDto> findEvaluatScoreBySql(EvaluatScoreDto dto, PageQuery page);
	
	List<EvaluatScoreDto> findEvaluatScoreBySql(EvaluatScoreDto dto);

	int save(EvaluatScoreDto dto);

	EvaluatScoreDto findEvaluatScoreById(String id);

	int modify(EvaluatScoreDto dto);

	void del(String id,Integer svalue);
	
	void modifyColumn(String id, String column_k, String column_v,String column_t);

}

package com.tianfang.evaluat.mapper;

import org.apache.ibatis.annotations.Param;

public interface EvaluatQuestionExMapper {

	int countScoreTotal(@Param("evaId") String evaId);

}

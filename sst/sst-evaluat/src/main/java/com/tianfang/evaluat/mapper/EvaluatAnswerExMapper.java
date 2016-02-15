package com.tianfang.evaluat.mapper;

import org.apache.ibatis.annotations.Param;

public interface EvaluatAnswerExMapper {

	int countScoreTotal(@Param("evaQuestionId")String evaQuestionId);

}

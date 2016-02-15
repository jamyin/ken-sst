package com.tianfang.evaluat.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class EvaluatQuestionDto implements Serializable{
	@Setter
	@Getter
    private String id;

	@Setter
	@Getter
    private String evaId;

	@Setter
	@Getter
    private String title;

	@Setter
	@Getter
    private Integer sumScore;

	@Setter
	@Getter
    private Integer orderBy;

	@Setter
	@Getter
    private Date createTime;

	@Setter
	@Getter
    private Date lastUpdateTime;

	@Setter
	@Getter
    private Integer stat;
	
	@Setter
	@Getter
	private List<EvaluatAnswerDto> answerList;

   
}
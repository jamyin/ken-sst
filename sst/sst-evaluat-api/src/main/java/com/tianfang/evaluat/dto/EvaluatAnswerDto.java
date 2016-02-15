package com.tianfang.evaluat.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class EvaluatAnswerDto implements Serializable{

	@Setter
	@Getter
    private String id;

	@Setter
	@Getter
    private String evaId;

	@Setter
	@Getter
    private String evaQuestionId;

	@Setter
	@Getter
    private String title;

	@Setter
	@Getter
    private Integer ansScore;

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
}
package com.tianfang.evaluat.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class EvaluatScoreDto implements Serializable{
	@Setter
	@Getter
    private String id;

	@Setter
	@Getter
    private String evaId;

	@Setter
	@Getter
    private Integer startScore;

	@Setter
	@Getter
    private Integer endScore;

	@Setter
	@Getter
    private String abstracts;

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
    private String thumbnail;
}
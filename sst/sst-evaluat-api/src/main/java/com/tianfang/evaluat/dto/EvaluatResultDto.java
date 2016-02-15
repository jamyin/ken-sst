package com.tianfang.evaluat.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class EvaluatResultDto implements Serializable {

	@Setter
	@Getter
	private String id;

	@Setter
	@Getter
	private String userId;
	
	@Setter
	@Getter
	private String evaId;

	@Setter
	@Getter
	private String evaScoreId;

	@Setter
	@Getter
	private Integer sumScore;

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
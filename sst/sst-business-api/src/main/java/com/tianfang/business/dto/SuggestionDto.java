package com.tianfang.business.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SuggestionDto implements Serializable {

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private Integer sfType;

	@Getter
	@Setter
	private String sfFeedback;

	@Getter
	@Setter
	private String sfUserId;

	@Getter
	@Setter
	private Date createTime;

	@Getter
	@Setter
	private Date lastUpdateTime;

	@Getter
	@Setter
	private Integer stat;

}
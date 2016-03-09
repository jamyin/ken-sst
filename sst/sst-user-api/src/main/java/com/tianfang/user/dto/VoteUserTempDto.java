package com.tianfang.user.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class VoteUserTempDto implements Serializable{
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String voteId;

	@Getter
	@Setter
    private String userId;

	@Getter
	@Setter
    private Integer selected;

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
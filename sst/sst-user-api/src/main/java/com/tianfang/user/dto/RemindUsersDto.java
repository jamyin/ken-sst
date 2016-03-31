package com.tianfang.user.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class RemindUsersDto {

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String noticeId;

	@Getter
	@Setter
    private String userId;

	@Getter
	@Setter
    private Integer readstat;

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
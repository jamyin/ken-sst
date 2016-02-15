package com.tianfang.evaluat.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "stat","createTime","lastUpdateTime"})
public class EvaluatDto implements Serializable{
	@Setter
	@Getter
    private String id;

	@Setter
	@Getter
    private String title;

	@Setter
	@Getter
    private String thumbnail;

	@Setter
	@Getter
    private Integer joinNumber;

	@Setter
	@Getter
    private Integer sumScore;

	@Setter
	@Getter
    private String abstracts;

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
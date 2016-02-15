package com.tianfang.train.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author YIn
 * @time:2016年1月15日 下午1:36:29
 * @ClassName: CompetitionNewsDto
 * @Description: TODO
 * @
 */
@JsonIgnoreProperties({"createAdminId","createAdminName","updateAdminId","updateAdminName","createTime","lastUpdateTime","stat"})
public class CompetitionNewsDto implements Serializable{
	
	private static final long serialVersionUID = -1735459507745966109L;

	@Getter
	@Setter
	private String id;
	
	@Getter
	@Setter
    private String compId;
	
	@Getter
	@Setter
    private String compName;   //对应赛事名称
	
	@Getter
	@Setter
    private String title;
	
	@Getter
	@Setter
	private String summary;
	
	@Getter
	@Setter
    private String content;
	
	@Getter
	@Setter
    private String createAdminId;
	
	@Getter
	@Setter
    private String createAdminName;
	
	@Getter
	@Setter
    private String updateAdminId;
	
	@Getter
	@Setter
    private String updateAdminName;
	
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
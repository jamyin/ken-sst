package com.tianfang.train.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author YIn
 * @time:2016年1月21日 下午7:02:56
 * @ClassName: CompetitionApplyDto
 * @Description: TODO
 * @
 */
@JsonIgnoreProperties({"createTime","lastUpdateTime","stat","createTimeStr","lastUpdateTimeStr","compType"})
public class CompetitionApplyDto implements Serializable{
	
	private static final long serialVersionUID = 4933671747979370463L;

	@Getter
	@Setter
	private String id;
	
	@Getter
	@Setter
    private String compId;
    
	@Getter
	@Setter
    private String compName;
	
	@Getter
	@Setter
    private String teamId;
	
	@Getter
	@Setter
    private String teamIcon;
	
	@Getter
	@Setter
    private String teamName;
	
	@Getter
	@Setter
    private String contacts;
	
	@Getter
	@Setter
    private String mobile;
	
	@Getter
	@Setter
    private Integer auditType;
	
	@Getter
	@Setter
    private String createUserId;
	
	@Getter
	@Setter
    private String createUserName;
	
	@Getter
	@Setter
    private String auditUserId;
	
	@Getter
	@Setter
    private String auditUserName;
	
	@Getter
	@Setter
    private Date createTime;
	
	@Getter
	@Setter
    private Date lastUpdateTime;
	
	@Getter
	@Setter
    private Integer stat;
	
	@Getter
	@Setter
	private String createTimeStr; //页面显示创建时间
	
	@Getter
	@Setter
	private String lastUpdateTimeStr; //页面显示更新时间

	@Getter
	@Setter
	private Integer compType; // 用于联表查询,赛事状态(0-未开始,1-进行中,2-已结束)

	@Getter
	@Setter
	private String auditReason;	// 审核理由
}
package com.tianfang.user.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: UserApplyTeamDto </p>
 * <p>Description: 类描述:用户申请球队记录表</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月15日上午10:03:36
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class UserApplyTeamDto implements Serializable{

	private static final long serialVersionUID = 5185425965369123009L;

	@Getter
	@Setter
	private String id;

	/**
	 * 球队id
	 */
	@Getter
	@Setter
    private String teamId;

	/**
	 * 用户id
	 */
	@Getter
	@Setter
    private String userId;

	/**
	 * 申请状态
	 */
	@Getter
	@Setter
    private Integer status;

	/**
	 * 申请理由
	 */
	@Getter
	@Setter
    private String reason; 

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
package com.tianfang.user.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: VoteDto </p>
 * <p>Description: 类描述:投票</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月22日上午9:44:25
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
@JsonIgnoreProperties({"lastUpdateTime","stat","tempId"})
public class VoteDto implements Serializable{
	
	private static final long serialVersionUID = 1277533629330537386L;

	@Setter
	@Getter
	private String id;

	/**
	 * 投票主题
	 */
	@Setter
	@Getter
    private String title;

	/**
	 * 允许投票项数
	 */
	@Setter
	@Getter
    private Integer optionNum;

	/**
	 * 截止时间
	 */
	@Setter
	@Getter
    private Date endTime;
	
	/**
	 * 是否已截止(0-未截止,1-已截止)
	 */
	@Setter
	@Getter
    private Integer overdue;

	/**
	 * 是否匿名
	 */
	@Setter
	@Getter
    private Integer isAnonymous;

	/**
	 * 发起人id
	 */
	@Setter
	@Getter
    private String publishId;

	/**
	 * 发起人
	 */
	@Setter
	@Getter
    private String publishName;

	/**
	 * 总投票次数
	 */
	@Setter
	@Getter
    private Integer amount;

	@Setter
	@Getter
    private Date createTime;		// 创建时间

	@Setter
	@Getter
    private Date lastUpdateTime;	// 最后修改时间

	@Setter
	@Getter
    private Integer stat;			// 数据状态
	
	/**
	 * 参与投票的用户
	 */
	@Getter
	@Setter
	private String userId;
	
	/**
	 * 是否已投票(0-未投, 1-已投)
	 */
	@Getter
	@Setter
	private Integer selected;
	
	/**
	 * 投票用户关联表主键id
	 */
	@Getter
	@Setter
	private String tempId;
}
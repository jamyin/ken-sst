package com.tianfang.user.dto;

import java.io.Serializable;
import java.util.Date;

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
	 * 群组id
	 */
	@Setter
	@Getter
    private String groupId;	

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
	 * 是否匿名
	 */
	@Setter
	@Getter
    private Integer isAnonymous;

	/**
	 * 用户id
	 */
	@Setter
	@Getter
    private String userId;

	/**
	 * 用户名称
	 */
	@Setter
	@Getter
    private String userName;

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
}
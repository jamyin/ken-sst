package com.tianfang.user.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: VoteParams </p>
 * <p>Description: 类描述:投票联表查询参数</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年3月9日上午10:38:46
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class VoteParams implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 投票id
	 */
	@Getter
	@Setter
	private String voteId;

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
}

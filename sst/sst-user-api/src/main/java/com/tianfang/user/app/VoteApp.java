package com.tianfang.user.app;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: VoteApp </p>
 * <p>Description: 类描述:投票提供给移动端的数据封装</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月22日上午9:46:52
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class VoteApp implements Serializable{
	
	private static final long serialVersionUID = 5631601468429101464L;

	/**
	 * 投票id
	 */
	@Getter
	@Setter
	private String id;
	
	/**
	 * 投票主题
	 */
	@Getter
	@Setter
	private String title;
	
	/**
	 * 投票可选项数
	 */
	@Getter
	@Setter
	private Integer optionNum;
	
	/**
	 * 投票截止时间
	 */
	@Getter
	@Setter
	private Date endTime; 
	
	/**
	 * 是否匿名
	 */
	@Getter
	@Setter
	private Integer isAnonymous;
	
	/**
	 * 投票发起用户
	 */
	@Getter
	@Setter
	private String userId;
	
	/**
	 * 投票发起用户名称
	 */
	@Getter
	@Setter
	private String userName; 
	
	/**
	 * 投票选项
	 */
	@Getter
	@Setter
	private List<VoteOptionApp> options; 

	public VoteApp() {
		super();
	}	
	
	public VoteApp(String id, String title, Integer optionNum, Date endTime,
			Integer isAnonymous, String userId, String userName) {
		super();
		this.id = id;
		this.title = title;
		this.optionNum = optionNum;
		this.endTime = endTime;
		this.isAnonymous = isAnonymous;
		this.userId = userId;
		this.userName = userName;
	}
}
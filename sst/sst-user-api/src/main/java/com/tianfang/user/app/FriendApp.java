package com.tianfang.user.app;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: FriendApp </p>
 * <p>Description: 类描述:封装返回给移动端好友信息</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月13日下午3:17:15
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class FriendApp implements Serializable{

	private static final long serialVersionUID = 7418717909653328609L;

	/**
	 * 朋友id
	 */
	@Getter
	@Setter
	private String friendId;
	
	/**
	 * 朋友聊天账户
	 */
	@Getter
	@Setter
	private String friendAccount;
	
	/**
	 * 朋友昵称
	 */
	@Getter
	@Setter
	private String nickName;
	
	/**
	 * 朋友头像
	 */
	@Getter
	@Setter
	private String headImg;
	
	/**
	 * 朋友手机号码
	 */
	@Getter
	@Setter
	private String mobile; 
	
	/**
	 * 用户id
	 */
	@Getter
	@Setter
	private String userId;
	
	/**
	 * 用户聊天账户
	 */
	@Getter
	@Setter
	private String userAccount;	
	
	/**
	 * 特别关注(0-否,1-是)
	 */
	@Getter
	@Setter
	private Integer care;

	public FriendApp() {
		super();
	}

	public FriendApp(String friendId, String friendAccount, String nickName,
			String headImg, String mobile, String userId, String userAccount, Integer care) {
		super();
		this.friendId = friendId;
		this.friendAccount = friendAccount;
		this.nickName = nickName;
		this.headImg = headImg;
		this.mobile = mobile;
		this.userId = userId;
		this.userAccount = userAccount;
		this.care = care;
	}	
}
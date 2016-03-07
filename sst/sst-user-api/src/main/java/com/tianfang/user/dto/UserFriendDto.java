package com.tianfang.user.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: UserFriendDto </p>
 * <p>Description: 类描述:用户好友信息</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月13日下午3:18:42
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class UserFriendDto implements Serializable{

	private static final long serialVersionUID = 2744290824950983659L;
	
	@Getter
	@Setter
	private String id;

	/**
	 * 用户id
	 */
	@Getter
	@Setter
    private String userId;

	/**
	 * 朋友id
	 */
	@Getter
	@Setter
    private String friendId;
	
	/**
	 * 特别关心(0-否,1-是)
	 */
	@Getter
	@Setter
	private Integer care;

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
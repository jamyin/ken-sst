package com.tianfang.user.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: GroupUserDto </p>
 * <p>Description: 类描述:群组用户表</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月15日上午10:02:03
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class GroupUserDto implements Serializable{

	private static final long serialVersionUID = -4566954126411069736L;

	@Getter
	@Setter
	private String id;

	/**
	 * 群组id
	 */
	@Getter
	@Setter
    private String groupId;

	/**
	 * 用户id
	 */
	@Getter
	@Setter
    private String userId;

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
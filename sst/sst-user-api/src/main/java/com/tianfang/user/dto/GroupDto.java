package com.tianfang.user.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: GroupDto </p>
 * <p>Description: 类描述:群组表</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月15日上午10:00:44
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class GroupDto implements Serializable {

	private static final long serialVersionUID = 3930894697462327036L;

	@Getter
	@Setter
	private String id;

	/**
	 * 群组名称
	 */
	@Getter
	@Setter
    private String name;

	/**
	 * 创建人id
	 */
	@Getter
	@Setter
    private String createUserId;
	
	/**
	 * 创建人名称
	 */
	@Getter
	@Setter
	private String createUserName;

	/**
	 * 创建时间
	 */
	@Getter
	@Setter
    private Date createTime;

	@Getter
	@Setter
    private Date lastUpdateTime;

	@Getter
	@Setter
    private Integer stat;
	
	/**
	 * 群内所有用户信息
	 */
	@Getter
	@Setter
	private List<UserDto> users;
}
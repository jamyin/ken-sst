package com.tianfang.user.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: MemoDto </p>
 * <p>Description: 类描述:用户备忘</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月14日下午2:34:32
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class MemoDto implements Serializable{
	
	private static final long serialVersionUID = 5485444058704195260L;

	@Getter
	@Setter
	private String id;

	/**
	 * 内容
	 */
	@Getter
	@Setter
	private String content;

	/**
	 * 用户id
	 */
	@Getter
	@Setter
    private String userId;
	
	/**
	 * 用户名称
	 */
	@Getter
	@Setter
	private String userName;

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
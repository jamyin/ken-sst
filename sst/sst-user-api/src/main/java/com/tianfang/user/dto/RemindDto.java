
package com.tianfang.user.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: RemindDto </p>
 * <p>Description: 类描述:用户提醒</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月14日下午2:41:54
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class RemindDto implements Serializable{

	private static final long serialVersionUID = 7546962713671232842L;
	
	@Getter
	@Setter
    private String id;

	/**
	 * 接收人id
	 */
	@Getter
	@Setter	
    private String toUserId;
	
	/**
	 * 接收人名称
	 */
	@Getter
	@Setter
	private String toUserName;

	/**
	 * 提醒方式
	 */
	@Getter
	@Setter
    private Integer type;

	/**
	 * 发送时间
	 */
	@Getter
	@Setter
    private String sendTime; 

	/**
	 * 附件
	 */
	@Getter
	@Setter
    private String attachment;
	
	/**
	 * 文字
	 */
	@Getter
	@Setter
    private String text; 

	/**
	 * 语音
	 */
	@Getter
	@Setter
    private String voice; 

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
	
	
	@Getter
	@Setter
    private String [] userIds;   //接收公告者数组
	
	
	@Getter
	@Setter
    private String [] jIds;   //接收用户的聊天账号  即电话号码
}
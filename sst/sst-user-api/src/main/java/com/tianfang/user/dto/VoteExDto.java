package com.tianfang.user.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: VoteExDto </p>
 * <p>Description: 类描述:投票表自定义sql查询封装数据</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月14日上午11:33:41
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class VoteExDto implements Serializable{

	private static final long serialVersionUID = 6449240502056394342L;
	
	/**
	 * 投票id
	 */
	@Getter
	@Setter
	private String voteId;
	
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
	private String publishId;
	
	/**
	 * 投票发起用户名称
	 */
	@Getter
	@Setter
	private String publishName;
	
	/**
	 * 总投票次数
	 */
	@Getter
	@Setter
	private Integer amount;
	
	/**
	 * 选项id
	 */
	@Getter
	@Setter
	private String optionId;
	
	/**
	 * 选项文本
	 */
	@Getter
	@Setter
	private String optionText;
	
	/**
	 * 选项图片
	 */
	@Getter
	@Setter
	private String optionPic;
	
	/**
	 * 投票用户id
	 */
	@Getter
	@Setter
	private String optionUserId;
	
	/**
	 * 投票用户昵称
	 */
	@Getter
	@Setter
	private String optionUserNickName;
	
	/**
	 * 投票用户头像
	 */
	@Getter
	@Setter
	private String optionUserPic;
}
package com.tianfang.user.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: VoteOptionDto </p>
 * <p>Description: 类描述:投票选项</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月14日下午2:58:22
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
@JsonIgnoreProperties({"lastUpdateTime","stat"})
public class VoteOptionDto implements Serializable{

	private static final long serialVersionUID = -6785591537825131924L;
	
	@Getter
	@Setter
    private String id;

	/**
	 * 投票id
	 */
	@Getter
	@Setter
    private String voteId;

	/**
	 * 选项描述
	 */
	@Getter
	@Setter
    private String text; 
	
	/**
	 * 选项图片
	 */
	@Getter
	@Setter
	private String pic;

	/**
	 * 投票次数
	 */
	@Getter
	@Setter
    private Integer num;

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
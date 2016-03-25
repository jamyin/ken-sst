package com.tianfang.user.app;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**		
 * <p>Title: VoteOptionApp </p>
 * <p>Description: 类描述:提供移动的投票选项封装</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月22日上午9:49:08
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class VoteOptionApp implements Serializable{

	private static final long serialVersionUID = -5268737386615984580L;
	
	/**
	 * 选项id
	 */
	@Getter
	@Setter
	private String id;
	
	/**
	 * 选项文本
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
	 * 选项百分比
	 */
	@Getter
	@Setter
	private Double percent;

	/**
	 * 已投票用户
	 */
	@Getter
	@Setter
	private List<OptionUserApp> users;

	public VoteOptionApp() {
		super();
	}
	
	public VoteOptionApp(String id, String text, String pic) {
		super();
		this.id = id;
		this.text = text;
		this.pic = pic;
	}
}
package com.tianfang.user.app;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: OptionUserApp </p>
 * <p>Description: 类描述:投票用户</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月22日上午9:46:13
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class OptionUserApp implements Serializable{
	
	private static final long serialVersionUID = 5099248652961166453L;
	
	/**
	 * 用户id
	 */
	@Getter
	@Setter
	private String id;
	
	/**
	 * 用户昵称
	 */
	@Getter
	@Setter
	private String nickName;
	
	/**
	 * 用户头像
	 */
	@Getter
	@Setter
	private String pic;

	public OptionUserApp() {
		super();
	}
	
	public OptionUserApp(String id, String nickName, String pic) {
		this.id = id;
		this.nickName = nickName;
		this.pic = pic;
	}
}
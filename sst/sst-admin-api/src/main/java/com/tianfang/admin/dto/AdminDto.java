package com.tianfang.admin.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: AdminDto </p>
 * <p>Description: 类描述:后台管理员</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月12日上午9:04:48
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class AdminDto implements Serializable {
	private static final long serialVersionUID = -6141729572168873700L;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
    private String account;
	
	@Getter
	@Setter
    private String pwd;
    
	@Getter
	@Setter
    private String confirmPwd;
    
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
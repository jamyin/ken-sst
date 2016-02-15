package com.tianfang.user.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: VoteUserOptionDto </p>
 * <p>Description: 类描述:用户投票结果</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月22日上午9:44:37
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class VoteUserOptionDto implements Serializable {

	private static final long serialVersionUID = -5211657415001773090L;

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
	 * 用户id
	 */
	@Getter
	@Setter
    private String userId; 

	/**
	 * 选项id
	 */
	@Getter
	@Setter
    private String optionId;

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
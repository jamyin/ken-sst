package com.tianfang.user.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: NotificationsDto </p>
 * <p>Description: 类描述:用户安全提醒</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年3月16日上午9:33:12
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class NotificationsDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**		
	 * <p>Title: Reminding </p>
	 * <p>Description: 类描述:安全设置提醒方式</p>
	 * <p>Copyright (c) 2015 </p>
	 * <p>Company: 上海天坊信息科技有限公司</p>
	 * @author xiang_wang	
	 * @date 2016年3月16日上午10:33:11
	 * @version 1.0
	 * <p>修改人：</p>
	 * <p>修改时间：</p>
	 * <p>修改备注：</p>
	 */
	public enum Reminding{
		Phone(1),Email(2),None(3);
		
		private int state;

		private Reminding(int state) {
			this.state = state;
		}

		public Integer getState() {
			return state;
		}
	}

	@Getter
	@Setter
    private String id;

	/**
	 *  修改密码(1.手机提醒  2. 邮件提醒  3. 不提醒)
	 */
	@Getter
	@Setter
    private Integer upPasswordStat;

	/**
	 * 登录提醒(1.手机提醒  2. 邮件提醒  3. 不提醒)
	 */
	@Getter
	@Setter
    private Integer loginStat;

	/**
	 * 异地登录状态(1.手机提醒  2. 邮件提醒  3. 不提醒)
	 */
	@Getter
	@Setter
    private Integer nonlocalLoginStat;

	/**
	 * 用户绑定邮箱
	 */
	@Getter
	@Setter
    private String email;

	/**
	 * 数据状态
	 */
	@Getter
	@Setter
    private Integer stat;

	/**
	 * 所属用户
	 */
	@Getter
	@Setter
    private String ownerId;
}
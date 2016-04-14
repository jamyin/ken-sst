package com.tianfang.train.dto;

import java.io.Serializable;
import java.util.Date;

import com.tianfang.common.constants.TeamPlayerPositionEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>Description: 球队成员信息 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author wangxiang
 * @date 16/4/14 上午10:09
 * @version 1.0
 */
public class TeamPlayerDto implements Serializable{

	private static final long serialVersionUID = 6770591068459495730L;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String teamId;
	
	@Getter
	@Setter
	private String teamName;

	@Getter
	@Setter
	private String userId;

	/**
	 * 用户真实姓名
	 */
	@Getter
	@Setter
	private String name;

	/**
	 * 年龄
	 */
	@Getter
	@Setter
	private Integer age;

	/**
	 * 身份证号
	 */
	@Getter
	@Setter
	private String cardNo;

	/**
	 * 手机号码
	 */
	@Getter
	@Setter
	private String mobile;

	/**
	 * 学校
	 */
	@Getter
	@Setter
	private String school;

	/**
	 * 学籍学号
	 */
	@Getter
	@Setter
	private String studentNo;

	@Getter
	@Setter
	private String pic;

	/**
	 * 性别(1.男  2.女)
	 */
	@Getter
	@Setter
	private Integer gender;

	/**
	 * 球号
	 */
	@Getter
	@Setter
	private Integer num;

	/**
	 * 团队的位置
	 */
	@Getter
	private Integer position;

	public void setPosition(Integer position) {
		this.position = position;
		if (null != position && position.intValue() > 0){
			setPositionStr(TeamPlayerPositionEnum.getName(position));
		}
	}

	/**
	 * 在团队中的位置中文描述，比如左边锋
	 */
	@Setter
	@Getter
	private String positionStr;

	/**
	 * 球员描述
	 */
	@Getter
	@Setter
	private String summary;

	/**
	 * 用户类型(1，普通会员；2，教练；3，队长)
	 */
	@Getter
	@Setter
	private Integer utype;

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
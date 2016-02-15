package com.tianfang.train.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties({"createUserId","createUserName","updateUserId","updateUserName","createTime","lastUpdateTime","stat","createTimeStr","lastUpdateTimeStr","checked"})
public class TeamDto implements Serializable {
	private static final long serialVersionUID = 4359295263377728811L;
	
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
    private String name;			// 球队名称

	@Getter
	@Setter
    private String icon;			// 图标

	@Getter
	@Setter
    private String summary;			// 简介
    
	@Getter
	@Setter
    private String province;
    
	@Getter
	@Setter
    private String city;
    
	@Getter
	@Setter
    private String area;
    
	@Getter
	@Setter
    private String address;
    
	@Getter
	@Setter
    private String contacts;
    
	@Getter
	@Setter
    private String mobile;
    
	@Getter
	@Setter
    private String createUserId;	// 创建人id
	
	@Getter
	@Setter
    private String createUserName;	// 创建人姓名

	@Getter
	@Setter
    private String updateUserId;	// 更新人id

	@Getter
	@Setter
    private String updateUserName;	// 更新人姓名

	@Getter
	@Setter
    private Date createTime;		// 创建时间

	@Getter
	@Setter
    private Date lastUpdateTime;	// 最后更新时间

	@Getter
	@Setter
    private Integer stat;			// 数据状态
	
	@Getter
    @Setter
    private boolean checked; 		// 是否选中(用于用户球队关联操作树展示)
	
	@Getter
	@Setter
	private String createTimeStr; //页面显示创建时间
	
	
	@Getter
	@Setter
	private String lastUpdateTimeStr;//页面显示更新时间

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * 重写equals方法用于list集合删除赛事报名成功数据
	 * @author xiang_wang	
	 * @date 2016年1月28日下午3:02:53
	 * @version 1.0
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof TeamDto){
			TeamDto i = (TeamDto)obj;
			if (null != i.getId() && null != this.getId() && this.getId().equals(i.getId())){
				return true;
			}else{
				return false;
			}
		} else if (obj instanceof CompetitionTeamDto){
			CompetitionTeamDto i = (CompetitionTeamDto)obj;
			if (null != i.getTeamId() && null != i.getId() && this.getId().equals(i.getTeamId())){
				return true;
			}else{
				return false;
			}
		} else{
			return false;
		}
	} 
}
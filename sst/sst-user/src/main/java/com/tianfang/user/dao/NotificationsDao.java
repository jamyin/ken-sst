package com.tianfang.user.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.user.dto.NotificationsDto;
import com.tianfang.user.mapper.NotificationsMapper;
import com.tianfang.user.pojo.Notifications;
import com.tianfang.user.pojo.NotificationsExample;

@Repository
public class NotificationsDao extends MyBatisBaseDao<Notifications> {
	
	@Getter
	@Autowired
	private NotificationsMapper mapper;
	
	public List<NotificationsDto> findNotificationsByParam(NotificationsDto dto){
		return findNotificationsByParam(dto, null);
	}
	
	public List<NotificationsDto> findNotificationsByParam(NotificationsDto dto, PageQuery query) {
		NotificationsExample example = new NotificationsExample();
		NotificationsExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("create_time desc limit "+query.getStartNum() +"," + query.getPageSize());
		}
        List<Notifications> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, NotificationsDto.class);
	}
	
	public int countNotificationsByParam(NotificationsDto dto){
		NotificationsExample example = new NotificationsExample();
		NotificationsExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
		return mapper.countByExample(example);
	}
	
	/**
	 * 组装查询参数
	 * @param params
	 * @param criteria
	 * @author xiang_wang
	 * 2016年1月12日下午4:51:12
	 */
	private void assemblyParams(NotificationsDto params, NotificationsExample.Criteria criteria) {
		if (null != params) {
        	if (StringUtils.isNotBlank(params.getId())){
        		criteria.andIdEqualTo(params.getId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getOwnerId())){
        		criteria.andOwnerIdEqualTo(params.getOwnerId().trim());
        	}
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}
}
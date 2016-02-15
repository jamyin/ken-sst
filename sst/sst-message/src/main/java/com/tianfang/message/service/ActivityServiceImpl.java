package com.tianfang.message.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.message.dao.ActivityDao;
import com.tianfang.message.dto.ActivityDto;
import com.tianfang.message.pojo.Activity;

@Service
public class ActivityServiceImpl implements IActivityService {

	@Autowired
	private ActivityDao activityDao;
	
	@Override
	public String save(ActivityDto dto) {
		checkObjIsNull(dto);
		Activity obj = BeanUtils.createBeanByTarget(dto, Activity.class);
		String id = UUIDGenerator.getUUID();
		obj.setId(id);
		activityDao.insertSelective(obj);
		return id;
	}

	@Override
	public void del(String id) {
		checkIdIsNull(id);
		Activity obj = activityDao.selectByPrimaryKey(id);
		checkObjNotExist(obj);
		if (null != obj.getStat() && obj.getStat() == DataStatus.DISABLED){
			return;
		}
		obj.setStat(DataStatus.DISABLED);
		activityDao.updateByPrimaryKeySelective(obj);
	}

	@Override
	public void update(ActivityDto dto) {
		checkObjIsNull(dto);
		checkIdIsNull(dto.getId());
		Activity obj = BeanUtils.createBeanByTarget(dto, Activity.class);
		activityDao.updateByPrimaryKeySelective(obj);
	}

	@Override
	public ActivityDto getActivityById(String id) {
		checkIdIsNull(id);
		Activity obj = activityDao.selectByPrimaryKey(id);
		if (null != obj && obj.getStat() == DataStatus.ENABLED){
			ActivityDto dto = BeanUtils.createBeanByTarget(obj, ActivityDto.class);
			return dto;
		}
		return null;
	}

	@Override
	public List<ActivityDto> findActivityByParam(
			ActivityDto dto) {
		List<ActivityDto> list = activityDao.findActivityByParam(dto);
		if (null != list && list.size() > 0){
			return BeanUtils.createBeanListByTarget(list, ActivityDto.class);
		}
		return null;
	}

	@Override
	public PageResult<ActivityDto> findActivityByParam(
			ActivityDto dto, PageQuery query) {
		int total = activityDao.countActivityByParam(dto);
		if (total > 0){
			query.setTotal(total);
			List<ActivityDto> results = activityDao.findActivityByParam(dto, query);
			return new PageResult<ActivityDto>(query, results);
		}
		return null;
	}

	private void checkObjIsNull(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,活动对象为空!");
		}
	}
	
	private void checkIdIsNull(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,活动对象主键ID为空!");
		}
	}
	
	private void checkObjNotExist(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,活动对象不存在!");
		}
	}
}
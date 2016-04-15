package com.tianfang.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.user.dao.RemindDao;
import com.tianfang.user.dto.RemindDto;
import com.tianfang.user.pojo.Remind;

@Service
public class RemindServiceImpl implements IRemindService {
	
	@Autowired
	private RemindDao remindDao;

	@Override
	public String save(RemindDto dto) throws Exception {
		checkObjIsNull(dto);
		Remind m = BeanUtils.createBeanByTarget(dto, Remind.class);
		String id = UUIDGenerator.getUUID();
		m.setId(id);
		remindDao.insertSelective(m);
		return id;
	}

	@Override
	public void del(String id) throws Exception {
		checkIdIsNull(id);
		Remind m = remindDao.selectByPrimaryKey(id);
		checkObjIsNotExist(m);
		m.setStat(DataStatus.DISABLED);
		remindDao.updateByPrimaryKeySelective(m);
	}

	@Override
	public void update(RemindDto dto) throws Exception {
		checkObjIsNull(dto);
		checkIdIsNull(dto.getId());
		checkObjIsNotExist(remindDao.selectByPrimaryKey(dto.getId()));
		Remind m = BeanUtils.createBeanByTarget(dto, Remind.class);
		remindDao.updateByPrimaryKeySelective(m);
	}

	@Override
	public RemindDto getRemindById(String id) throws Exception {
		checkIdIsNull(id);
		Remind m = remindDao.selectByPrimaryKey(id);
		RemindDto dto = BeanUtils.createBeanByTarget(m, RemindDto.class);
		return dto;
	}

	@Override
	public List<RemindDto> findRemindByParam(RemindDto dto) throws Exception {
		return remindDao.findRemindByParam(dto);
	}

	@Override
	public PageResult<RemindDto> findRemindByParam(RemindDto dto, PageQuery query)
			throws Exception {
		int total = remindDao.countByExample(dto);
		if (total > 0){
			query.setTotal(total);
			List<RemindDto> results = remindDao.selectByExample(dto, query);
			return new PageResult<RemindDto>(query, results);
		}
		
		return null;
	}

	private void checkObjIsNull(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,用户提醒对象为空!");
		}
	}
	
	private void checkIdIsNull(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,用户提醒对象主键ID为空!");
		}
	}
	
	private void checkObjIsNotExist(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,用户提醒对象不存在!");
		}
	}

	@Override
	public PageResult<RemindDto> findRemindByParam(String userId,PageQuery query) {
		int total = remindDao.countRemindByParam(userId);
		if (total > 0){
			query.setTotal(total);
			List<RemindDto> results = remindDao.findRemindByParam(userId, query);
			return new PageResult<RemindDto>(query, results);
		}
		
		return null;
	}
}
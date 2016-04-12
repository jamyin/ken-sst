package com.tianfang.user.service;

import java.util.List;

import com.tianfang.user.app.AppUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.user.dao.UserApplyTeamDao;
import com.tianfang.user.dto.UserApplyTeamDto;
import com.tianfang.user.pojo.UserApplyTeam;

@Service
public class UserApplyTeamServiceImpl implements IUserApplyTeamService {

	@Autowired
	private UserApplyTeamDao userApplyTeamDao;

	@Override
	public String save(UserApplyTeamDto dto) {
		checkObjIsNull(dto);
		UserApplyTeam m = BeanUtils.createBeanByTarget(dto, UserApplyTeam.class);
		String id = UUIDGenerator.getUUID();
		m.setId(id);
		userApplyTeamDao.insertSelective(m);
		return id;
	}

	@Override
	public void del(String id) {
		checkIdIsNull(id);
		UserApplyTeam m = userApplyTeamDao.selectByPrimaryKey(id);
		checkObjIsNotExist(m);
		m.setStat(DataStatus.DISABLED);
		userApplyTeamDao.updateByPrimaryKeySelective(m);
	}

	@Override
	public void update(UserApplyTeamDto dto) {
		checkObjIsNull(dto);
		checkIdIsNull(dto.getId());
		checkObjIsNotExist(userApplyTeamDao.selectByPrimaryKey(dto.getId()));
		UserApplyTeam m = BeanUtils.createBeanByTarget(dto, UserApplyTeam.class);
		userApplyTeamDao.updateByPrimaryKeySelective(m);
	}

	@Override
	public UserApplyTeamDto getUserApplyTeamById(String id) {
		checkIdIsNull(id);
		UserApplyTeam m = userApplyTeamDao.selectByPrimaryKey(id);
		UserApplyTeamDto dto = BeanUtils.createBeanByTarget(m, UserApplyTeamDto.class);
		return dto;
	}

	@Override
	public List<UserApplyTeamDto> findUserApplyTeamByParam(UserApplyTeamDto dto){
		return userApplyTeamDao.findUserApplyTeamByParam(dto);
	}

	@Override
	public PageResult<UserApplyTeamDto> findUserApplyTeamByParam(UserApplyTeamDto dto, PageQuery query){
		int total = userApplyTeamDao.countUserApplyTeamByParam(dto);
		if (total > 0){
			query.setTotal(total);
			List<UserApplyTeamDto> results = userApplyTeamDao.findUserApplyTeamByParam(dto, query);
			return new PageResult<UserApplyTeamDto>(query, results);
		}
		
		return null;
	}

	@Override
	public PageResult<UserApplyTeamDto> findUserApplyTeamExByParam( UserApplyTeamDto dto, PageQuery query) {
		int total = userApplyTeamDao.countUserApplyTeamExByParam(dto);
		if (total > 0){
			query.setTotal(total);
			List<UserApplyTeamDto> results = userApplyTeamDao.findUserApplyTeamExByParam(dto, query);
			return new PageResult<UserApplyTeamDto>(query, results);
		}
		
		return null;
	}

	@Override
	public AppUserInfo getUserApplyInfoById(String id) {
		checkIdIsNull(id);
		return userApplyTeamDao.getUserApplyInfoById(id);
	}

	@Override
	public PageResult<AppUserInfo> queryUserApplyInfoByParam(AppUserInfo param, PageQuery query) {
		int total = userApplyTeamDao.countUserApplyInfoByParam(param);
		if (total > 0){
			query.setTotal(total);
			List<AppUserInfo> datas = userApplyTeamDao.queryUserApplyInfoByParam(param, query);
			PageResult<AppUserInfo> result = new PageResult<>(query,datas);

			return result;
		}

		return null;
	}

	public PageResult<AppUserInfo> queryUserTeamApplyInfoByParam(UserApplyTeamDto dto,PageQuery query){
		int total = userApplyTeamDao.countUserTeamApplyInfoByParam(dto);
		if (total > 0) {
			query.setTotal(total);
			List<AppUserInfo> datas = userApplyTeamDao.queryUserTeamApplyInfoByParam(dto, query);
			PageResult<AppUserInfo> result = new PageResult<>(query,datas);
			return result;
		}
		return null;
	}
	
	@Override
	public List<AppUserInfo> queryUserApplyInfoByParam(AppUserInfo param) {
		return userApplyTeamDao.queryUserApplyInfoByParam(param, null);
	}

	private void checkObjIsNull(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,用户申请球队记录对象为空!");
		}
	}
	
	private void checkIdIsNull(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,用户申请球队记录对象主键ID为空!");
		}
	}
	
	private void checkObjIsNotExist(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,用户申请球队记录对象不存在!");
		}
	}
}
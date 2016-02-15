package com.tianfang.train.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.train.dao.TeamResultDao;
import com.tianfang.train.dto.TeamResultDto;
import com.tianfang.train.pojo.TeamResult;

@Service
public class TeamResultServiceImpl implements ITeamResultService {
	
	@Autowired
	private TeamResultDao resultDao;

	@Override
	public String save(TeamResultDto dto) {
		checkObjIsNull(dto);
		TeamResult obj = BeanUtils.createBeanByTarget(dto, TeamResult.class);
		String id = UUIDGenerator.getUUID();
		obj.setId(id);
		resultDao.insertSelective(obj);
		return id;
	}

	@Override
	public void del(String id) {
		checkIdIsNull(id);
		TeamResult obj = resultDao.selectByPrimaryKey(id);
		checkObjNotExist(obj);
		if (null != obj.getStat() && obj.getStat() == DataStatus.DISABLED){
			return;
		}
		obj.setStat(DataStatus.DISABLED);
		resultDao.updateByPrimaryKeySelective(obj);
	}

	@Override
	public void update(TeamResultDto dto) {
		checkObjIsNull(dto);
		checkIdIsNull(dto.getId());
		TeamResult obj = BeanUtils.createBeanByTarget(dto, TeamResult.class);
		resultDao.updateByPrimaryKeySelective(obj);
	}

	@Override
	public TeamResultDto getTeamResultById(String id) {
		checkIdIsNull(id);
		TeamResult obj = resultDao.selectByPrimaryKey(id);
		if (null != obj && obj.getStat() == DataStatus.ENABLED){
			TeamResultDto dto = BeanUtils.createBeanByTarget(obj, TeamResultDto.class);
			return dto;
		}
		return null;
	}

	@Override
	public List<TeamResultDto> findTeamResultByParam(TeamResultDto dto) {
		List<TeamResultDto> list = resultDao.findTeamResultByParam(dto);
		if (null != list && list.size() > 0){
			return BeanUtils.createBeanListByTarget(list, TeamResultDto.class);
		}
		return null;
	}

	@Override
	public PageResult<TeamResultDto> findTeamResultByParam(TeamResultDto dto,
			PageQuery query) {
		int total = resultDao.countTeamResultByParam(dto);
		if (total > 0){
			query.setTotal(total);
			List<TeamResultDto> results = resultDao.findTeamResultByParam(dto, query);
			return new PageResult<TeamResultDto>(query, results);
		}
		return null;
	}

	private void checkObjIsNull(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,球队成绩对象为空!");
		}
	}
	
	private void checkIdIsNull(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,球队成绩对象主键ID为空!");
		}
	}
	
	private void checkObjNotExist(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,球队成绩对象不存在!");
		}
	}
}
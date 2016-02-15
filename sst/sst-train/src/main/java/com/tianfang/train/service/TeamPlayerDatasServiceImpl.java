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
import com.tianfang.train.dao.TeamPlayerDatasDao;
import com.tianfang.train.dto.TeamPlayerDatasDto;
import com.tianfang.train.pojo.TeamPlayerDatas;

@Service
public class TeamPlayerDatasServiceImpl implements ITeamPlayerDatasService {

	@Autowired
	private TeamPlayerDatasDao datasDao;
	
	@Override
	public String save(TeamPlayerDatasDto dto) {
		checkObjIsNull(dto);
		TeamPlayerDatas obj = BeanUtils.createBeanByTarget(dto, TeamPlayerDatas.class);
		String id = UUIDGenerator.getUUID();
		obj.setId(id);
		datasDao.insertSelective(obj);
		return id;
	}

	@Override
	public void del(String id) {
		checkIdIsNull(id);
		TeamPlayerDatas obj = datasDao.selectByPrimaryKey(id);
		checkObjNotExist(obj);
		if (null != obj.getStat() && obj.getStat() == DataStatus.DISABLED){
			return;
		}
		obj.setStat(DataStatus.DISABLED);
		datasDao.updateByPrimaryKeySelective(obj);
	}

	@Override
	public void update(TeamPlayerDatasDto dto) {
		checkObjIsNull(dto);
		checkIdIsNull(dto.getId());
		TeamPlayerDatas obj = BeanUtils.createBeanByTarget(dto, TeamPlayerDatas.class);
		datasDao.updateByPrimaryKeySelective(obj);
	}

	@Override
	public TeamPlayerDatasDto getTeamPlayerDatasById(String id) {
		checkIdIsNull(id);
		TeamPlayerDatas obj = datasDao.selectByPrimaryKey(id);
		if (null != obj && obj.getStat() == DataStatus.ENABLED){
			TeamPlayerDatasDto dto = BeanUtils.createBeanByTarget(obj, TeamPlayerDatasDto.class);
			return dto;
		}
		return null;
	}

	@Override
	public List<TeamPlayerDatasDto> findTeamPlayerDatasByParam(
			TeamPlayerDatasDto dto) {
		List<TeamPlayerDatasDto> list = datasDao.findTeamPlayerDatasByParam(dto);
		if (null != list && list.size() > 0){
			return BeanUtils.createBeanListByTarget(list, TeamPlayerDatasDto.class);
		}
		return null;
	}

	@Override
	public PageResult<TeamPlayerDatasDto> findTeamPlayerDatasByParam(
			TeamPlayerDatasDto dto, PageQuery query) {
		int total = datasDao.countTeamPlayerDatasByParam(dto);
		if (total > 0){
			query.setTotal(total);
			List<TeamPlayerDatasDto> results = datasDao.findTeamPlayerDatasByParam(dto, query);
			return new PageResult<TeamPlayerDatasDto>(query, results);
		}
		return null;
	}
	
	@Override
	public PageResult<TeamPlayerDatasDto> findTeamPlayerDatasByCompId(
			String compId, PageQuery query, String order) {
		TeamPlayerDatasDto dto = new TeamPlayerDatasDto();
		dto.setCompId(compId);
		int total = datasDao.countTeamPlayerDatasByParam(dto);
		if (total > 0){
			query.setTotal(total);
			List<TeamPlayerDatasDto> results = datasDao.findTeamPlayerDatasByParam(dto, query, order);
			return new PageResult<TeamPlayerDatasDto>(query, results);
		}
		return null;
	}

	private void checkObjIsNull(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,赛事球员数据对象为空!");
		}
	}
	
	private void checkIdIsNull(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,赛事球员数据对象主键ID为空!");
		}
	}
	
	private void checkObjNotExist(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,赛事球员数据对象不存在!");
		}
	}
}
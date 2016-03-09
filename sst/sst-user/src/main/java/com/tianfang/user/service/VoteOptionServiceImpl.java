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
import com.tianfang.user.dao.VoteOptionDao;
import com.tianfang.user.dto.VoteOptionDto;
import com.tianfang.user.pojo.VoteOption;

@Service
public class VoteOptionServiceImpl implements IVoteOptionService {

	@Autowired
	private VoteOptionDao voteOptionDao;

	@Override
	public String save(VoteOptionDto dto){
		checkObjIsNull(dto);
		VoteOption m = BeanUtils.createBeanByTarget(dto, VoteOption.class);
		String id = UUIDGenerator.getUUID();
		m.setId(id);
		voteOptionDao.insertSelective(m);
		return id;
	}

	@Override
	public void del(String id){
		checkIdIsNull(id);
		VoteOption m = voteOptionDao.selectByPrimaryKey(id);
		checkObjIsNotExist(m);
		m.setStat(DataStatus.DISABLED);
		voteOptionDao.updateByPrimaryKeySelective(m);
	}

	@Override
	public void update(VoteOptionDto dto){
		checkObjIsNull(dto);
		checkIdIsNull(dto.getId());
		checkObjIsNotExist(voteOptionDao.selectByPrimaryKey(dto.getId()));
		VoteOption m = BeanUtils.createBeanByTarget(dto, VoteOption.class);
		voteOptionDao.updateByPrimaryKeySelective(m);
	}

	@Override
	public VoteOptionDto getVoteOptionById(String id){
		checkIdIsNull(id);
		VoteOption m = voteOptionDao.selectByPrimaryKey(id);
		VoteOptionDto dto = BeanUtils.createBeanByTarget(m, VoteOptionDto.class);
		return dto;
	}

	@Override
	public List<VoteOptionDto> findVoteOptionByParam(VoteOptionDto dto){
		return voteOptionDao.findVoteOptionByParam(dto);
	}

	@Override
	public PageResult<VoteOptionDto> findVoteOptionByParam(VoteOptionDto dto, PageQuery query){
		int total = voteOptionDao.countVoteOptionByParam(dto);
		if (total > 0){
			query.setTotal(total);
			List<VoteOptionDto> results = voteOptionDao.findVoteOptionByParam(dto, query);
			return new PageResult<VoteOptionDto>(query, results);
		}
		
		return null;
	}

	private void checkObjIsNull(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,投票选项对象为空!");
		}
	}
	
	private void checkIdIsNull(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,投票选项对象主键ID为空!");
		}
	}
	
	private void checkObjIsNotExist(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,投票选项对象不存在!");
		}
	}
}

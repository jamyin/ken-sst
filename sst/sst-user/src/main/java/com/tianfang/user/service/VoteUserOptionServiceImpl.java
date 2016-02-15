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
import com.tianfang.user.dao.VoteUserOptionDao;
import com.tianfang.user.dto.VoteUserOptionDto;
import com.tianfang.user.pojo.VoteUserOption;

@Service
public class VoteUserOptionServiceImpl implements IVoteUserOptionService {
	
	@Autowired
	private VoteUserOptionDao voteUserOptionDao;

	@Override
	public String save(VoteUserOptionDto dto) throws Exception {
		checkObjIsNull(dto);
		VoteUserOption m = BeanUtils.createBeanByTarget(dto, VoteUserOption.class);
		String id = UUIDGenerator.getUUID();
		m.setId(id);
		voteUserOptionDao.insertSelective(m);
		return id;
	}

	@Override
	public void del(String id) throws Exception {
		checkIdIsNull(id);
		VoteUserOption m = voteUserOptionDao.selectByPrimaryKey(id);
		checkObjIsNotExist(m);
		m.setStat(DataStatus.DISABLED);
		voteUserOptionDao.updateByPrimaryKeySelective(m);
	}

	@Override
	public void update(VoteUserOptionDto dto) throws Exception {
		checkObjIsNull(dto);
		checkIdIsNull(dto.getId());
		checkObjIsNotExist(voteUserOptionDao.selectByPrimaryKey(dto.getId()));
		VoteUserOption m = BeanUtils.createBeanByTarget(dto, VoteUserOption.class);
		voteUserOptionDao.updateByPrimaryKeySelective(m);
	}

	@Override
	public VoteUserOptionDto getVoteUserOptionById(String id) throws Exception {
		checkIdIsNull(id);
		VoteUserOption m = voteUserOptionDao.selectByPrimaryKey(id);
		VoteUserOptionDto dto = BeanUtils.createBeanByTarget(m, VoteUserOptionDto.class);
		return dto;
	}

	@Override
	public List<VoteUserOptionDto> findVoteUserOptionByParam(VoteUserOptionDto dto) throws Exception {
		return voteUserOptionDao.findVoteUserOptionByParam(dto);
	}

	@Override
	public PageResult<VoteUserOptionDto> findVoteUserOptionByParam(VoteUserOptionDto dto, PageQuery query)
			throws Exception {
		int total = voteUserOptionDao.countVoteUserOptionByParam(dto);
		if (total > 0){
			query.setTotal(total);
			List<VoteUserOptionDto> results = voteUserOptionDao.findVoteUserOptionByParam(dto, query);
			return new PageResult<VoteUserOptionDto>(query, results);
		}
		
		return null;
	}

	private void checkObjIsNull(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,投票用户结果对象为空!");
		}
	}
	
	private void checkIdIsNull(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,投票用户结果对象主键ID为空!");
		}
	}
	
	private void checkObjIsNotExist(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,投票用户结果对象不存在!");
		}
	}
}
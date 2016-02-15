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
import com.tianfang.user.dao.MemoDao;
import com.tianfang.user.dto.MemoDto;
import com.tianfang.user.pojo.Memo;

@Service
public class MemoServiceImpl implements IMemoService {
	
	@Autowired
	private MemoDao memoDao;

	@Override
	public String save(MemoDto dto) throws Exception {
		checkObjIsNull(dto);
		Memo m = BeanUtils.createBeanByTarget(dto, Memo.class);
		String id = UUIDGenerator.getUUID();
		m.setId(id);
		int flag = memoDao.insertSelective(m);
		if(flag > 0){
			return id;
		}
		return "";
	}

	@Override
	public void del(String id) throws Exception {
		checkIdIsNull(id);
		Memo m = memoDao.selectByPrimaryKey(id);
		checkObjIsNotExist(m);
		m.setStat(DataStatus.DISABLED);
		memoDao.updateByPrimaryKeySelective(m);
	}

	@Override
	public void update(MemoDto dto) throws Exception {
		checkObjIsNull(dto);
		checkIdIsNull(dto.getId());
		checkObjIsNotExist(memoDao.selectByPrimaryKey(dto.getId()));
		Memo m = BeanUtils.createBeanByTarget(dto, Memo.class);
		memoDao.updateByPrimaryKeySelective(m);
	}

	@Override
	public MemoDto getMemoById(String id) throws Exception {
		checkIdIsNull(id);
		Memo m = memoDao.selectByPrimaryKey(id);
		MemoDto dto = BeanUtils.createBeanByTarget(m, MemoDto.class);
		return dto;
	}

	@Override
	public List<MemoDto> findMemoByParam(MemoDto dto) throws Exception {
		return memoDao.findMemoByParam(dto);
	}
	
	@Override
	public List<MemoDto> findMemoByUserId(String userId) throws Exception {
		checkUserIdIsNull(userId);
		MemoDto dto = new MemoDto();
		dto.setUserId(userId);
		return memoDao.findMemoByParam(dto);
	}

	@Override
	public PageResult<MemoDto> findMemoByParam(MemoDto dto, PageQuery query)
			throws Exception {
		int total = memoDao.countMemoByParam(dto);
		if (total > 0){
			query.setTotal(total);
			List<MemoDto> results = memoDao.findMemoByParam(dto, query);
			return new PageResult<MemoDto>(query, results);
		}
		
		return null;
	}

	private void checkObjIsNull(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,用户备忘对象为空!");
		}
	}
	
	private void checkIdIsNull(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,用户备忘对象主键ID为空!");
		}
	}
	
	private void checkObjIsNotExist(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,用户备忘对象不存在!");
		}
	}
	
	private void checkUserIdIsNull(String userId) {
		if (StringUtils.isBlank(userId)){
			throw new RuntimeException("对不起,用户备忘对象用户ID为空!");
		}
	}
}
package com.tianfang.evaluat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.evaluat.dao.EvaluatDao;
import com.tianfang.evaluat.dto.EvaluatDto;
import com.tianfang.evaluat.pojo.Evaluat;

@Service
public class EvaluatServiceImpl implements IEvaluatService {
	
	@Autowired
	private EvaluatDao evaluatDao;

	@Override
	public PageResult<EvaluatDto> findEvaluatBySql(EvaluatDto dto, PageQuery page) {
		int total = evaluatDao.countEvaluatByParam(dto);
		page.setTotal(total);
		List<EvaluatDto> results = evaluatDao.findEvaluatBySql(dto, page);
		return new PageResult<EvaluatDto>(page, results);
	}

	@Override
	public int save(EvaluatDto dto) {
		// TODO Auto-generated method stub
		dto.setOrderBy(evaluatDao.countAllEvaluat()+1);			
		Evaluat evaluat = BeanUtils.createBeanByTarget(dto, Evaluat.class);
		return evaluatDao.insertSelective(evaluat);
	}

	@Override
	public EvaluatDto findEvaluatById(String id) {
		// TODO Auto-generated method stub
		Evaluat evaluat = evaluatDao.selectByPrimaryKey(id);
		return BeanUtils.createBeanByTarget(evaluat, EvaluatDto.class);
	}

	@Override
	public int modify(EvaluatDto dto) {
		Evaluat evaluat = BeanUtils.createBeanByTarget(dto, Evaluat.class);
		return evaluatDao.updateByPrimaryKeySelective(evaluat);
	}

	@Override
	public void del(String id,Integer svalue) {
		Evaluat evaluat = evaluatDao.selectByPrimaryKey(id);
		evaluat.setStat(svalue);
		evaluatDao.updateByPrimaryKeySelective(evaluat);
	}

}

package com.tianfang.evaluat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.reflect.DynamicMethod;
import com.tianfang.evaluat.dao.EvaluatResultDao;
import com.tianfang.evaluat.dto.EvaluatResultDto;
import com.tianfang.evaluat.pojo.EvaluatResult;

@Service
public class EvaluatResultServiceImpl implements IEvaluatResultService {
	
	@Autowired
	private EvaluatResultDao evaluatResultDao;

	@Override
	public PageResult<EvaluatResultDto> findEvaluatResultBySql(EvaluatResultDto dto, PageQuery page) {
		int total = evaluatResultDao.countEvaluatResultByParam(dto);
		page.setTotal(total);
		List<EvaluatResultDto> results = evaluatResultDao.findEvaluatResultBySql(dto, page);
		return new PageResult<EvaluatResultDto>(page, results);
	}

	@Override
	public int save(EvaluatResultDto dto) {
		// TODO Auto-generated method stub			
		EvaluatResult EvaluatResult = BeanUtils.createBeanByTarget(dto, EvaluatResult.class);
		return evaluatResultDao.insertSelective(EvaluatResult);
	}

	@Override
	public EvaluatResultDto findEvaluatResultById(String id) {
		// TODO Auto-generated method stub
		EvaluatResult EvaluatResult = evaluatResultDao.selectByPrimaryKey(id);
		return BeanUtils.createBeanByTarget(EvaluatResult, EvaluatResultDto.class);
	}

	@Override
	public int modify(EvaluatResultDto dto) {
		EvaluatResult EvaluatResult = BeanUtils.createBeanByTarget(dto, EvaluatResult.class);
		return evaluatResultDao.updateByPrimaryKeySelective(EvaluatResult);
	}

	@Override
	public void del(String id,Integer svalue) {
		EvaluatResult EvaluatResult = evaluatResultDao.selectByPrimaryKey(id);
		EvaluatResult.setStat(svalue);
		evaluatResultDao.updateByPrimaryKeySelective(EvaluatResult);
	}

	@Override
	public List<EvaluatResultDto> findEvaluatResultBySql(EvaluatResultDto dto) {
		// TODO Auto-generated method stub
		List<EvaluatResultDto> results = evaluatResultDao.findEvaluatResultBySql(dto);
		return results;
	}

	@Override
	public void modifyColumn(String id, String column_k, String column_v,String column_t) {
		// TODO Auto-generated method stub
		EvaluatResult EvaluatResult = evaluatResultDao.selectByPrimaryKey(id);
		if(Objects.equal(column_t, "integer")){
			DynamicMethod.invokeMethod(EvaluatResult, column_k, new Object[]{Integer.valueOf(String.valueOf(column_v))});	
		}else{
			DynamicMethod.invokeMethod(EvaluatResult, column_k, new Object[]{String.valueOf(column_v)});
		}
		evaluatResultDao.updateByPrimaryKeySelective(EvaluatResult);
	}

}

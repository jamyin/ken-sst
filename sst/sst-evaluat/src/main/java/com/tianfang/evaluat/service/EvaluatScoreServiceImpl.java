package com.tianfang.evaluat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.reflect.DynamicMethod;
import com.tianfang.evaluat.dao.EvaluatScoreDao;
import com.tianfang.evaluat.dto.EvaluatDto;
import com.tianfang.evaluat.dto.EvaluatScoreDto;
import com.tianfang.evaluat.pojo.Evaluat;
import com.tianfang.evaluat.pojo.EvaluatAnswer;
import com.tianfang.evaluat.pojo.EvaluatScore;

@Service
public class EvaluatScoreServiceImpl implements IEvaluatScoreService {
	
	@Autowired
	private EvaluatScoreDao evaluatScoreDao;

	@Override
	public PageResult<EvaluatScoreDto> findEvaluatScoreBySql(EvaluatScoreDto dto, PageQuery page) {
		int total = evaluatScoreDao.countEvaluatByParam(dto);
		page.setTotal(total);
		List<EvaluatScoreDto> results = evaluatScoreDao.findEvaluatBySql(dto, page);
		return new PageResult<EvaluatScoreDto>(page, results);
	}

	@Override
	public int save(EvaluatScoreDto dto) {
		// TODO Auto-generated method stub		
		EvaluatScore evaluat = BeanUtils.createBeanByTarget(dto, EvaluatScore.class);
		return evaluatScoreDao.insertSelective(evaluat);
	}

	@Override
	public EvaluatScoreDto findEvaluatScoreById(String id) {
		// TODO Auto-generated method stub
		EvaluatScore evaluat = evaluatScoreDao.selectByPrimaryKey(id);
		return BeanUtils.createBeanByTarget(evaluat, EvaluatScoreDto.class);
	}

	@Override
	public int modify(EvaluatScoreDto dto) {
		EvaluatScore evaluat = BeanUtils.createBeanByTarget(dto, Evaluat.class);
		return evaluatScoreDao.updateByPrimaryKeySelective(evaluat);
	}

	@Override
	public void del(String id,Integer svalue) {
		EvaluatScore evaluat = evaluatScoreDao.selectByPrimaryKey(id);
		evaluat.setStat(svalue);
		evaluatScoreDao.updateByPrimaryKeySelective(evaluat);
	}

	@Override
	public List<EvaluatScoreDto> findEvaluatScoreBySql(EvaluatScoreDto dto) {
		List<EvaluatScoreDto> results = evaluatScoreDao.findEvaluatBySql(dto);
		return results;
	}
	
	@Override
	public void modifyColumn(String id, String column_k, String column_v,String column_t) {
		// TODO Auto-generated method stub
		EvaluatScore evaluatScore = evaluatScoreDao.selectByPrimaryKey(id);
		if(Objects.equal(column_t, "integer")){
			DynamicMethod.invokeMethod(evaluatScore, column_k, new Object[]{Integer.valueOf(String.valueOf(column_v))});	
		}else{
			DynamicMethod.invokeMethod(evaluatScore, column_k, new Object[]{String.valueOf(column_v)});
		}
		evaluatScoreDao.updateByPrimaryKeySelective(evaluatScore);
	}

}

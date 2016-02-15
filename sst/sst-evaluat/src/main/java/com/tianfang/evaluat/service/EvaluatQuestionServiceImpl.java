package com.tianfang.evaluat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.reflect.DynamicMethod;
import com.tianfang.evaluat.dao.EvaluatQuestionDao;
import com.tianfang.evaluat.dto.EvaluatQuestionDto;
import com.tianfang.evaluat.pojo.EvaluatQuestion;

@Service
public class EvaluatQuestionServiceImpl implements IEvaluatQuestionService {
	
	@Autowired
	private EvaluatQuestionDao evaluatQuestionDao;

	@Override
	public PageResult<EvaluatQuestionDto> findEvaluatQuesBySql(EvaluatQuestionDto dto, PageQuery page) {
		int total = evaluatQuestionDao.countEvaluatQuesByParam(dto);
		page.setTotal(total);
		List<EvaluatQuestionDto> results = evaluatQuestionDao.findEvaluatQuesBySql(dto, page);
		return new PageResult<EvaluatQuestionDto>(page, results);
	}

	@Override
	public int save(EvaluatQuestionDto dto) {
		// TODO Auto-generated method stub
		dto.setOrderBy(evaluatQuestionDao.countAllEvaluatQues(dto.getEvaId())+1);			
		EvaluatQuestion EvaluatQuestion = BeanUtils.createBeanByTarget(dto, EvaluatQuestion.class);
		return evaluatQuestionDao.insertSelective(EvaluatQuestion);
	}

	@Override
	public EvaluatQuestionDto findEvaluatQuesById(String id) {
		// TODO Auto-generated method stub
		EvaluatQuestion EvaluatQuestion = evaluatQuestionDao.selectByPrimaryKey(id);
		return BeanUtils.createBeanByTarget(EvaluatQuestion, EvaluatQuestionDto.class);
	}

	@Override
	public int modify(EvaluatQuestionDto dto) {
		EvaluatQuestion EvaluatQuestion = BeanUtils.createBeanByTarget(dto, EvaluatQuestion.class);
		return evaluatQuestionDao.updateByPrimaryKeySelective(EvaluatQuestion);
	}

	@Override
	public void del(String id,Integer svalue) {
		EvaluatQuestion evaluatQuestion = evaluatQuestionDao.selectByPrimaryKey(id);
		evaluatQuestion.setStat(svalue);
		evaluatQuestionDao.updateByPrimaryKeySelective(evaluatQuestion);
	}

	@Override
	public List<EvaluatQuestionDto> findEvaluatQuesBySql(EvaluatQuestionDto dto) {
		// TODO Auto-generated method stub
		List<EvaluatQuestionDto> results = evaluatQuestionDao.findEvaluatQuesBySql(dto);
		return results;
	}

	@Override
	public void modifyColumn(String id, String column_k, String column_v,String column_t) {
		// TODO Auto-generated method stub
		EvaluatQuestion evaluatQuestion = evaluatQuestionDao.selectByPrimaryKey(id);
		if(Objects.equal(column_t, "integer")){
			DynamicMethod.invokeMethod(evaluatQuestion, column_k, new Object[]{Integer.valueOf(String.valueOf(column_v))});	
		}else{
			DynamicMethod.invokeMethod(evaluatQuestion, column_k, new Object[]{String.valueOf(column_v)});
		}
		evaluatQuestionDao.updateByPrimaryKeySelective(evaluatQuestion);
	}

}

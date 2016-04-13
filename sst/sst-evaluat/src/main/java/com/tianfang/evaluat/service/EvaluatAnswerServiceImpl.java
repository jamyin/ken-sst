package com.tianfang.evaluat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.reflect.DynamicMethod;
import com.tianfang.evaluat.dao.EvaluatAnswerDao;
import com.tianfang.evaluat.dao.EvaluatDao;
import com.tianfang.evaluat.dao.EvaluatQuestionDao;
import com.tianfang.evaluat.dto.EvaluatAnswerDto;
import com.tianfang.evaluat.pojo.Evaluat;
import com.tianfang.evaluat.pojo.EvaluatAnswer;
import com.tianfang.evaluat.pojo.EvaluatQuestion;

@Service
public class EvaluatAnswerServiceImpl implements IEvaluatAnswerService {
	
	@Autowired
	private EvaluatAnswerDao evaluatAnswerDao;

	@Autowired
	private EvaluatQuestionDao evaluatQuestionDao;

	@Autowired
	private EvaluatDao evaluatDao; 
	
	@Override
	public PageResult<EvaluatAnswerDto> findEvaluatAnswerBySql(EvaluatAnswerDto dto, PageQuery page) {
		int total = evaluatAnswerDao.countEvaluatAnswerByParam(dto);
		page.setTotal(total);
		List<EvaluatAnswerDto> results = evaluatAnswerDao.findEvaluatAnswerBySql(dto, page);
		return new PageResult<EvaluatAnswerDto>(page, results);
	}

	@Override
	public int save(EvaluatAnswerDto dto) {
		// TODO Auto-generated method stub
		dto.setOrderBy(evaluatAnswerDao.countAllEvaluatAnswer(dto.getEvaId(),dto.getEvaQuestionId())+1);			
		EvaluatAnswer evaluatAnswer = BeanUtils.createBeanByTarget(dto, EvaluatAnswer.class);
		
		int result = evaluatAnswerDao.insertSelective(evaluatAnswer);
		
		//统计分数
		setTotalScore(evaluatAnswer);
				
		return result;
	}

	@Override
	public EvaluatAnswerDto findEvaluatAnswerById(String id) {
		// TODO Auto-generated method stub
		EvaluatAnswer EvaluatAnswer = evaluatAnswerDao.selectByPrimaryKey(id);
		return BeanUtils.createBeanByTarget(EvaluatAnswer, EvaluatAnswerDto.class);
	}

	@Override
	public int modify(EvaluatAnswerDto dto) {
		EvaluatAnswer EvaluatAnswer = BeanUtils.createBeanByTarget(dto, EvaluatAnswer.class);
		return evaluatAnswerDao.updateByPrimaryKeySelective(EvaluatAnswer);
	}

	@Override
	public void del(String id,Integer svalue) {
		EvaluatAnswer EvaluatAnswer = evaluatAnswerDao.selectByPrimaryKey(id);
		EvaluatAnswer.setStat(svalue);
		evaluatAnswerDao.updateByPrimaryKeySelective(EvaluatAnswer);
	}

	@Override
	public List<EvaluatAnswerDto> findEvaluatAnswerBySql(EvaluatAnswerDto dto) {
		// TODO Auto-generated method stub
		List<EvaluatAnswerDto> results = evaluatAnswerDao.findEvaluatAnswerBySql(dto);
		return results;
	}

	@Override
	public void modifyColumn(String id, String column_k, String column_v,String column_t) {
		// TODO Auto-generated method stub
		EvaluatAnswer evaluatAnswer = evaluatAnswerDao.selectByPrimaryKey(id);
		if(Objects.equal(column_t, "integer")){
			DynamicMethod.invokeMethod(evaluatAnswer, column_k, new Object[]{Integer.valueOf(String.valueOf(column_v))});	
		}else{
			DynamicMethod.invokeMethod(evaluatAnswer, column_k, new Object[]{String.valueOf(column_v)});
		}
		evaluatAnswerDao.updateByPrimaryKeySelective(evaluatAnswer);
		
		
		setTotalScore(evaluatAnswer);
		
	}

	//答案分数修改后 需要计算 问题的分数 以及标题的分数
	//问题分数= 各个答案分数之和
	//标题分数= 各个问题分数之和
	@Async
	public void setTotalScore(EvaluatAnswer evaluatAnswer){
		//计算问题之和
		int sumQuestionScore = evaluatAnswerDao.sumAnswerScore(evaluatAnswer.getEvaQuestionId());
		
		EvaluatQuestion eq = new EvaluatQuestion();
		eq.setId(evaluatAnswer.getEvaQuestionId());
		eq.setSumScore(sumQuestionScore);
		evaluatQuestionDao.updateByPrimaryKeySelective(eq);
		
		String evaId = evaluatAnswer.getEvaId();
		int sumEvaScore = evaluatQuestionDao.countScoreTotal(evaId);
		Evaluat eva = new Evaluat();
		eva.setId(evaId);
		eva.setSumScore(sumEvaScore);
		evaluatDao.updateByPrimaryKeySelective(eva);
	}
	
	
	
}

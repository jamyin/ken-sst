package com.tianfang.evaluat.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.evaluat.dto.EvaluatAnswerDto;
import com.tianfang.evaluat.mapper.EvaluatAnswerExMapper;
import com.tianfang.evaluat.mapper.EvaluatAnswerMapper;
import com.tianfang.evaluat.pojo.EvaluatAnswer;
import com.tianfang.evaluat.pojo.EvaluatAnswerExample;

@Repository
public class EvaluatAnswerDao extends MyBatisBaseDao<EvaluatAnswer>{
	
	@Autowired
	@Getter
	private EvaluatAnswerMapper mapper;

	@Autowired
	@Getter
	private EvaluatAnswerExMapper mapperEx;
	
	public int countAllEvaluatAnswer(String evaId,String evaQuestionId){
		EvaluatAnswerExample example = new EvaluatAnswerExample();
		EvaluatAnswerExample.Criteria criteria = example.createCriteria();
		
		criteria.andEvaIdEqualTo(evaId);
		
		criteria.andEvaQuestionIdEqualTo(evaQuestionId);
		
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.countByExample(example);
	}
	
	public int countEvaluatAnswerByParam(EvaluatAnswerDto dto) {
		EvaluatAnswerExample example = new EvaluatAnswerExample();
		EvaluatAnswerExample.Criteria criteria = example.createCriteria();
		queryBySql(dto, criteria);
		return mapper.countByExample(example);
	}

	public List<EvaluatAnswerDto> findEvaluatAnswerBySql(EvaluatAnswerDto dto, PageQuery page) {
		EvaluatAnswerExample example = new EvaluatAnswerExample();
		EvaluatAnswerExample.Criteria criteria = example.createCriteria();
		queryBySql(dto, criteria);
        if(null != page){
        	example.setOrderByClause("stat desc,order_by asc,create_time desc limit "+page.getStartNum() +"," + page.getPageSize());
		}
        List<EvaluatAnswer> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, EvaluatAnswerDto.class);
	}
	
	private void queryBySql(EvaluatAnswerDto params, EvaluatAnswerExample.Criteria criteria) {
    	if (StringUtils.isNotBlank(params.getId())){
    		criteria.andIdEqualTo(params.getId().trim());
    	}
    	if(!StringUtils.isEmpty(params.getTitle())){
    		criteria.andTitleLike("%"+params.getTitle()+"%");
    	}
    	if(params.getStat()!=null){
    		criteria.andStatEqualTo(params.getStat());	
    	}
    	if(!StringUtils.isEmpty(params.getEvaId())){
    		criteria.andEvaIdEqualTo(params.getEvaId());
    	}
    	if(!StringUtils.isEmpty(params.getEvaQuestionId())){
    		criteria.andEvaQuestionIdEqualTo(params.getEvaQuestionId());
    	}
	}

	public List<EvaluatAnswerDto> findEvaluatAnswerBySql(EvaluatAnswerDto dto) {
		EvaluatAnswerExample example = new EvaluatAnswerExample();
		EvaluatAnswerExample.Criteria criteria = example.createCriteria();

		queryBySql(dto, criteria);

		example.setOrderByClause("stat desc,order_by asc,create_time desc");
        List<EvaluatAnswer> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, EvaluatAnswerDto.class);
	}

	public int sumAnswerScore(String evaQuestionId) {
		return mapperEx.countScoreTotal(evaQuestionId);
	}
	
	
}




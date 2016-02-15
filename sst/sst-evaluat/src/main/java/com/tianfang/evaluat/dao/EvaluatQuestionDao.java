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
import com.tianfang.evaluat.dto.EvaluatQuestionDto;
import com.tianfang.evaluat.mapper.EvaluatQuestionExMapper;
import com.tianfang.evaluat.mapper.EvaluatQuestionMapper;
import com.tianfang.evaluat.pojo.EvaluatQuestion;
import com.tianfang.evaluat.pojo.EvaluatQuestionExample;

@Repository
public class EvaluatQuestionDao extends MyBatisBaseDao<EvaluatQuestion>{
	
	@Autowired
	@Getter
	private EvaluatQuestionMapper mapper;

	@Autowired
	@Getter
	private EvaluatQuestionExMapper mapperEx;
	
	public int countAllEvaluatQues(String evaId){
		EvaluatQuestionExample example = new EvaluatQuestionExample();
		EvaluatQuestionExample.Criteria criteria = example.createCriteria();
		
		criteria.andEvaIdEqualTo(evaId);
		
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.countByExample(example);
	}
	
	public int countEvaluatQuesByParam(EvaluatQuestionDto dto) {
		EvaluatQuestionExample example = new EvaluatQuestionExample();
		EvaluatQuestionExample.Criteria criteria = example.createCriteria();
		queryBySql(dto, criteria);
		return mapper.countByExample(example);
	}

	public List<EvaluatQuestionDto> findEvaluatQuesBySql(EvaluatQuestionDto dto, PageQuery page) {
		EvaluatQuestionExample example = new EvaluatQuestionExample();
		EvaluatQuestionExample.Criteria criteria = example.createCriteria();
		queryBySql(dto, criteria);
        if(null != page){
        	example.setOrderByClause("stat desc,order_by asc,create_time desc limit "+page.getStartNum() +"," + page.getPageSize());
		}
        List<EvaluatQuestion> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, EvaluatQuestionDto.class);
	}
	
	private void queryBySql(EvaluatQuestionDto params, EvaluatQuestionExample.Criteria criteria) {
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
	}

	public List<EvaluatQuestionDto> findEvaluatQuesBySql(EvaluatQuestionDto dto) {
		EvaluatQuestionExample example = new EvaluatQuestionExample();
		EvaluatQuestionExample.Criteria criteria = example.createCriteria();

		queryBySql(dto, criteria);

		example.setOrderByClause("stat desc,order_by asc,create_time desc");
        List<EvaluatQuestion> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, EvaluatQuestionDto.class);
	}

	public int countScoreTotal(String evaQuestionId) {
		return mapperEx.countScoreTotal(evaQuestionId);
	}
	
	
}
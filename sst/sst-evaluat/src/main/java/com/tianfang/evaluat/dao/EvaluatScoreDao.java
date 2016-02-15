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
import com.tianfang.evaluat.dto.EvaluatDto;
import com.tianfang.evaluat.dto.EvaluatScoreDto;
import com.tianfang.evaluat.mapper.EvaluatScoreMapper;
import com.tianfang.evaluat.pojo.EvaluatScore;
import com.tianfang.evaluat.pojo.EvaluatScoreExample;

@Repository
public class EvaluatScoreDao extends MyBatisBaseDao<EvaluatScore>{
	
	@Autowired
	@Getter
	private EvaluatScoreMapper mapper;

	
	public int countAllEvaluat(){
		EvaluatScoreExample example = new EvaluatScoreExample();
		EvaluatScoreExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.countByExample(example);
	}
	
	public int countEvaluatByParam(EvaluatScoreDto dto) {
		EvaluatScoreExample example = new EvaluatScoreExample();
		EvaluatScoreExample.Criteria criteria = example.createCriteria();
		queryBySql(dto, criteria);
		return mapper.countByExample(example);
	}

	public List<EvaluatScoreDto> findEvaluatBySql(EvaluatScoreDto dto, PageQuery page) {
		EvaluatScoreExample example = new EvaluatScoreExample();
		EvaluatScoreExample.Criteria criteria = example.createCriteria();
		queryBySql(dto, criteria);
        if(null != page){
        	example.setOrderByClause("stat desc,create_time desc limit "+page.getStartNum() +"," + page.getPageSize());
		}
        List<EvaluatScore> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, EvaluatScoreDto.class);
	}
	
	private void queryBySql(EvaluatScoreDto params, EvaluatScoreExample.Criteria criteria) {
    	if (StringUtils.isNotBlank(params.getId())){
    		criteria.andIdEqualTo(params.getId().trim());
    	}
    	if(params.getStat()!=null){
    		criteria.andStatEqualTo(DataStatus.ENABLED);	
    	}
    	if(params.getEvaId()!=null){
    		criteria.andEvaIdEqualTo(params.getEvaId());
    	}
    	
    	if(params.getStartScore()!=null){
    		criteria.andStartScoreLessThanOrEqualTo(params.getStartScore());
    	}
    	
    	if(params.getEndScore()!=null){
    		criteria.andEndScoreGreaterThanOrEqualTo(params.getStartScore());
    	}

	}

	public List<EvaluatScoreDto> findEvaluatBySql(EvaluatScoreDto dto) {
		EvaluatScoreExample example = new EvaluatScoreExample();
		EvaluatScoreExample.Criteria criteria = example.createCriteria();
		queryBySql(dto, criteria);
        example.setOrderByClause("stat desc,create_time desc");
        List<EvaluatScore> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, EvaluatScoreDto.class);
	}
	
	
}

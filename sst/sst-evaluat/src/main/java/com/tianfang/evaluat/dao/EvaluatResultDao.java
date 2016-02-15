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
import com.tianfang.evaluat.dto.EvaluatResultDto;
import com.tianfang.evaluat.mapper.EvaluatResultMapper;
import com.tianfang.evaluat.pojo.EvaluatResult;
import com.tianfang.evaluat.pojo.EvaluatResultExample;

@Repository
public class EvaluatResultDao extends MyBatisBaseDao<EvaluatResult>{
	
	@Autowired
	@Getter
	private EvaluatResultMapper mapper;
	
	public int countAllEvaluatResult(String evaId){
		EvaluatResultExample example = new EvaluatResultExample();
		EvaluatResultExample.Criteria criteria = example.createCriteria();
		
		criteria.andEvaIdEqualTo(evaId);
		
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.countByExample(example);
	}
	
	public int countEvaluatResultByParam(EvaluatResultDto dto) {
		EvaluatResultExample example = new EvaluatResultExample();
		EvaluatResultExample.Criteria criteria = example.createCriteria();
		queryBySql(dto, criteria);
		return mapper.countByExample(example);
	}

	public List<EvaluatResultDto> findEvaluatResultBySql(EvaluatResultDto dto, PageQuery page) {
		EvaluatResultExample example = new EvaluatResultExample();
		EvaluatResultExample.Criteria criteria = example.createCriteria();
		queryBySql(dto, criteria);
        if(null != page){
        	example.setOrderByClause("stat desc,create_time desc limit "+page.getStartNum() +"," + page.getPageSize());
		}
        List<EvaluatResult> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, EvaluatResultDto.class);
	}
	
	private void queryBySql(EvaluatResultDto params, EvaluatResultExample.Criteria criteria) {
    	if (StringUtils.isNotBlank(params.getId())){
    		criteria.andIdEqualTo(params.getId().trim());
    	}
    	if(params.getStat()!=null){
    		criteria.andStatEqualTo(params.getStat());	
    	}
    	if(!StringUtils.isEmpty(params.getEvaId())){
    		criteria.andEvaIdEqualTo(params.getEvaId());
    	}
    	if(!StringUtils.isEmpty(params.getUserId())){
    		criteria.andUserIdEqualTo(params.getUserId());
    	}
	}

	public List<EvaluatResultDto> findEvaluatResultBySql(EvaluatResultDto dto) {
		EvaluatResultExample example = new EvaluatResultExample();
		EvaluatResultExample.Criteria criteria = example.createCriteria();

		queryBySql(dto, criteria);

		example.setOrderByClause("stat desc,create_time desc");
        List<EvaluatResult> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, EvaluatResultDto.class);
	}	
}

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
import com.tianfang.evaluat.mapper.EvaluatMapper;
import com.tianfang.evaluat.pojo.Evaluat;
import com.tianfang.evaluat.pojo.EvaluatExample;

@Repository
public class EvaluatDao extends MyBatisBaseDao<Evaluat>{
	
	@Autowired
	@Getter
	private EvaluatMapper mapper;

	
	public int countAllEvaluat(){
		EvaluatExample example = new EvaluatExample();
		EvaluatExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.countByExample(example);
	}
	
	public int countEvaluatByParam(EvaluatDto dto) {
		EvaluatExample example = new EvaluatExample();
		EvaluatExample.Criteria criteria = example.createCriteria();
		queryBySql(dto, criteria);
		return mapper.countByExample(example);
	}

	public List<EvaluatDto> findEvaluatBySql(EvaluatDto dto, PageQuery page) {
		EvaluatExample example = new EvaluatExample();
		EvaluatExample.Criteria criteria = example.createCriteria();
		queryBySql(dto, criteria);
        if(null != page){
        	example.setOrderByClause("stat desc,order_by asc,create_time desc limit "+page.getStartNum() +"," + page.getPageSize());
		}
        List<Evaluat> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, EvaluatDto.class);
	}
	
	private void queryBySql(EvaluatDto params, EvaluatExample.Criteria criteria) {
    	if (StringUtils.isNotBlank(params.getId())){
    		criteria.andIdEqualTo(params.getId().trim());
    	}
    	
    	if(!StringUtils.isEmpty(params.getTitle())){
    		criteria.andTitleLike("%"+params.getTitle()+"%");
    	}
    	if(params.getStat()!=null){
    		criteria.andStatEqualTo(DataStatus.ENABLED);	
    	}
	}
	
	
}

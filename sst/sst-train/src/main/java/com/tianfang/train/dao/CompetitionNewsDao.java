package com.tianfang.train.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.CompetitionNewsDto;
import com.tianfang.train.mapper.CompetitionNewsMapper;
import com.tianfang.train.pojo.CompetitionNews;
import com.tianfang.train.pojo.CompetitionNewsExample;

@Repository
public class CompetitionNewsDao extends MyBatisBaseDao<CompetitionNews> {

	@Getter
	@Autowired
	private CompetitionNewsMapper mapper;

	public List<CompetitionNews> findCompetitionNewsByParam(CompetitionNewsDto dto){
		return findCompetitionNewsByParam(dto, null);
	}
	
	public List<CompetitionNews> findCompetitionNewsByParam(CompetitionNewsDto dto, PageQuery query) {
		CompetitionNewsExample example = new CompetitionNewsExample();
		CompetitionNewsExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("create_time desc limit "+query.getStartNum() +"," + query.getPageSize());
		}else{
			example.setOrderByClause("create_time desc");
		}
        List<CompetitionNews> results = mapper.selectByExample(example);        
		return results;
	}
	
	public int countCompetitionNewsByParam(CompetitionNewsDto dto){
		CompetitionNewsExample example = new CompetitionNewsExample();
		CompetitionNewsExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
		return mapper.countByExample(example);
	}
	
	/**
	 * 组装查询参数
	 * @param params
	 * @param criteria
	 * @author xiang_wang
	 * 2016年1月12日下午4:51:12
	 */
	private void assemblyParams(CompetitionNewsDto params, CompetitionNewsExample.Criteria criteria) {
		if (null != params) {
        	if (StringUtils.isNotBlank(params.getId())){
        		criteria.andIdEqualTo(params.getId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getCompId())){
        		criteria.andCompIdEqualTo(params.getCompId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getCompName())){
        		criteria.andCompNameLike("%"+params.getCompName()+"%");
        	}
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}
}
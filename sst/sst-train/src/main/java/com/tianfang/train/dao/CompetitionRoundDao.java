package com.tianfang.train.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.CompetitionRoundDto;
import com.tianfang.train.mapper.CompetitionRoundExMapper;
import com.tianfang.train.mapper.CompetitionRoundMapper;
import com.tianfang.train.pojo.CompetitionRound;
import com.tianfang.train.pojo.CompetitionRoundExample;

@Repository
public class CompetitionRoundDao extends MyBatisBaseDao<CompetitionRound> {
	
	@Autowired
	@Getter
	private CompetitionRoundMapper mapper;
	@Autowired
	private CompetitionRoundExMapper exMapper;
	
	public List<CompetitionRoundDto> findCompetitionRoundByParam(CompetitionRoundDto dto){
		return findCompetitionRoundByParam(dto, null);
	}
	
	public List<CompetitionRoundDto> findCompetitionRoundByParam(CompetitionRoundDto dto, PageQuery query) {
		CompetitionRoundExample example = new CompetitionRoundExample();
		CompetitionRoundExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("page_ranking,create_time limit "+query.getStartNum() +"," + query.getPageSize());
		}else{
			example.setOrderByClause("page_ranking,create_time");
		}
        List<CompetitionRound> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, CompetitionRoundDto.class);
	}
	
	public int countCompetitionRoundByParam(CompetitionRoundDto dto){
		CompetitionRoundExample example = new CompetitionRoundExample();
		CompetitionRoundExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
		return mapper.countByExample(example);
	}
	
	/**
	 * 当前赛事下页面排序的最大值
	 * @param compId
	 * @return
	 * @author xiang_wang
	 * 2016年2月2日下午1:55:49
	 */
	public int maxPageRanking(String compId){
		return exMapper.maxPageRanking(compId);
	}
	
	/**
	 * 组装查询参数
	 * @param params
	 * @param criteria
	 * @author xiang_wang
	 * 2016年1月12日下午4:51:12
	 */
	private void assemblyParams(CompetitionRoundDto params, CompetitionRoundExample.Criteria criteria) {
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
        	if (StringUtils.isNotBlank(params.getName())){
        		criteria.andNameLike("%"+params.getName()+"%");
        	}
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}
}
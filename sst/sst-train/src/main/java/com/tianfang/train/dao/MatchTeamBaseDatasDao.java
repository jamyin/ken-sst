package com.tianfang.train.dao;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.MatchTeamBaseDatasDto;
import com.tianfang.train.mapper.MatchTeamBaseDatasExMapper;
import com.tianfang.train.mapper.MatchTeamBaseDatasMapper;
import com.tianfang.train.pojo.MatchTeamBaseDatas;
import com.tianfang.train.pojo.MatchTeamBaseDatasExample;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MatchTeamBaseDatasDao extends MyBatisBaseDao<MatchTeamBaseDatas>{

	@Autowired
	@Getter
	private MatchTeamBaseDatasMapper mapper;
	@Autowired
	private MatchTeamBaseDatasExMapper exMapper;
	
	public List<MatchTeamBaseDatasDto> findMatchTeamBaseDatasByParam(MatchTeamBaseDatasDto dto){
		return findMatchTeamBaseDatasByParam(dto, null);
	}
	
	public List<MatchTeamBaseDatasDto> findMatchTeamBaseDatasByParam(MatchTeamBaseDatasDto dto, PageQuery query) {
		MatchTeamBaseDatasExample example = new MatchTeamBaseDatasExample();
		MatchTeamBaseDatasExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("create_time desc limit "+query.getStartNum() +"," + query.getPageSize());
		}else{
			example.setOrderByClause("create_time desc");
		}
        List<MatchTeamBaseDatas> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, MatchTeamBaseDatasDto.class);
	}
	
	public int countMatchTeamBaseDatasByParam(MatchTeamBaseDatasDto dto){
		MatchTeamBaseDatasExample example = new MatchTeamBaseDatasExample();
		MatchTeamBaseDatasExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
		return mapper.countByExample(example);
	}

	public void batchInsertTeamDatas(List<MatchTeamBaseDatasDto> dtos) {
		List<MatchTeamBaseDatas> datas = BeanUtils.createBeanListByTarget(dtos, MatchTeamBaseDatas.class);
		exMapper.batchInsertTeamDatas(datas);
	}

	public void deleteByMatchId(String matchId){
		exMapper.deleteByMatchId(matchId);
	}
	
	/**
	 * 组装查询参数
	 * @param params
	 * @param criteria
	 * @author xiang_wang
	 * 2016年1月12日下午4:51:12
	 */
	private void assemblyParams(MatchTeamBaseDatasDto params, MatchTeamBaseDatasExample.Criteria criteria) {
		if (null != params) {
        	if (StringUtils.isNotBlank(params.getId())){
        		criteria.andIdEqualTo(params.getId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getTeamId())){
        		criteria.andTeamIdEqualTo(params.getTeamId().trim());
        	}
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}


}

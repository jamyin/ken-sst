package com.tianfang.train.dao;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.MatchPlayerHotDatasDto;
import com.tianfang.train.mapper.MatchPlayerHotDatasExMapper;
import com.tianfang.train.mapper.MatchPlayerHotDatasMapper;
import com.tianfang.train.pojo.MatchPlayerHotDatas;
import com.tianfang.train.pojo.MatchPlayerHotDatasExample;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MatchPlayerHotDatasDao extends MyBatisBaseDao<MatchPlayerHotDatas>{

	@Autowired
	@Getter
	private MatchPlayerHotDatasMapper mapper;
	@Autowired
	private MatchPlayerHotDatasExMapper exMapper;
	
	public List<MatchPlayerHotDatasDto> findMatchPlayerHotDatasByParam(MatchPlayerHotDatasDto dto){
		return findMatchPlayerHotDatasByParam(dto, null);
	}
	
	public List<MatchPlayerHotDatasDto> findMatchPlayerHotDatasByParam(MatchPlayerHotDatasDto dto, PageQuery query) {
		MatchPlayerHotDatasExample example = new MatchPlayerHotDatasExample();
		MatchPlayerHotDatasExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("create_time desc limit "+query.getStartNum() +"," + query.getPageSize());
		}else{
			example.setOrderByClause("create_time desc");
		}
        List<MatchPlayerHotDatas> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, MatchPlayerHotDatasDto.class);
	}
	
	public int countMatchPlayerHotDatasByParam(MatchPlayerHotDatasDto dto){
		MatchPlayerHotDatasExample example = new MatchPlayerHotDatasExample();
		MatchPlayerHotDatasExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
		return mapper.countByExample(example);
	}

	public void batchInsertPlayerHotDatas(List<MatchPlayerHotDatasDto> dtos) {
		List<MatchPlayerHotDatas> datas = BeanUtils.createBeanListByTarget(dtos, MatchPlayerHotDatas.class);
		exMapper.batchInsertPlayerHotDatas(datas);
	}

	public void deleteByMatchIdAndTeamId(String matchId, String teamId) {
		exMapper.deleteByMatchIdAndTeamId(matchId, teamId);
	}
	
	/**
	 * 组装查询参数
	 * @param params
	 * @param criteria
	 * @author xiang_wang
	 * 2016年1月12日下午4:51:12
	 */
	private void assemblyParams(MatchPlayerHotDatasDto params, MatchPlayerHotDatasExample.Criteria criteria) {
		if (null != params) {
        	if (StringUtils.isNotBlank(params.getId())){
        		criteria.andIdEqualTo(params.getId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getTeamId())){
        		criteria.andTeamIdEqualTo(params.getTeamId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getMatchId())){
				criteria.andMatchIdEqualTo(params.getMatchId().trim());
			}
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}
}
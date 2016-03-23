package com.tianfang.train.dao;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.MatchPlayerBaseDatasDto;
import com.tianfang.train.mapper.MatchPlayerBaseDatasExMapper;
import com.tianfang.train.mapper.MatchPlayerBaseDatasMapper;
import com.tianfang.train.pojo.MatchPlayerBaseDatas;
import com.tianfang.train.pojo.MatchPlayerBaseDatasExample;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MatchPlayerBaseDatasDao extends MyBatisBaseDao<MatchPlayerBaseDatas>{

	@Autowired
	@Getter
	private MatchPlayerBaseDatasMapper mapper;
	@Autowired
	private MatchPlayerBaseDatasExMapper exMapper;
	
	public List<MatchPlayerBaseDatasDto> findMatchPlayerBaseDatasByParam(MatchPlayerBaseDatasDto dto){
		return findMatchPlayerBaseDatasByParam(dto, null);
	}
	
	public List<MatchPlayerBaseDatasDto> findMatchPlayerBaseDatasByParam(MatchPlayerBaseDatasDto dto, PageQuery query) {
		MatchPlayerBaseDatasExample example = new MatchPlayerBaseDatasExample();
		MatchPlayerBaseDatasExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("reserve asc, create_time desc limit "+query.getStartNum() +"," + query.getPageSize());
		}else{
			example.setOrderByClause("reserve asc, create_time desc");
		}
        List<MatchPlayerBaseDatas> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, MatchPlayerBaseDatasDto.class);
	}
	
	public int countMatchPlayerBaseDatasByParam(MatchPlayerBaseDatasDto dto){
		MatchPlayerBaseDatasExample example = new MatchPlayerBaseDatasExample();
		MatchPlayerBaseDatasExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
		return mapper.countByExample(example);
	}

	public void batchInsertPlayerBaseDatas(List<MatchPlayerBaseDatasDto> dtos) {
		List<MatchPlayerBaseDatas> datas = BeanUtils.createBeanListByTarget(dtos, MatchPlayerBaseDatas.class);
		exMapper.batchInsertPlayerBaseDatas(datas);
	}

	public void deleteByMatchIdAndTeamId(String matchId, String teamId) {
		Map<String, String> map = new HashMap<String, String>(2);
		map.put("matchId",matchId);
		map.put("teamId",teamId);
		exMapper.deleteByMatchIdAndTeamId(map);
	}

	/**
	 * 组装查询参数
	 * @param params
	 * @param criteria
	 * @author xiang_wang
	 * 2016年1月12日下午4:51:12
	 */
	private void assemblyParams(MatchPlayerBaseDatasDto params, MatchPlayerBaseDatasExample.Criteria criteria) {
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
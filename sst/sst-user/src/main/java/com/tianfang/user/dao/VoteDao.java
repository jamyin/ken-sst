package com.tianfang.user.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.user.dto.VoteDto;
import com.tianfang.user.dto.VoteExDto;
import com.tianfang.user.mapper.VoteExMapper;
import com.tianfang.user.mapper.VoteMapper;
import com.tianfang.user.pojo.Vote;
import com.tianfang.user.pojo.VoteExample;

@Repository
public class VoteDao extends MyBatisBaseDao<Vote>{

	@Autowired
	@Getter
	private VoteMapper mapper;
	
	@Autowired
	private VoteExMapper voteExMapper;
	
	public List<VoteDto> findVoteByParam(VoteDto dto){
		return findVoteByParam(dto, null);
	}
	
	public List<VoteDto> findVoteByParam(VoteDto dto, PageQuery query) {
		VoteExample example = new VoteExample();
		VoteExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("create_time desc limit "+query.getStartNum() +"," + query.getPageSize());
		}
        List<Vote> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, VoteDto.class);
	}
	
	public int countVoteByParam(VoteDto dto){
		VoteExample example = new VoteExample();
		VoteExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
		return mapper.countByExample(example);
	}
	
	public List<VoteExDto> findVoteExById(String id){
		return voteExMapper.findVoteExById(id);
	}
	
	/**
	 * 组装查询参数
	 * @param params
	 * @param criteria
	 * @author xiang_wang
	 * 2016年1月12日下午4:51:12
	 */
	private void assemblyParams(VoteDto params, VoteExample.Criteria criteria) {
		if (null != params) {
        	if (StringUtils.isNotBlank(params.getId())){
        		criteria.andIdEqualTo(params.getId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getTitle())){
        		criteria.andTitleLike("%"+params.getTitle().trim()+"%");
        	}
        	if (StringUtils.isNotBlank(params.getGroupId())){
        		criteria.andGroupIdEqualTo(params.getGroupId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getUserId())){
        		criteria.andUserIdEqualTo(params.getUserId().trim());
        	}
        	if (null != params.getIsAnonymous()){
        		criteria.andIsAnonymousEqualTo(params.getIsAnonymous().intValue());
        	}
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}
}

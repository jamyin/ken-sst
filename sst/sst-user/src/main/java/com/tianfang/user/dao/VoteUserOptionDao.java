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
import com.tianfang.user.dto.VoteUserOptionDto;
import com.tianfang.user.mapper.VoteUserOptionMapper;
import com.tianfang.user.pojo.VoteUserOption;
import com.tianfang.user.pojo.VoteUserOptionExample;

@Repository
public class VoteUserOptionDao extends MyBatisBaseDao<VoteUserOption>{
	
	@Getter
	@Autowired
	private VoteUserOptionMapper mapper;
	
	public List<VoteUserOptionDto> findVoteUserOptionByParam(VoteUserOptionDto dto){
		return findVoteUserOptionByParam(dto, null);
	}
	
	public List<VoteUserOptionDto> findVoteUserOptionByParam(VoteUserOptionDto dto, PageQuery query) {
		VoteUserOptionExample example = new VoteUserOptionExample();
		VoteUserOptionExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("create_time desc limit "+query.getStartNum() +"," + query.getPageSize());
		}
        List<VoteUserOption> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, VoteUserOptionDto.class);
	}
	
	public int countVoteUserOptionByParam(VoteUserOptionDto dto){
		VoteUserOptionExample example = new VoteUserOptionExample();
		VoteUserOptionExample.Criteria criteria = example.createCriteria();
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
	private void assemblyParams(VoteUserOptionDto params, VoteUserOptionExample.Criteria criteria) {
		if (null != params) {
        	if (StringUtils.isNotBlank(params.getId())){
        		criteria.andIdEqualTo(params.getId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getVoteId())){
        		criteria.andVoteIdEqualTo(params.getVoteId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getUserId())){
        		criteria.andUserIdEqualTo(params.getUserId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getOptionId())){
        		criteria.andOptionIdEqualTo(params.getOptionId().trim());
        	}
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}
}
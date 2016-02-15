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
import com.tianfang.user.dto.VoteOptionDto;
import com.tianfang.user.mapper.VoteOptionMapper;
import com.tianfang.user.pojo.VoteOption;
import com.tianfang.user.pojo.VoteOptionExample;

@Repository
public class VoteOptionDao extends MyBatisBaseDao<VoteOption> {

	@Getter
	@Autowired
	private VoteOptionMapper mapper;
	
	public List<VoteOptionDto> findVoteOptionByParam(VoteOptionDto dto){
		return findVoteOptionByParam(dto, null);
	}
	
	public List<VoteOptionDto> findVoteOptionByParam(VoteOptionDto dto, PageQuery query) {
		VoteOptionExample example = new VoteOptionExample();
		VoteOptionExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("create_time desc limit "+query.getStartNum() +"," + query.getPageSize());
		}
        List<VoteOption> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, VoteOptionDto.class);
	}
	
	public int countVoteOptionByParam(VoteOptionDto dto){
		VoteOptionExample example = new VoteOptionExample();
		VoteOptionExample.Criteria criteria = example.createCriteria();
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
	private void assemblyParams(VoteOptionDto params, VoteOptionExample.Criteria criteria) {
		if (null != params) {
        	if (StringUtils.isNotBlank(params.getId())){
        		criteria.andIdEqualTo(params.getId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getVoteId())){
        		criteria.andVoteIdEqualTo(params.getVoteId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getText())){
        		criteria.andTextLike("%"+params.getText().trim()+"%");
        	}
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}
}
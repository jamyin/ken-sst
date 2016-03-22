package com.tianfang.user.dao;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;
import com.tianfang.user.dto.*;
import com.tianfang.user.mapper.VoteExMapper;
import com.tianfang.user.mapper.VoteMapper;
import com.tianfang.user.mapper.VoteUserTempMapper;
import com.tianfang.user.pojo.Vote;
import com.tianfang.user.pojo.VoteExample;
import com.tianfang.user.pojo.VoteUserTemp;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VoteDao extends MyBatisBaseDao<Vote>{

	@Autowired
	@Getter
	private VoteMapper mapper;
	@Autowired
	private VoteExMapper voteExMapper;
	@Autowired
	private VoteUserTempMapper voteUserTempMapper;
	
	public List<Vote> findVoteByParam(VoteDto dto){
		return findVoteByParam(dto, null);
	}
	
	public List<Vote> findVoteByParam(VoteDto dto, PageQuery query) {
		VoteExample example = new VoteExample();
		VoteExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("create_time desc limit "+query.getStartNum() +"," + query.getPageSize());
		}
        List<Vote> results = mapper.selectByExample(example);        
		return results;
	}
	
	public void updateVoteUserTemp(VoteUserTemp temp){
		voteUserTempMapper.updateByPrimaryKeySelective(temp);
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
	
	public List<VoteDto> findVoteTempByParam(VoteParams params){
		return findVoteTempByParam(params, null);
	}
	
	public List<VoteDto> findVoteTempByParam(VoteParams params, PageQuery query) {
		List<VoteDto> results = voteExMapper.findVoteTempByParams(params, query);
		return results;
	}
	
	public int countVoteTempByParam(VoteParams params){
		return voteExMapper.countVoteTempByParams(params);
	}
	
	/**
	 * 批量保存投票选项
	 * @param options
	 * @author xiang_wang
	 * 2016年3月9日下午3:05:14
	 */
	public void insertBatchVoteOption(List<VoteOptionDto> options){
		voteExMapper.insertBatchVoteOption(options);
	}
	
	/**
	 * 批量保存投票用户关联表数据
	 * @param temps
	 * @author xiang_wang
	 * 2016年3月9日下午3:05:16
	 */
	public void insertBatchVoteUserTemp(List<VoteUserTempDto> temps){
		voteExMapper.insertBatchVoteUserTemp(temps);
	}

	public VoteDto getLast(String userId){
		return voteExMapper.getLast(userId);
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
        	if (StringUtils.isNotBlank(params.getPublishId())){
        		criteria.andPublishIdEqualTo(params.getPublishId().trim());
        	}
        	if (null != params.getIsAnonymous()){
        		criteria.andIsAnonymousEqualTo(params.getIsAnonymous().intValue());
        	}
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}
}

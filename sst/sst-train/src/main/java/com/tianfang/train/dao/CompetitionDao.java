package com.tianfang.train.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.mapper.CompetitionMapper;
import com.tianfang.train.pojo.Competition;
import com.tianfang.train.pojo.CompetitionExample;
import com.tianfang.train.pojo.CompetitionExample.Criteria;

@Repository
public class CompetitionDao extends MyBatisBaseDao<Competition> {

	@Getter
	@Autowired
	private CompetitionMapper mapper;

	/**
	 * 不带分页查询
	 * @author YIn
	 * @time:2016年1月14日 下午1:24:21
	 */
	public List<Competition> selectByParameter(Competition competition) {
		CompetitionExample example = new CompetitionExample();
		CompetitionExample.Criteria criteria = example.createCriteria();
        assemblyParams(competition, criteria);   //组装参数
        List<Competition> result = mapper.selectByExample(example);  
        return result;
	}

	/**
	 * 组装参数
	 * @author YIn
	 * @time:2016年1月14日 下午1:27:43
	 */
	private void assemblyParams(Competition competition, Criteria criteria) {
		if (null != competition){
			if (StringUtils.isNotBlank(competition.getId())){
	    		criteria.andIdEqualTo(competition.getId());
	    	}
	    	if (StringUtils.isNotBlank(competition.getTitle())){
	    		criteria.andTitleLike("%" +competition.getTitle()+"%");
	    	}
	    	if (null != competition.getIsClosed()){
	    		criteria.andIsClosedEqualTo(competition.getIsClosed());
	    	}
	    	if (null != competition.getCompType()){
	    		criteria.andCompTypeEqualTo(competition.getCompType());
	    	}
		}
		
    	criteria.andStatEqualTo(DataStatus.ENABLED);
	}

	/**
	 * 分页查询赛事
	 * @author YIn
	 * @time:2016年1月14日 下午2:43:29
	 */
	public List<Competition> findcompetitionViewByPage(Competition competition, PageQuery page) {
		CompetitionExample example = new CompetitionExample();
		CompetitionExample.Criteria criteria = example.createCriteria();
		example.setOrderByClause(" create_time DESC limit " + page.getStartNum() +"," + page.getPageSize());
        assemblyParams(competition, criteria);   //组装参数
        List<Competition> result = mapper.selectByExample(example);  
        return result;
	}

	/**
	 * 查询总条数
	 * @author YIn
	 * @time:2016年1月14日 下午2:43:59
	 */
	public int selectAccount(Competition competition) {
		CompetitionExample example = new CompetitionExample();
		CompetitionExample.Criteria criteria = example.createCriteria();
        assemblyParams(competition, criteria);   //组装参数
        return mapper.countByExample(example);
	}

	
	
}
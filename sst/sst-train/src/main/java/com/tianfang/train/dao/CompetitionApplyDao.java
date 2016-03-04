package com.tianfang.train.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.CompetitionApplyDto;
import com.tianfang.train.mapper.CompetitionApplyExMapper;
import com.tianfang.train.mapper.CompetitionApplyMapper;
import com.tianfang.train.pojo.CompetitionApply;
import com.tianfang.train.pojo.CompetitionApplyExample;
import com.tianfang.train.pojo.CompetitionApplyExample.Criteria;

@Repository
public class CompetitionApplyDao extends MyBatisBaseDao<CompetitionApply> {

	@Getter
	@Autowired
	private CompetitionApplyMapper mapper;
	
	@Getter
	@Autowired
	private CompetitionApplyExMapper mappers;

	/**
	 * 不带分页查询
	 * @author YIn
	 * @time:2016年1月20日 下午1:24:21
	 */
	public List<CompetitionApply> selectByParameter(CompetitionApply competitionApply) {
		CompetitionApplyExample example = new CompetitionApplyExample();
		CompetitionApplyExample.Criteria criteria = example.createCriteria();
        assemblyParams(competitionApply, criteria);   //组装参数
        List<CompetitionApply> result = mapper.selectByExample(example);  
        return result;
	}

	/**
	 * 组装参数
	 * @author YIn
	 * @time:2016年1月20日 下午1:27:43
	 */
	private void assemblyParams(CompetitionApply competitionApply, Criteria criteria) {
		if (null != competitionApply){
			if (StringUtils.isNotBlank(competitionApply.getId())){
	    		criteria.andIdEqualTo(competitionApply.getId());
	    	}
			if (StringUtils.isNotBlank(competitionApply.getTeamId())){
				criteria.andTeamIdEqualTo(competitionApply.getTeamId());
			}
			if (StringUtils.isNotBlank(competitionApply.getCompId())){
				criteria.andCompIdEqualTo(competitionApply.getCompId());
			}
			if (StringUtils.isNotBlank(competitionApply.getCompName())){
	    		criteria.andCompNameLike("%"+competitionApply.getCompName()+"%");
	    	}
			if (StringUtils.isNotBlank(competitionApply.getTeamName())){
	    		criteria.andTeamNameLike("%"+competitionApply.getTeamName()+"%");
	    	}
			if (null != competitionApply.getAuditType()){
				criteria.andAuditTypeEqualTo(competitionApply.getAuditType());
			}
		}
		
    	criteria.andStatEqualTo(DataStatus.ENABLED);
	}

	/**
	 * 分页查询比赛报名
	 * @author YIn
	 * @time:2016年1月20日 下午2:43:29
	 */
	public List<CompetitionApply> findCompetitionApplyViewByPage(CompetitionApply competitionApply, PageQuery page) {
		CompetitionApplyExample example = new CompetitionApplyExample();
		CompetitionApplyExample.Criteria criteria = example.createCriteria();
		example.setOrderByClause(" create_time DESC limit " + page.getStartNum() +"," + page.getPageSize());
        assemblyParams(competitionApply, criteria);   //组装参数
        List<CompetitionApply> result = mapper.selectByExample(example);  
        return result;
	}

	/**
	 * 查询总条数
	 * @author YIn
	 * @time:2016年1月20日 下午2:43:59
	 */
	public int selectAccount(CompetitionApply competitionApply) {
		CompetitionApplyExample example = new CompetitionApplyExample();
		CompetitionApplyExample.Criteria criteria = example.createCriteria();
        assemblyParams(competitionApply, criteria);   //组装参数
        return mapper.countByExample(example);
	}
	
	/**
	 * @author YIn
	 * @time:2016年1月22日 下午5:42:40
	 */
	public List<CompetitionApplyDto> findCompApplyViewByPage(CompetitionApplyDto competitionApplyDto, PageQuery page) {
			List<CompetitionApplyDto> result = mappers.selectByExample(competitionApplyDto, page);  
	        return result;
	}

	/**
	 * @author YIn
	 * @time:2016年1月22日 下午5:42:56
	 */
	public int selectCompAccount(CompetitionApplyDto competitionApplyDto) {
		    return mappers.countByExample(competitionApplyDto);
	}

	
	
}
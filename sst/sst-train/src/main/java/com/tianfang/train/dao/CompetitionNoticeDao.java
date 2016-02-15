package com.tianfang.train.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.CompetitionNoticeDto;
import com.tianfang.train.mapper.CompetitionNoticeExMapper;
import com.tianfang.train.mapper.CompetitionNoticeMapper;
import com.tianfang.train.pojo.CompetitionNotice;
import com.tianfang.train.pojo.CompetitionNoticeExample;
import com.tianfang.train.pojo.CompetitionNoticeExample.Criteria;

@Repository
public class CompetitionNoticeDao extends MyBatisBaseDao<CompetitionNotice> {

	@Getter
	@Autowired
	private CompetitionNoticeMapper mapper;
	
	@Getter
	@Autowired
	private CompetitionNoticeExMapper mappers;

	/**
	 * 不带分页查询
	 * @author YIn
	 * @time:2016年1月15日 下午1:24:21
	 */
	public List<CompetitionNotice> selectByParameter(CompetitionNotice competitionNotice) {
		CompetitionNoticeExample example = new CompetitionNoticeExample();
		CompetitionNoticeExample.Criteria criteria = example.createCriteria();
        assemblyParams(competitionNotice, criteria);   //组装参数
        List<CompetitionNotice> result = mapper.selectByExample(example);  
        return result;
	}

	/**
	 * 组装参数
	 * @author YIn
	 * @time:2016年1月15日 下午1:27:43
	 */
	private void assemblyParams(CompetitionNotice competitionNotice, Criteria criteria) {
		if (StringUtils.isNotBlank(competitionNotice.getId())){
    		criteria.andIdEqualTo(competitionNotice.getId());
    	}
    	criteria.andStatEqualTo(DataStatus.ENABLED);
	}

	/**
	 * 分页查询赛事公告
	 * @author YIn
	 * @time:2016年1月15日 下午2:43:29
	 */
	public List<CompetitionNotice> findCompetitionNoticeViewByPage(CompetitionNotice competitionNotice, PageQuery page) {
		CompetitionNoticeExample example = new CompetitionNoticeExample();
		CompetitionNoticeExample.Criteria criteria = example.createCriteria();
		example.setOrderByClause(" create_time DESC limit " + page.getStartNum() +"," + page.getPageSize());
        assemblyParams(competitionNotice, criteria);   //组装参数
        List<CompetitionNotice> result = mapper.selectByExample(example);  
        return result;
	}

	/**
	 * 查询总条数
	 * @author YIn
	 * @time:2016年1月15日 下午2:43:59
	 */
	public int selectAccount(CompetitionNotice competitionNotice) {
		CompetitionNoticeExample example = new CompetitionNoticeExample();
		CompetitionNoticeExample.Criteria criteria = example.createCriteria();
        assemblyParams(competitionNotice, criteria);   //组装参数
        return mapper.countByExample(example);
	}
	
	/**
	 * @author YIn
	 * @time:2016年1月22日 下午5:42:40
	 */
	public List<CompetitionNoticeDto> findCompNoticeViewByPage(
			CompetitionNoticeDto competitionNoticeDto, PageQuery page) {
			List<CompetitionNoticeDto> result = mappers.selectByExample(competitionNoticeDto, page);  
	        return result;
	}

	/**
	 * @author YIn
	 * @time:2016年1月22日 下午5:42:56
	 */
	public int selectCompAccount(CompetitionNoticeDto competitionNoticeDto) {
		    return mappers.countByExample(competitionNoticeDto);
	}
}
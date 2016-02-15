package com.tianfang.train.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.TeamImageDto;
import com.tianfang.train.mapper.TeamImageExMapper;
import com.tianfang.train.mapper.TeamImageMapper;
import com.tianfang.train.pojo.TeamImage;
import com.tianfang.train.pojo.TeamImageExample;
import com.tianfang.train.pojo.TeamImageExample.Criteria;

@Repository
public class TeamImageDao extends MyBatisBaseDao<TeamImage> {

	@Getter
	@Autowired
	private TeamImageMapper mapper;
	
	@Getter
	@Autowired
	private TeamImageExMapper mappers;

	/**
	 * 不带分页查询
	 * @author YIn
	 * @time:2016年1月27日 下午1:24:21
	 */
	public List<TeamImage> selectByParameter(TeamImage teamImage) {
		TeamImageExample example = new TeamImageExample();
		TeamImageExample.Criteria criteria = example.createCriteria();
        assemblyParams(teamImage, criteria);   //组装参数
        List<TeamImage> result = mapper.selectByExample(example);  
        return result;
	}

	/**
	 * 组装参数
	 * @author YIn
	 * @time:2016年1月27日 下午1:27:43
	 */
	private void assemblyParams(TeamImage teamImage, Criteria criteria) {
		if (StringUtils.isNotBlank(teamImage.getId())){
    		criteria.andIdEqualTo(teamImage.getId());
    	}
		if (StringUtils.isNotBlank(teamImage.getTitle())){
			criteria.andTitleLike("%" + teamImage.getTitle() + "%");
		}
    	criteria.andStatEqualTo(DataStatus.ENABLED);
	}

	/**
	 * 分页查询球队图片
	 * @author YIn
	 * @time:2016年1月27日 下午2:43:29
	 */
	public List<TeamImage> findTeamImageViewByPage(TeamImage teamImage, PageQuery page) {
		TeamImageExample example = new TeamImageExample();
		TeamImageExample.Criteria criteria = example.createCriteria();
		example.setOrderByClause(" create_time DESC limit " + page.getStartNum() +"," + page.getPageSize());
        assemblyParams(teamImage, criteria);   //组装参数
        List<TeamImage> result = mapper.selectByExample(example);  
        return result;
	}

	/**
	 * 查询总条数
	 * @author YIn
	 * @time:2016年1月27日 下午2:43:59
	 */
	public int selectAccount(TeamImage teamImage) {
		TeamImageExample example = new TeamImageExample();
		TeamImageExample.Criteria criteria = example.createCriteria();
        assemblyParams(teamImage, criteria);   //组装参数
        return mapper.countByExample(example);
	}
	
	/**
	 * @author YIn
	 * @time:2016年1月27日 下午5:42:40
	 */
	public List<TeamImageDto> findTeamImageConn(
			TeamImageDto TeamImageDto, PageQuery page) {
			List<TeamImageDto> result = mappers.selectByExample(TeamImageDto, page);  
	        return result;
	}

	/**
	 * @author YIn
	 * @time:2016年1月27日 下午5:42:56
	 */
	public int selectTeamImageConnAccount(TeamImageDto teamImageDto) {
		    return mappers.countByExample(teamImageDto);
	}

	/**
	 * @author YIn
	 * @time:2016年1月29日 下午1:53:46
	 */
	public Integer addTeamImageBatch(List<TeamImageDto> list) {
		return mappers.addTeamImageBatch(list);
	}
}
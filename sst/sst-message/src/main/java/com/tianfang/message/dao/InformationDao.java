package com.tianfang.message.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.message.dto.InformationDto;
import com.tianfang.message.mapper.InformationMapper;
import com.tianfang.message.pojo.Information;
import com.tianfang.message.pojo.InformationExample;

@Repository
public class InformationDao extends MyBatisBaseDao<Information>{

	@Autowired
	@Getter
	private InformationMapper mapper;
	
	public List<InformationDto> findInformationByParam(InformationDto dto){
		return findInformationByParam(dto, null);
	}
	
	public List<InformationDto> findInformationByParam(InformationDto dto, PageQuery query) {
		InformationExample example = new InformationExample();
		InformationExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("create_time desc limit "+query.getStartNum() +"," + query.getPageSize());
		}
        List<Information> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, InformationDto.class);
	}
	
	public int countInformationByParam(InformationDto dto){
		InformationExample example = new InformationExample();
		InformationExample.Criteria criteria = example.createCriteria();
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
	private void assemblyParams(InformationDto params, InformationExample.Criteria criteria) {
		if (null != params) {
        	if (StringUtils.isNotBlank(params.getId())){
        		criteria.andIdEqualTo(params.getId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getTitle())){
        		criteria.andTitleLike("%"+params.getTitle().trim()+"%");
        	}
        	if (null != params.getParentType()){
        		criteria.andParentTypeEqualTo(params.getParentType().intValue());
        	}
        	if (null != params.getSubType()){
        		criteria.andSubTypeEqualTo(params.getSubType().intValue());
        	}
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}
}

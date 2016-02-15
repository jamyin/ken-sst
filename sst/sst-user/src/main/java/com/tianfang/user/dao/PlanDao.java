package com.tianfang.user.dao;

import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.user.dto.PlanDto;
import com.tianfang.user.mapper.PlanExMapper;
import com.tianfang.user.mapper.PlanMapper;
import com.tianfang.user.pojo.Plan;
import com.tianfang.user.pojo.PlanExample;

@Repository
public class PlanDao extends MyBatisBaseDao<Plan>{

	@Getter
	@Autowired
	private PlanMapper mapper;
	@Autowired
	private PlanExMapper exMapper;
	
	public List<PlanDto> findPlanByParam(PlanDto dto){
		return findPlanByParam(dto, null);
	}
	
	public List<PlanDto> findPlanByParam(PlanDto dto, PageQuery query) {
		PlanExample example = new PlanExample();
		PlanExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("plan_time asc limit "+query.getStartNum() +"," + query.getPageSize());
		}else{
			example.setOrderByClause("plan_time asc");
		}
        List<Plan> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, PlanDto.class);
	}
	
	public int countPlanByParam(PlanDto dto){
		PlanExample example = new PlanExample();
		PlanExample.Criteria criteria = example.createCriteria();
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
	private void assemblyParams(PlanDto params, PlanExample.Criteria criteria) {
		if (null != params) {
        	if (StringUtils.isNotBlank(params.getId())){
        		criteria.andIdEqualTo(params.getId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getUserId())){
        		criteria.andUserIdEqualTo(params.getUserId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getTitle())){
        		criteria.andTitleLike("%"+params.getTitle()+"%");
        	}
        	if (StringUtils.isNotBlank(params.getUserName())){
        		criteria.andUserNameLike("%"+params.getUserName()+"%");
        	}
        	if (null != params.getStartTime()){
        		criteria.andPlanTimeGreaterThanOrEqualTo(params.getStartTime());
        	}
        	if (null != params.getEndTime()){
        		criteria.andPlanTimeLessThan(params.getEndTime());
        	}
        	if (null != params.getIsFinish()){
        		criteria.andIsFinishEqualTo(params.getIsFinish());
        	}
        	if (StringUtils.isNotBlank(params.getYear())){
        		Date star = DateUtils.parse(params.getYear(), "yyyy");
        		Date end = DateUtils.parse((Integer.parseInt(params.getYear()) + 1)+"", "yyyy");
        		criteria.andPlanTimeGreaterThanOrEqualTo(star);
        		criteria.andPlanTimeLessThan(end);
        	}
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}

	public void batchUpdate(PlanDto dto) {
		exMapper.batchUpdate(dto);
	}
}
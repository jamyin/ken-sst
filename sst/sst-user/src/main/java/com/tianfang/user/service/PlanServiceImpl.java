package com.tianfang.user.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.user.dao.PlanDao;
import com.tianfang.user.dto.PlanDto;
import com.tianfang.user.pojo.Plan;

@Service
public class PlanServiceImpl implements IPlanService {
	@Autowired
	private PlanDao planDao;

	@Override
	public String save(PlanDto dto){
		checkObjIsNull(dto);
		Plan m = BeanUtils.createBeanByTarget(dto, Plan.class);
		String id = UUIDGenerator.getUUID();
		m.setId(id);
		planDao.insertSelective(m);
		return id;
	}

	@Override
	public void del(String id){
		checkIdIsNull(id);
		Plan m = planDao.selectByPrimaryKey(id);
		checkObjIsNotExist(m);
		m.setStat(DataStatus.DISABLED);
		planDao.updateByPrimaryKeySelective(m);
	}

	@Override
	public void update(PlanDto dto){
		checkObjIsNull(dto);
		checkIdIsNull(dto.getId());
		checkObjIsNotExist(planDao.selectByPrimaryKey(dto.getId()));
		Plan m = BeanUtils.createBeanByTarget(dto, Plan.class);
		planDao.updateByPrimaryKeySelective(m);
	}

	@Override
	public PlanDto getPlanById(String id){
		checkIdIsNull(id);
		Plan m = planDao.selectByPrimaryKey(id);
		if (null != m && m.getStat() == DataStatus.ENABLED){
			return BeanUtils.createBeanByTarget(m, PlanDto.class);
		}
		return null;
	}

	@Override
	public List<PlanDto> findPlanByParam(PlanDto dto){
		return planDao.findPlanByParam(dto);
	}
	
	@Override
	public List<PlanDto> findPlanByUserId(String userId){
		checkUserIdIsNull(userId);
		PlanDto dto = new PlanDto();
		dto.setUserId(userId);
		return planDao.findPlanByParam(dto);
	}
	
	@Override
	public List<PlanDto> findPlanByUserId(String userId, String date) {
		checkUserIdIsNull(userId);
		PlanDto dto = new PlanDto();
		dto.setUserId(userId);
		Date now = DateUtils.parse(date, "yyyy-MM-dd");
		dto.setStartTime(now);
		dto.setEndTime(new Date(now.getTime() + 86400000L));
		return planDao.findPlanByParam(dto);
	}

	@Override
	public PageResult<PlanDto> findPlanByParam(PlanDto dto, PageQuery query){
		int total = planDao.countPlanByParam(dto);
		if (total > 0){
			query.setTotal(total);
			List<PlanDto> results = planDao.findPlanByParam(dto, query);
			return new PageResult<PlanDto>(query, results);
		}
		
		return null;
	}
	
	@Override
	public void delAll(PlanDto dto) {
		if (null != dto){
			if (StringUtils.isNotBlank(dto.getYear())){
				dto.setStartTime(DateUtils.parse(dto.getYear(), "yyyy"));
				dto.setEndTime(DateUtils.parse((Integer.parseInt(dto.getYear())+1)+"", "yyyy"));
			}
			planDao.batchUpdate(dto);
		}
	}

	private void checkObjIsNull(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,用户训练计划对象为空!");
		}
	}
	
	private void checkIdIsNull(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,用户训练计划对象主键ID为空!");
		}
	}
	
	private void checkObjIsNotExist(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,用户训练计划对象不存在!");
		}
	}
	
	private void checkUserIdIsNull(String userId) {
		if (StringUtils.isBlank(userId)){
			throw new RuntimeException("对不起,用户训练计划对象用户ID为空!");
		}
	}
}
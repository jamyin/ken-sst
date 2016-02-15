package com.tianfang.user.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.user.dto.PlanDto;

/**		
 * <p>Title: IPlanService </p>
 * <p>Description: 类描述:训练计划接口</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月15日下午4:26:50
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public interface IPlanService {
	
	String save(PlanDto dto);
	
	void del(String id);
	
	void update(PlanDto dto);
	
	PlanDto getPlanById(String id);
	
	List<PlanDto> findPlanByParam(PlanDto dto);
	
	PageResult<PlanDto> findPlanByParam(PlanDto dto, PageQuery query);
	
	List<PlanDto> findPlanByUserId(String userId);
	
	List<PlanDto> findPlanByUserId(String userId, String date);

	/**
	 * 根据参数删除用户训练计划
	 * @param dto
	 * @author xiang_wang
	 * 2016年2月3日下午2:15:38
	 */
	void delAll(PlanDto dto);
}
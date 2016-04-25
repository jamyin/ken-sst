package com.tianfang.admin.dao;

import com.tianfang.admin.dto.AdminLogDto;
import com.tianfang.admin.mapper.AdminLogMapper;
import com.tianfang.admin.pojo.AdminLog;
import com.tianfang.admin.pojo.AdminLogExample;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminLogDao extends MyBatisBaseDao<AdminLog> {
	
	@Getter
	@Autowired
	private AdminLogMapper mapper;
	
	public List<AdminLogDto> findAdminLogByParam(AdminLogDto dto){
		return findAdminLogByParam(dto, null);
	}
	
	public List<AdminLogDto> findAdminLogByParam(AdminLogDto dto, PageQuery query) {
		AdminLogExample example = new AdminLogExample();
		AdminLogExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("create_time desc limit "+query.getStartNum() +"," + query.getPageSize());
		}else{
			example.setOrderByClause("create_time desc");
		}
        List<AdminLog> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, AdminLogDto.class);
	}
	
	public int countAdminLogByParam(AdminLogDto dto){
		AdminLogExample example = new AdminLogExample();
		AdminLogExample.Criteria criteria = example.createCriteria();
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
	private void assemblyParams(AdminLogDto params, AdminLogExample.Criteria criteria) {
		if (null != params) {
        	if (StringUtils.isNotBlank(params.getId())){
        		criteria.andIdEqualTo(params.getId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getAdminId())){
        		criteria.andAdminIdEqualTo(params.getAdminId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getAdminAccount())){
        		criteria.andAdminAccountLike("%"+params.getAdminAccount()+"%");
        	}
			if (StringUtils.isNotBlank(params.getServiceName())){
				criteria.andServiceNameEqualTo(params.getServiceName());
			}
			if (StringUtils.isNotBlank(params.getMethodName())){
				criteria.andMethodNameEqualTo(params.getMethodName());
			}
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}
}
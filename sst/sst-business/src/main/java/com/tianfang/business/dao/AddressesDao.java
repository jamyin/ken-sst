package com.tianfang.business.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.business.mapper.AddressesMapper;
import com.tianfang.business.pojo.Addresses;
import com.tianfang.business.pojo.AddressesExample;
import com.tianfang.business.pojo.AddressesExample.Criteria;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;

@Repository
public class AddressesDao extends MyBatisBaseDao<Addresses>{
	
	@Autowired
	@Getter
	private AddressesMapper mapper;
	
	/**
	 * 组装参数
	 * @author YIn
	 * @time:2016年2月29日 下午2:29:58
	 * @param Album
	 * @param criteria
	 */
	private void assemblyParams(Addresses addresses, Criteria criteria) {
		if (addresses.getId() != null){
    		criteria.andIdEqualTo(addresses.getId());
    	}
		if (addresses.getLevel() != null){
    		criteria.andLevelEqualTo(addresses.getLevel());
    	}
		if (StringUtils.isNotEmpty(addresses.getParentId())){
    		criteria.andParentIdEqualTo(addresses.getParentId());
    	}
    	criteria.andStatEqualTo(DataStatus.ENABLED);
	}

	/**
	 * @author YIn
	 * @time:2016年2月29日 下午2:46:47
	 */
	public List<Addresses> selectByParameter(Addresses Addresses) {
		AddressesExample example = new AddressesExample();
		AddressesExample.Criteria criteria = example.createCriteria();
        assemblyParams(Addresses, criteria);   //组装参数
        List<Addresses> result = mapper.selectByExample(example);  
        return result;
	}
}

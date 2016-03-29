package com.tianfang.business.dao;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.business.mapper.AppVersionMapper;
import com.tianfang.business.pojo.AppVersion;
import com.tianfang.common.mybatis.MyBatisBaseDao;

@Repository
public class AppVersionDao extends MyBatisBaseDao<AppVersion>{

	@Autowired
	@Getter
	private AppVersionMapper mapper;

}

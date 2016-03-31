package com.tianfang.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.tianfang.business.dao.AppVersionDao;
import com.tianfang.business.dto.AppVersionDto;
import com.tianfang.business.pojo.AppVersion;
import com.tianfang.business.service.IAppVersionService;
import com.tianfang.common.util.BeanUtils;

@Service
public class AppVersionServiceImpl implements IAppVersionService {

	@Autowired
	private AppVersionDao appVersionDao;

	@Override
	public AppVersionDto getAppVersionBy(AppVersionDto dto) {
		AppVersion appversion  = appVersionDao.selectByPrimaryKey(dto.getId());
		if(Objects.equal(appversion, null)){
			return null;
		}
		return BeanUtils.createBeanByTarget(appversion, AppVersionDto.class);
	}

}

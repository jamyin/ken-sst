package com.tianfang.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.tianfang.business.dao.SuggestionDao;
import com.tianfang.business.dto.SuggestionDto;
import com.tianfang.business.pojo.Suggestion;
import com.tianfang.business.service.ISuggestionService;
import com.tianfang.common.util.BeanUtils;

@Service
public class SuggestionServiceImpl implements ISuggestionService {

	@Autowired
	private SuggestionDao suggestionDao;

	@Override
	public int save(SuggestionDto dto) {
		// TODO Auto-generated method stub
		Suggestion bean = BeanUtils.createBeanByTarget(dto, Suggestion.class);
		return suggestionDao.insertSelective(bean);
	}
	

}

package com.tianfang.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.business.dao.SuggestionDao;
import com.tianfang.business.dto.SuggestionDto;
import com.tianfang.business.service.ISuggestionService;

@Service
public class SuggestionServiceImpl implements ISuggestionService {

	@Autowired
	private SuggestionDao suggestionDao;

	@Override
	public int save(SuggestionDto dto) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}

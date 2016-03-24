package com.tianfang.business.dao;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.business.mapper.SuggestionMapper;
import com.tianfang.business.pojo.Suggestion;
import com.tianfang.common.mybatis.MyBatisBaseDao;

@Repository
public class SuggestionDao extends MyBatisBaseDao<Suggestion>{
	
	@Autowired
	@Getter
	private SuggestionMapper mapper;

}

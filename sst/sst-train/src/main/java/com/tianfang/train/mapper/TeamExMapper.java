package com.tianfang.train.mapper;

import java.util.List;

import com.tianfang.train.pojo.Team;


public interface TeamExMapper {

	List<Team> findAllByCompId(String compId);
}
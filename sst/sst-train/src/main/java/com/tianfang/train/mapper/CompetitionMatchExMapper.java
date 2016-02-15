package com.tianfang.train.mapper;

import java.util.List;

import com.tianfang.train.pojo.CompetitionMatch;

public interface CompetitionMatchExMapper {

	void insertBatch(List<CompetitionMatch> matchs);
}
package com.tianfang.train.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.train.dto.CompetitionTeamDto;
import com.tianfang.train.mapper.CompetitionTeamExMapper;
import com.tianfang.train.mapper.CompetitionTeamMapper;
import com.tianfang.train.pojo.CompetitionTeam;

@Repository
public class CompetitionTeamDao extends MyBatisBaseDao<CompetitionTeam> {
	
	@Autowired
	@Getter
	private CompetitionTeamMapper mapper;
	
	@Autowired
	private CompetitionTeamExMapper exMapper;
	
	public List<CompetitionTeamDto> findCompetitionTeamByParam(CompetitionTeamDto dto){
		return exMapper.findCompetitionTeamByParam(dto);
	}
	
	public int countCompetitionTeamByParam(CompetitionTeamDto dto){
		return exMapper.countCompetitionTeamByParam(dto);
	}

	public List<CompetitionTeamDto> selectCompeTeamList(String userId) {
		// TODO Auto-generated method stub
		return exMapper.selectCompeTeamList(userId);
	}
}
package com.tianfang.train.mapper;

import com.tianfang.train.pojo.MatchTeamBaseDatas;

import java.util.List;

public interface MatchTeamBaseDatasExMapper {

    void batchInsertTeamDatas(List<MatchTeamBaseDatas> dtos);

    void deleteByMatchId(String matchId);
}
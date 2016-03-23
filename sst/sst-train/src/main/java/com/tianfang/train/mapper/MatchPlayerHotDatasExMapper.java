package com.tianfang.train.mapper;

import com.tianfang.train.pojo.MatchPlayerHotDatas;

import java.util.List;

public interface MatchPlayerHotDatasExMapper {

    void batchInsertPlayerHotDatas(List<MatchPlayerHotDatas> dtos);

    void deleteByMatchIdAndTeamId(String matchId, String teamId);
}
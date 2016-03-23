package com.tianfang.train.mapper;

import com.tianfang.train.pojo.MatchPlayerBaseDatas;

import java.util.List;

public interface MatchPlayerBaseDatasExMapper {

    void batchInsertPlayerBaseDatas(List<MatchPlayerBaseDatas> dtos);

    void deleteByMatchIdAndTeamId(String matchId, String teamidId);
}
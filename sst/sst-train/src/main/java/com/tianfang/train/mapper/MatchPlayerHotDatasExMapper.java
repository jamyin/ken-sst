package com.tianfang.train.mapper;

import com.tianfang.train.pojo.MatchPlayerHotDatas;

import java.util.List;
import java.util.Map;

public interface MatchPlayerHotDatasExMapper {

    void batchInsertPlayerHotDatas(List<MatchPlayerHotDatas> dtos);

    void deleteByMatchIdAndTeamId(Map<String, String> map);
}
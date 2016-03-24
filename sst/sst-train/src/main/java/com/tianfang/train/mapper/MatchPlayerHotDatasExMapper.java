package com.tianfang.train.mapper;

import com.tianfang.train.dto.MatchPlayerHotDatasDto;
import com.tianfang.train.dto.MatchPlayerHotDatasTempDto;
import com.tianfang.train.pojo.MatchPlayerHotDatas;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MatchPlayerHotDatasExMapper {

    void batchInsertPlayerHotDatas(List<MatchPlayerHotDatas> dtos);

    void deleteByMatchIdAndTeamId(Map<String, String> map);

    List<MatchPlayerHotDatasTempDto> queryPlayerHotDatasTempByParams(@Param("dto")MatchPlayerHotDatasDto params);
}
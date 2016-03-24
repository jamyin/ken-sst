package com.tianfang.train.mapper;

import com.tianfang.train.dto.MatchPlayerBaseDatasDto;
import com.tianfang.train.dto.MatchPlayerBaseDatasTempDto;
import com.tianfang.train.pojo.MatchPlayerBaseDatas;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MatchPlayerBaseDatasExMapper {

    void batchInsertPlayerBaseDatas(List<MatchPlayerBaseDatas> dtos);

    void deleteByMatchIdAndTeamId(Map<String, String> map);

    List<MatchPlayerBaseDatasTempDto> queryPlayerBaseDatasTempByParams(@Param("dto")MatchPlayerBaseDatasDto params);
}
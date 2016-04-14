package com.tianfang.train.mapper;

import com.tianfang.common.model.PageQuery;
import com.tianfang.train.dto.TeamPlayerDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeamPlayerExMapper {

    List<TeamPlayerDto> findTeamPlayerByParam(@Param("dto")TeamPlayerDto dto, @Param("page")PageQuery query);

    int countTeamPlayerByParam(@Param("dto")TeamPlayerDto dto);
    
    List<TeamPlayerDto> findTeamPlayerByTeamPlayer(@Param("dto")TeamPlayerDto dto, @Param("page")PageQuery query);
    
    int countTeamPlayerByTeamPlayer(@Param("dto")TeamPlayerDto dto);
}
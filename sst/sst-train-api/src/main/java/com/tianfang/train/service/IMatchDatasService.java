package com.tianfang.train.service;

import com.tianfang.train.dto.MatchPlayerBaseDatasDto;
import com.tianfang.train.dto.MatchPlayerHotDatasDto;
import com.tianfang.train.dto.MatchTeamBaseDatasDto;

import java.util.List;

/**
 * 比赛球队,球员,时间轴数据接口
 *
 * @Author wangxiang
 * @Date 2016/3/22 14:36
 */
public interface IMatchDatasService {

    /**
     * 批量新增比赛球队数据
     * @param dtos
     */
    void batchInsertTeamDatas(List<MatchTeamBaseDatasDto> dtos);

    /**
     * 批量更新比赛球队数据
     * @param dtos
     */
    void batchUpdateTeamDatas(List<MatchTeamBaseDatasDto> dtos);

    /**
     * 根据比赛id查询比赛球队数据
     * @param matchId
     * @return
     */
    List<MatchTeamBaseDatasDto> queryTeamDatasByMatchId(String matchId);

    /**
     * 根据参数查询比赛球队数据
     * @param params
     * @return
     */
    List<MatchTeamBaseDatasDto> queryTeamDatasByParams(MatchTeamBaseDatasDto params);

    /**
     * 批量新增比赛球员数据
     * @param dtos
     */
    void batchInsertPlayerBaseDatas(List<MatchPlayerBaseDatasDto> dtos);

    /**
     * 批量更新比赛球员数据
     * @param dtos
     */
    void updatePlayerBaseDatas(List<MatchPlayerBaseDatasDto> dtos);

    /**
     * 根据参数查询比赛球员数据
     * @param params
     * @return
     */
    List<MatchPlayerBaseDatasDto> queryPlayerBaseDatasByParams(MatchPlayerBaseDatasDto params);

    /**
     * 批量新增比赛时间轴数据
     * @param dtos
     */
    void batchInsertPlayerHotDatas(List<MatchPlayerHotDatasDto> dtos);

    /**
     * 批量更新比赛时间轴数据
     * @param dtos
     */
    void updatePlayerHotDatas(List<MatchPlayerHotDatasDto> dtos);

    /**
     * 根据参数查询比赛时间轴数据
     * @param params
     * @return
     */
    List<MatchPlayerHotDatasDto> queryPlayerHotDatasByParams(MatchPlayerHotDatasDto params);
}

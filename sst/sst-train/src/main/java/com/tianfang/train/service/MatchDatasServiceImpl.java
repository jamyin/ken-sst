package com.tianfang.train.service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.train.dao.MatchPlayerBaseDatasDao;
import com.tianfang.train.dao.MatchPlayerHotDatasDao;
import com.tianfang.train.dao.MatchTeamBaseDatasDao;
import com.tianfang.train.dto.MatchPlayerBaseDatasDto;
import com.tianfang.train.dto.MatchPlayerHotDatasDto;
import com.tianfang.train.dto.MatchTeamBaseDatasDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 比赛球队,球员,时间轴数据接口
 *
 * @Author wangxiang
 * @Date 2016/3/22 14:58
 */
@Service
public class MatchDatasServiceImpl implements IMatchDatasService {

    @Autowired
    private MatchTeamBaseDatasDao teamDatasDao;
    @Autowired
    private MatchPlayerBaseDatasDao playerDatasDao;
    @Autowired
    private MatchPlayerHotDatasDao hotDatasDao;

    @Override
    public void batchInsertTeamDatas(List<MatchTeamBaseDatasDto> dtos) {
        checkObjIsNullException(dtos);
        for (MatchTeamBaseDatasDto dto : dtos){
            dto.setId(UUIDGenerator.getUUID());
            dto.setCreateTime(new Date());
            dto.setStat(DataStatus.ENABLED);
        }
        teamDatasDao.batchInsertTeamDatas(dtos);
    }

    @Override
    public void batchUpdateTeamDatas(List<MatchTeamBaseDatasDto> dtos) {
        checkObjIsNullException(dtos);
        String matchId = dtos.get(0).getMatchId();
        checkObjIsNullException(matchId);
        teamDatasDao.deleteByMatchId(matchId);
        batchInsertTeamDatas(dtos);
    }

    @Override
    public List<MatchTeamBaseDatasDto> queryTeamDatasByMatchId(String matchId) {
        checkObjIsNullException(matchId);
        MatchTeamBaseDatasDto dto = new MatchTeamBaseDatasDto();
        dto.setMatchId(matchId);

        return queryTeamDatasByParams(dto);
    }

    @Override
    public List<MatchTeamBaseDatasDto> queryTeamDatasByParams(MatchTeamBaseDatasDto params) {
        return teamDatasDao.findMatchTeamBaseDatasByParam(params);
    }

    @Override
    public void batchInsertPlayerBaseDatas(List<MatchPlayerBaseDatasDto> dtos) {
        checkObjIsNullException(dtos);
        for (MatchPlayerBaseDatasDto dto : dtos){
            dto.setId(UUIDGenerator.getUUID());
            dto.setCreateTime(new Date());
            dto.setStat(DataStatus.ENABLED);
        }
        playerDatasDao.batchInsertPlayerBaseDatas(dtos);
    }

    @Override
    public void updatePlayerBaseDatas(List<MatchPlayerBaseDatasDto> dtos) {
        checkObjIsNullException(dtos);
        String matchId = dtos.get(0).getMatchId();
        checkObjIsNullException(matchId);
        String teamId = dtos.get(0).getTeamId();
        checkObjIsNullException(teamId);
        playerDatasDao.deleteByMatchIdAndTeamId(matchId, teamId);
        batchInsertPlayerBaseDatas(dtos);
    }

    @Override
    public List<MatchPlayerBaseDatasDto> queryPlayerBaseDatasByParams(MatchPlayerBaseDatasDto params) {
        return playerDatasDao.findMatchPlayerBaseDatasByParam(params);
    }

    @Override
    public void batchInsertPlayerHotDatas(List<MatchPlayerHotDatasDto> dtos) {
        checkObjIsNullException(dtos);
        for (MatchPlayerHotDatasDto dto : dtos){
            dto.setId(UUIDGenerator.getUUID());
            dto.setCreateTime(new Date());
            dto.setStat(DataStatus.ENABLED);
        }
        hotDatasDao.batchInsertPlayerHotDatas(dtos);
    }

    @Override
    public void updatePlayerHotDatas(List<MatchPlayerHotDatasDto> dtos) {
        checkObjIsNullException(dtos);
        String matchId = dtos.get(0).getMatchId();
        checkObjIsNullException(matchId);
        String teamId = dtos.get(0).getTeamId();
        checkObjIsNullException(teamId);
        hotDatasDao.deleteByMatchIdAndTeamId(matchId, teamId);
        batchInsertPlayerHotDatas(dtos);
    }

    @Override
    public List<MatchPlayerHotDatasDto> queryPlayerHotDatasByParams(MatchPlayerHotDatasDto params) {
        return hotDatasDao.findMatchPlayerHotDatasByParam(params);
    }

    /**
     * 异常对象校验
     * @param obj
     */
    private void checkObjIsNullException(Object obj) {
        if (null == obj){
            throw new RuntimeException("对不起.对象为空!");
        }
        if (obj instanceof List){
            if (((List) obj).size() == 0){
                throw new RuntimeException("对不起.集合对象为空!");
            }
        }
        if (obj instanceof String){
            if (StringUtils.isBlank((String)obj)){
                throw new RuntimeException("对不起.String对象为空!");
            }
        }
    }
}

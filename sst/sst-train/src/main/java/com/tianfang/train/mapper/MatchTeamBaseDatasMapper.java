package com.tianfang.train.mapper;

import com.tianfang.train.pojo.MatchTeamBaseDatas;
import com.tianfang.train.pojo.MatchTeamBaseDatasExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MatchTeamBaseDatasMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_match_team_base_datas
     *
     * @mbggenerated Tue Mar 22 16:42:32 CST 2016
     */
    int countByExample(MatchTeamBaseDatasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_match_team_base_datas
     *
     * @mbggenerated Tue Mar 22 16:42:32 CST 2016
     */
    int deleteByExample(MatchTeamBaseDatasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_match_team_base_datas
     *
     * @mbggenerated Tue Mar 22 16:42:32 CST 2016
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_match_team_base_datas
     *
     * @mbggenerated Tue Mar 22 16:42:32 CST 2016
     */
    int insert(MatchTeamBaseDatas record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_match_team_base_datas
     *
     * @mbggenerated Tue Mar 22 16:42:32 CST 2016
     */
    int insertSelective(MatchTeamBaseDatas record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_match_team_base_datas
     *
     * @mbggenerated Tue Mar 22 16:42:32 CST 2016
     */
    List<MatchTeamBaseDatas> selectByExample(MatchTeamBaseDatasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_match_team_base_datas
     *
     * @mbggenerated Tue Mar 22 16:42:32 CST 2016
     */
    MatchTeamBaseDatas selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_match_team_base_datas
     *
     * @mbggenerated Tue Mar 22 16:42:32 CST 2016
     */
    int updateByExampleSelective(@Param("record") MatchTeamBaseDatas record, @Param("example") MatchTeamBaseDatasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_match_team_base_datas
     *
     * @mbggenerated Tue Mar 22 16:42:32 CST 2016
     */
    int updateByExample(@Param("record") MatchTeamBaseDatas record, @Param("example") MatchTeamBaseDatasExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_match_team_base_datas
     *
     * @mbggenerated Tue Mar 22 16:42:32 CST 2016
     */
    int updateByPrimaryKeySelective(MatchTeamBaseDatas record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_match_team_base_datas
     *
     * @mbggenerated Tue Mar 22 16:42:32 CST 2016
     */
    int updateByPrimaryKey(MatchTeamBaseDatas record);
}
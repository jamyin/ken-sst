package com.tianfang.train.mapper;

import com.tianfang.train.pojo.TeamPlayer;
import com.tianfang.train.pojo.TeamPlayerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TeamPlayerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team_player
     *
     * @mbggenerated Wed Jan 27 14:58:55 CST 2016
     */
    int countByExample(TeamPlayerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team_player
     *
     * @mbggenerated Wed Jan 27 14:58:55 CST 2016
     */
    int deleteByExample(TeamPlayerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team_player
     *
     * @mbggenerated Wed Jan 27 14:58:55 CST 2016
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team_player
     *
     * @mbggenerated Wed Jan 27 14:58:55 CST 2016
     */
    int insert(TeamPlayer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team_player
     *
     * @mbggenerated Wed Jan 27 14:58:55 CST 2016
     */
    int insertSelective(TeamPlayer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team_player
     *
     * @mbggenerated Wed Jan 27 14:58:55 CST 2016
     */
    List<TeamPlayer> selectByExample(TeamPlayerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team_player
     *
     * @mbggenerated Wed Jan 27 14:58:55 CST 2016
     */
    TeamPlayer selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team_player
     *
     * @mbggenerated Wed Jan 27 14:58:55 CST 2016
     */
    int updateByExampleSelective(@Param("record") TeamPlayer record, @Param("example") TeamPlayerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team_player
     *
     * @mbggenerated Wed Jan 27 14:58:55 CST 2016
     */
    int updateByExample(@Param("record") TeamPlayer record, @Param("example") TeamPlayerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team_player
     *
     * @mbggenerated Wed Jan 27 14:58:55 CST 2016
     */
    int updateByPrimaryKeySelective(TeamPlayer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team_player
     *
     * @mbggenerated Wed Jan 27 14:58:55 CST 2016
     */
    int updateByPrimaryKey(TeamPlayer record);
}
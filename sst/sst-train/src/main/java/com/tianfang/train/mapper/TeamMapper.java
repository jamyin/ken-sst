package com.tianfang.train.mapper;

import com.tianfang.train.pojo.Team;
import com.tianfang.train.pojo.TeamExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TeamMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team
     *
     * @mbggenerated Sat Jan 23 10:22:36 CST 2016
     */
    int countByExample(TeamExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team
     *
     * @mbggenerated Sat Jan 23 10:22:36 CST 2016
     */
    int deleteByExample(TeamExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team
     *
     * @mbggenerated Sat Jan 23 10:22:36 CST 2016
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team
     *
     * @mbggenerated Sat Jan 23 10:22:36 CST 2016
     */
    int insert(Team record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team
     *
     * @mbggenerated Sat Jan 23 10:22:36 CST 2016
     */
    int insertSelective(Team record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team
     *
     * @mbggenerated Sat Jan 23 10:22:36 CST 2016
     */
    List<Team> selectByExample(TeamExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team
     *
     * @mbggenerated Sat Jan 23 10:22:36 CST 2016
     */
    Team selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team
     *
     * @mbggenerated Sat Jan 23 10:22:36 CST 2016
     */
    int updateByExampleSelective(@Param("record") Team record, @Param("example") TeamExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team
     *
     * @mbggenerated Sat Jan 23 10:22:36 CST 2016
     */
    int updateByExample(@Param("record") Team record, @Param("example") TeamExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team
     *
     * @mbggenerated Sat Jan 23 10:22:36 CST 2016
     */
    int updateByPrimaryKeySelective(Team record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team
     *
     * @mbggenerated Sat Jan 23 10:22:36 CST 2016
     */
    int updateByPrimaryKey(Team record);
}
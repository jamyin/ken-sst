package com.tianfang.train.mapper;

import com.tianfang.train.pojo.TeamImage;
import com.tianfang.train.pojo.TeamImageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TeamImageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team_image
     *
     * @mbggenerated Mon Jan 11 16:27:29 CST 2016
     */
    int countByExample(TeamImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team_image
     *
     * @mbggenerated Mon Jan 11 16:27:29 CST 2016
     */
    int deleteByExample(TeamImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team_image
     *
     * @mbggenerated Mon Jan 11 16:27:29 CST 2016
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team_image
     *
     * @mbggenerated Mon Jan 11 16:27:29 CST 2016
     */
    int insert(TeamImage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team_image
     *
     * @mbggenerated Mon Jan 11 16:27:29 CST 2016
     */
    int insertSelective(TeamImage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team_image
     *
     * @mbggenerated Mon Jan 11 16:27:29 CST 2016
     */
    List<TeamImage> selectByExample(TeamImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team_image
     *
     * @mbggenerated Mon Jan 11 16:27:29 CST 2016
     */
    TeamImage selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team_image
     *
     * @mbggenerated Mon Jan 11 16:27:29 CST 2016
     */
    int updateByExampleSelective(@Param("record") TeamImage record, @Param("example") TeamImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team_image
     *
     * @mbggenerated Mon Jan 11 16:27:29 CST 2016
     */
    int updateByExample(@Param("record") TeamImage record, @Param("example") TeamImageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team_image
     *
     * @mbggenerated Mon Jan 11 16:27:29 CST 2016
     */
    int updateByPrimaryKeySelective(TeamImage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_team_image
     *
     * @mbggenerated Mon Jan 11 16:27:29 CST 2016
     */
    int updateByPrimaryKey(TeamImage record);
}
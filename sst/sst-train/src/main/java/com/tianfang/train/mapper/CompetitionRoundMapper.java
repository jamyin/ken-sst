package com.tianfang.train.mapper;

import com.tianfang.train.pojo.CompetitionRound;
import com.tianfang.train.pojo.CompetitionRoundExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CompetitionRoundMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_competition_round
     *
     * @mbggenerated Tue Feb 02 13:32:19 CST 2016
     */
    int countByExample(CompetitionRoundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_competition_round
     *
     * @mbggenerated Tue Feb 02 13:32:19 CST 2016
     */
    int deleteByExample(CompetitionRoundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_competition_round
     *
     * @mbggenerated Tue Feb 02 13:32:19 CST 2016
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_competition_round
     *
     * @mbggenerated Tue Feb 02 13:32:19 CST 2016
     */
    int insert(CompetitionRound record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_competition_round
     *
     * @mbggenerated Tue Feb 02 13:32:19 CST 2016
     */
    int insertSelective(CompetitionRound record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_competition_round
     *
     * @mbggenerated Tue Feb 02 13:32:19 CST 2016
     */
    List<CompetitionRound> selectByExample(CompetitionRoundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_competition_round
     *
     * @mbggenerated Tue Feb 02 13:32:19 CST 2016
     */
    CompetitionRound selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_competition_round
     *
     * @mbggenerated Tue Feb 02 13:32:19 CST 2016
     */
    int updateByExampleSelective(@Param("record") CompetitionRound record, @Param("example") CompetitionRoundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_competition_round
     *
     * @mbggenerated Tue Feb 02 13:32:19 CST 2016
     */
    int updateByExample(@Param("record") CompetitionRound record, @Param("example") CompetitionRoundExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_competition_round
     *
     * @mbggenerated Tue Feb 02 13:32:19 CST 2016
     */
    int updateByPrimaryKeySelective(CompetitionRound record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_competition_round
     *
     * @mbggenerated Tue Feb 02 13:32:19 CST 2016
     */
    int updateByPrimaryKey(CompetitionRound record);
}
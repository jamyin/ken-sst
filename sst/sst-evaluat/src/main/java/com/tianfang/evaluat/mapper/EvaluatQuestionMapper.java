package com.tianfang.evaluat.mapper;

import com.tianfang.evaluat.pojo.EvaluatQuestion;
import com.tianfang.evaluat.pojo.EvaluatQuestionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EvaluatQuestionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat_question
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int countByExample(EvaluatQuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat_question
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int deleteByExample(EvaluatQuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat_question
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat_question
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int insert(EvaluatQuestion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat_question
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int insertSelective(EvaluatQuestion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat_question
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    List<EvaluatQuestion> selectByExample(EvaluatQuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat_question
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    EvaluatQuestion selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat_question
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int updateByExampleSelective(@Param("record") EvaluatQuestion record, @Param("example") EvaluatQuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat_question
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int updateByExample(@Param("record") EvaluatQuestion record, @Param("example") EvaluatQuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat_question
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int updateByPrimaryKeySelective(EvaluatQuestion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat_question
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int updateByPrimaryKey(EvaluatQuestion record);
}
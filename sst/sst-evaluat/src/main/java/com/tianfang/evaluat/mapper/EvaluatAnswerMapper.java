package com.tianfang.evaluat.mapper;

import com.tianfang.evaluat.pojo.EvaluatAnswer;
import com.tianfang.evaluat.pojo.EvaluatAnswerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EvaluatAnswerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat_answer
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int countByExample(EvaluatAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat_answer
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int deleteByExample(EvaluatAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat_answer
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat_answer
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int insert(EvaluatAnswer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat_answer
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int insertSelective(EvaluatAnswer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat_answer
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    List<EvaluatAnswer> selectByExample(EvaluatAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat_answer
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    EvaluatAnswer selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat_answer
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int updateByExampleSelective(@Param("record") EvaluatAnswer record, @Param("example") EvaluatAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat_answer
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int updateByExample(@Param("record") EvaluatAnswer record, @Param("example") EvaluatAnswerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat_answer
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int updateByPrimaryKeySelective(EvaluatAnswer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat_answer
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int updateByPrimaryKey(EvaluatAnswer record);
}
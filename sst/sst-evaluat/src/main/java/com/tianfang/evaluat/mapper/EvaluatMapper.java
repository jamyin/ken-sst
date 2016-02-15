package com.tianfang.evaluat.mapper;

import com.tianfang.evaluat.pojo.Evaluat;
import com.tianfang.evaluat.pojo.EvaluatExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EvaluatMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int countByExample(EvaluatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int deleteByExample(EvaluatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int insert(Evaluat record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int insertSelective(Evaluat record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    List<Evaluat> selectByExample(EvaluatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    Evaluat selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int updateByExampleSelective(@Param("record") Evaluat record, @Param("example") EvaluatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int updateByExample(@Param("record") Evaluat record, @Param("example") EvaluatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int updateByPrimaryKeySelective(Evaluat record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_evaluat
     *
     * @mbggenerated Fri Jan 29 17:27:29 CST 2016
     */
    int updateByPrimaryKey(Evaluat record);
}
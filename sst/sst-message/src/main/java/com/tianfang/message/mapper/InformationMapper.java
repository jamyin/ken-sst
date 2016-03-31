package com.tianfang.message.mapper;

import com.tianfang.message.pojo.Information;
import com.tianfang.message.pojo.InformationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InformationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_information
     *
     * @mbggenerated Thu Mar 31 09:44:38 CST 2016
     */
    int countByExample(InformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_information
     *
     * @mbggenerated Thu Mar 31 09:44:38 CST 2016
     */
    int deleteByExample(InformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_information
     *
     * @mbggenerated Thu Mar 31 09:44:38 CST 2016
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_information
     *
     * @mbggenerated Thu Mar 31 09:44:38 CST 2016
     */
    int insert(Information record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_information
     *
     * @mbggenerated Thu Mar 31 09:44:38 CST 2016
     */
    int insertSelective(Information record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_information
     *
     * @mbggenerated Thu Mar 31 09:44:38 CST 2016
     */
    List<Information> selectByExampleWithBLOBs(InformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_information
     *
     * @mbggenerated Thu Mar 31 09:44:38 CST 2016
     */
    List<Information> selectByExample(InformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_information
     *
     * @mbggenerated Thu Mar 31 09:44:38 CST 2016
     */
    Information selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_information
     *
     * @mbggenerated Thu Mar 31 09:44:38 CST 2016
     */
    int updateByExampleSelective(@Param("record") Information record, @Param("example") InformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_information
     *
     * @mbggenerated Thu Mar 31 09:44:38 CST 2016
     */
    int updateByExampleWithBLOBs(@Param("record") Information record, @Param("example") InformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_information
     *
     * @mbggenerated Thu Mar 31 09:44:38 CST 2016
     */
    int updateByExample(@Param("record") Information record, @Param("example") InformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_information
     *
     * @mbggenerated Thu Mar 31 09:44:38 CST 2016
     */
    int updateByPrimaryKeySelective(Information record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_information
     *
     * @mbggenerated Thu Mar 31 09:44:38 CST 2016
     */
    int updateByPrimaryKeyWithBLOBs(Information record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_information
     *
     * @mbggenerated Thu Mar 31 09:44:38 CST 2016
     */
    int updateByPrimaryKey(Information record);
}
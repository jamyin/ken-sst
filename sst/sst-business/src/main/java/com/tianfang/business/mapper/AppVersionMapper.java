package com.tianfang.business.mapper;

import com.tianfang.business.pojo.AppVersion;
import com.tianfang.business.pojo.AppVersionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppVersionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_app_version
     *
     * @mbggenerated Tue Mar 29 14:39:25 CST 2016
     */
    int countByExample(AppVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_app_version
     *
     * @mbggenerated Tue Mar 29 14:39:25 CST 2016
     */
    int deleteByExample(AppVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_app_version
     *
     * @mbggenerated Tue Mar 29 14:39:25 CST 2016
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_app_version
     *
     * @mbggenerated Tue Mar 29 14:39:25 CST 2016
     */
    int insert(AppVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_app_version
     *
     * @mbggenerated Tue Mar 29 14:39:25 CST 2016
     */
    int insertSelective(AppVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_app_version
     *
     * @mbggenerated Tue Mar 29 14:39:25 CST 2016
     */
    List<AppVersion> selectByExample(AppVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_app_version
     *
     * @mbggenerated Tue Mar 29 14:39:25 CST 2016
     */
    AppVersion selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_app_version
     *
     * @mbggenerated Tue Mar 29 14:39:25 CST 2016
     */
    int updateByExampleSelective(@Param("record") AppVersion record, @Param("example") AppVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_app_version
     *
     * @mbggenerated Tue Mar 29 14:39:25 CST 2016
     */
    int updateByExample(@Param("record") AppVersion record, @Param("example") AppVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_app_version
     *
     * @mbggenerated Tue Mar 29 14:39:25 CST 2016
     */
    int updateByPrimaryKeySelective(AppVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_app_version
     *
     * @mbggenerated Tue Mar 29 14:39:25 CST 2016
	*/
    int updateByPrimaryKey(AppVersion record);
}
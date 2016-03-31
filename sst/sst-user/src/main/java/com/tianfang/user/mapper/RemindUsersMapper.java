package com.tianfang.user.mapper;

import com.tianfang.user.pojo.RemindUsers;
import com.tianfang.user.pojo.RemindUsersExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RemindUsersMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_remind_users
     *
     * @mbggenerated Thu Mar 31 17:37:38 CST 2016
     */
    int countByExample(RemindUsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_remind_users
     *
     * @mbggenerated Thu Mar 31 17:37:38 CST 2016
     */
    int deleteByExample(RemindUsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_remind_users
     *
     * @mbggenerated Thu Mar 31 17:37:38 CST 2016
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_remind_users
     *
     * @mbggenerated Thu Mar 31 17:37:38 CST 2016
     */
    int insert(RemindUsers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_remind_users
     *
     * @mbggenerated Thu Mar 31 17:37:38 CST 2016
     */
    int insertSelective(RemindUsers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_remind_users
     *
     * @mbggenerated Thu Mar 31 17:37:38 CST 2016
     */
    List<RemindUsers> selectByExample(RemindUsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_remind_users
     *
     * @mbggenerated Thu Mar 31 17:37:38 CST 2016
     */
    RemindUsers selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_remind_users
     *
     * @mbggenerated Thu Mar 31 17:37:38 CST 2016
     */
    int updateByExampleSelective(@Param("record") RemindUsers record, @Param("example") RemindUsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_remind_users
     *
     * @mbggenerated Thu Mar 31 17:37:38 CST 2016
     */
    int updateByExample(@Param("record") RemindUsers record, @Param("example") RemindUsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_remind_users
     *
     * @mbggenerated Thu Mar 31 17:37:38 CST 2016
     */
    int updateByPrimaryKeySelective(RemindUsers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sst_remind_users
     *
     * @mbggenerated Thu Mar 31 17:37:38 CST 2016
     */
    int updateByPrimaryKey(RemindUsers record);
}
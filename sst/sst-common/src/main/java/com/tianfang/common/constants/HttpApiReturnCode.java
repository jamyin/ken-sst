/**
 * 
 */
package com.tianfang.common.constants;

/**		
 * <p>Title: HttpApiReturnCode </p>
 * <p>Description: HTTP接口返回码定义</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Vincent	
 * @date 2015年5月21日 下午5:01:31	
 * @version 1.0
 */
public interface HttpApiReturnCode
{
    /**   
    * SUCCESS_CODE: 成功
    */
    final static String SUCCESS_CODE = "000000";
    /**
     * VENUES_NOT_FOUND: 场馆不存在
     */
    final static String VENUES_NOT_FOUND = "100001";
    /**   
     * CARD_NOT_FOUND: 卡片不存在
     */
    final static String CARDNO_IS_NULL = "100002";
    /**   
     * ORDER_NOT_FOUND: 订单不存
     */
    final static String ORDERITEMS_NOT_FOUND = "100003";
    
    /**   
     * GENERATE_ORDER_FAIL: 生成更新订单失败
     */
    final static String GENERATE_ORDER_FAIL = "100004";
    /**   
     * REPEAT_CHECK_CARD: 重复刷卡
     */
    final static String REPEAT_CHECK_CARD = "100005";
    /**   
     * CARD_NOT_BIND: 卡未绑定
     */
    final static String CARD_NOT_BIND = "100006";
    /**   
     * CARD_NOT_FOUND: 卡片不存在
     */
    final static String CARD_NOT_FOUND = "100007";
    /**
    * CARD_NOT_FOUND: 订单已消费
    */
   final static String ORDER_IS_USER = "100008";
   
   /**
   * REAL_NAME_AUTHENTICATION: 实名认证未通过
   */
   final static String REAL_NAME_AUTHENTICATION_FAIL = "100009";

    /**   
     * IP_IS_ILLEGAL: IP不合法
     */
    final static String IP_IS_ILLEGAL = "800001";
    /**   
    * SEQ_IS_NULL: seq为空
    */
    final static String SEQ_IS_NULL = "800002";
    /**   
    * PARPAN_IS_ILLEGAL: 参数不合法
    */
    final static String PARPAN_IS_ILLEGAL = "800003";
    /**   
    * NOT_AUTHORIZED_REQ: 无授权的请求
    */
    final static String NOT_AUTHORIZED_REQ = "800004";
    /**   
    * NOT_PERMISSION_EQUIPMENT: 该外围设备没有使用该接口的权限
    */
    final static String NOT_PERMISSION_EQUIPMENT = "800005";
    /**   
    * OUT_CONTINUED_SUM: 超出认证连续最多失败次数
    */
    final static String OUT_CONTINUED_SUM = "800006";
    /**   
    * OUT_DAY_SUM: 超出每日认证最多失败次数
    */
    final static String OUT_DAY_SUM = "800007";
    /**   
    * USER_IS_ILLEGAL: 成功
    */
    final static String USER_IS_ILLEGAL = "800008";
    /**   
    * SIDPWD_IS_ERROR: 外围设备访问密码错误
    */
    final static String SIDPWD_IS_ERROR = "800009";
    /**   
    * SID_IS_NULL: 外围设备编号不存在
    */
    final static String SID_IS_NULL = "800010";
    /**   
    * SIDPWD_IS_OVERDUE: 外围设备密码过期
    */
    final static String SIDPWD_IS_OVERDUE = "800011";
    /**   
    * PERPHERAL_REQ_FAIL: 访问外围设备失败——参数校验、鉴权等原因
    */
    final static String PERPHERAL_REQ_FAIL = "800012";
    /**   
     * MUST_PARAM_NULL: 输入的必选参数为空
     */
    final static String MUST_PARAM_NULL = "800013";
    /**   
     * PARAM_FORMAT_FAIL: 输入的参数格式错误
     */
    final static String PARAM_FORMAT_FAIL = "800014";
    /**   
     * GET_INFO_FAIL: 获取信息失败
     */
    final static String GET_INFO_FAIL = "800015";
    /**   
     * SYSTEM_EXCEPTION: 系统异常
     */
    final static String SYSTEM_EXCEPTION = "800016";
    /**   
     * DB_EXCEPTION: 数据库操作发生异常
     */
    final static String DB_EXCEPTION = "800017";
    /**   
     * DEVICE_TIME_OUT: 到外围设备超时
     */
    final static String DEVICE_TIME_OUT = "800018";
    /**   
     * CLIENT_TIME_OUT: 用户连接超时
     */
    final static String CLIENT_TIME_OUT = "800019";
    /**   
     * CLIENT_TIME_OUT: 用户基础信息不存在
     */
    final static String USERS_NOT_BIND = "800020";
    
    
    
    

}

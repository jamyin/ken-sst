package com.tianfang.common.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088911654968847";
	//								2088911654968847
	// 收款支付宝账号
	public static String seller_email = "steven_gan@ssic.cn";
	// 商户的私钥
	public static String key = "78m4n6pn9oge4gi3yt5sl7e5ncqgd0bs";
	
	//app 支付宝
	public static String appKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMEQKEliGGDsMIRXWNIM0v+bk7opmkp+7RQ3MtNk5u0wrtFomflNugjfE0nsJImIdTYMaAQ4xBHr+XOA/wlpAH2FuUoEYc0oZ+sDu4LDS5gM1sFcY4iRsJUJKVfSzmbtPp5sDMKh+PGwwxiB5QZTktNl8treLRNpl02D3S6on/mlAgMBAAECgYBQtHRi2MLgIVvWtrzGcd3yRBLpC2MYKR7qCHkSrWdrV6J8n4FeGgPemYHysOhFJen5gg4pVaGA7GgGxLHYOTCejaWzqOkX1q3XQzo0zajVfVtVFY8lhC4+J+cQBHbjMhdS6KOwrvJpzF+llTM04tG7Y3tir4AX1Nvq46ZFi0hhgQJBAPK8Kue+dnxVd/o6N7ovWrkUcktWeVC0mSirsYB3etMSqbAzjjpUUnPrEea6h/TJaipCPKMNKM/HcPEGHH6sLSkCQQDLnRYBMh1Mqx6EIKvh7FRLRD+VemXm1N1ucKSbT6MFB52RvKLBscWiy/jOSpTj3UZ/d5jIwqC7ymglYybIJ3wdAkB1sflXzskitkprYQkeVNG3hgt5Lh+6mybcSovRj51VdOokTos7CyHDpQpUBsPfVD4O6Xj/UmUkgj7oDx+H6DIpAkEAyE+gCEkfHf/yo5XowvhW7T0VOJ8ThQXBWhcjOVAdVqNpVqi3jcqwXjpAaeJhm9JiuqAUBN3EdzrnPnDPBHYj2QJBAOVWjBbFe/5HCp/d0EnUqlBJ3MiF2VjS0jsS7WfWn6CUxAs+x+fzl5elkIfbDJvo1FQQudNraPmlUnBW6RN3cus=";
//								78m4n6pn9oge4gi3yt5sl7e5ncqgd0bs
	
	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "MD5";

}

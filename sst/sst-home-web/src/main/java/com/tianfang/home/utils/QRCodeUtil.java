package com.tianfang.home.utils;

import com.tianfang.common.qrcode.TwoDimensionCode;
import com.tianfang.common.util.PropertiesUtils;

import java.io.File;

/**		
 * <p>Title: QRCodeUtil </p>
 * <p>Description: 类描述:生成用户二维码工具类</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年3月8日上午11:17:15
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class QRCodeUtil {

	private static final String QRCODE_URL = PropertiesUtils.getProperty("upload.url");
	private static final String basePath;
	private static final String SUFFIX = ".png";
	
	static{
		basePath = new StringBuilder(File.separator).append("upload").append(File.separator).append("qrcodes").append(File.separator).toString();
	}
	
	/**
	 * 根据用户id生成二维码
	 * @param userId
	 * @return 二维码生成路径
	 * @author xiang_wang
	 * 2016年3月8日上午11:21:46
	 */
	public static String createCode(String userId){
		StringBuilder url = new StringBuilder();
		StringBuilder filePath = new StringBuilder(basePath).append(userId).append(SUFFIX);
		TwoDimensionCode.encoderQRCode(userId, url.append(QRCODE_URL).append(filePath).toString());
		return filePath.toString();
	}
}
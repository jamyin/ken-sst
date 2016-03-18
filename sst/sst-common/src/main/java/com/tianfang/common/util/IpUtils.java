package com.tianfang.common.util;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**		
 * <p>Title: IpUtils </p>
 * <p>Description: 类描述:ip工具类,包含获取ip,根据ip获取城市信息等功能</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年3月16日上午10:28:28
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class IpUtils {
	private final static String ENCODING = "utf-8";
	private final static String API_URL = "http://ip.taobao.com/service/getIpInfo.php";
	
	public static String getIRealIPAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)
				|| "null".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)
				|| "null".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)
				|| "null".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static void main(String[] args) {
		String ip = "10.10.11.79";
		String city = getCity(ip);
		System.out.println(city);
	}
	
	/**
	 * 根据ip查询获取市区信息
	 * @param ip
	 * @return
	 * @author xiang_wang
	 * 2016年3月16日上午10:24:18
	 */
	public static String getCity(String ip){
		if (ip.equals("127.0.0.1")){
			return null;
		}
		Address add = null;
		try {
			add = getAddress(ip);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != add){
			return add.getCity();
		}
		return null;
	}

	/**
	 * 根据ip获取地址详情
	 * @param ip
	 * @return
	 * @throws Exception
	 * @author xiang_wang
	 * 2016年3月16日上午10:11:11
	 */
	public static Address getAddress(String ip) throws Exception {
		if (ip.equals("127.0.0.1")){
			return null;
		}

		if (!isIpv4(ip)){
			throw new RuntimeException("ip地址不合法");
		}
		
		String returnStr = getRs(API_URL, "ip="+ip, ENCODING);

		if (returnStr != null) {
			Data data = JSON.parseObject(returnStr, Data.class);
			if ("0".equals(data.getCode())) {
				Address add = data.getData();
				add.setCountry(decodeUnicode(add.getCountry()));
				add.setArea(decodeUnicode(add.getArea()));
				add.setRegion(decodeUnicode(add.getRegion()));
				add.setCity(decodeUnicode(add.getCity()));
				add.setCounty(decodeUnicode(add.getCounty()));
				add.setIsp(decodeUnicode(add.getIsp()));

				return add;
			} else {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * 校验ip是否合法
	 * @param ipAddress
	 * @return
	 * @author xiang_wang
	 * 2016年3月16日上午10:15:23
	 */
	public static boolean isIpv4(String ipAddress) {  
        String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."  
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."  
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."  
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";  
  
        Pattern pattern = Pattern.compile(ip);  
        Matcher matcher = pattern.matcher(ipAddress);  
        return matcher.matches();  
    }  

	/**
	 * 从url获取结果
	 * 
	 * @param path
	 * @param params
	 * @param encoding
	 * @return
	 */
	private static String getRs(String path, String params, String encoding) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(path);
			connection = (HttpURLConnection) url.openConnection();// 新建连接实例
			connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒
			connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒
			connection.setDoInput(true);// 是否打开输出流true|false
			connection.setDoOutput(true);// 是否打开输入流true|false
			connection.setRequestMethod("POST");// 提交方法POST|GET
			connection.setUseCaches(false);// 是否缓存true|false
			connection.connect();// 打开连接端口

			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(params);
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}

			reader.close();
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();// 关闭连接
		}

		return null;
	}

	/**
	 * 字符转码
	 * 
	 * @param theString
	 * @return
	 */
	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer buffer = new StringBuffer(len);
		for (int i = 0; i < len;) {
			aChar = theString.charAt(i++);
			if (aChar == '\\') {
				aChar = theString.charAt(i++);
				if (aChar == 'u') {
					int val = 0;
					for (int j = 0; j < 4; j++) {
						aChar = theString.charAt(i++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							val = (val << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							val = (val << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							val = (val << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed      encoding.");
						}
					}

					buffer.append((char) val);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					}
					if (aChar == 'r') {
						aChar = '\r';
					}
					if (aChar == 'n') {
						aChar = '\n';
					}
					if (aChar == 'f') {
						aChar = '\f';
					}
					buffer.append(aChar);
				}
			} else {
				buffer.append(aChar);
			}
		}
		return buffer.toString();
	}
}
/**		
 * <p>Title: Address </p>
 * <p>Description: 类描述:地址数据封装</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年3月16日上午10:18:43
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
class Address {
	private String country; // 国家
	private String area; // 地区
	private String region; // 省份
	private String city; // 市区
	private String county; // 地区
	private String isp; // isp公司

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

	@Override
	public String toString() {
		return "Address [country=" + country + ", area=" + area
				+ ", region=" + region + ", city=" + city + ", county="
				+ county + ", isp=" + isp + "]";
	}
}

/**		
 * <p>Title: Data </p>
 * <p>Description: 类描述:ip接口返回数据封装</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年3月16日上午10:19:02
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
class Data {
	private String code; // 状态码 0-正确,1-错误
	private Address data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Address getData() {
		return data;
	}

	public void setData(Address data) {
		this.data = data;
	}
}
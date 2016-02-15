package com.tianfang.common.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetPostUtil {

	private static final String URL = "http://127.0.0.1:8080/BJDR-CURD-Api";

	// 微信登录请求
	public static String weiLink(String url) {

		// 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
		String getURL =  url;// URLEncoder.encode(address, "utf-8")
		URL getUrl = null;
		BufferedReader reader = null;
		HttpURLConnection connection = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			getUrl = new URL(getURL);

			// 根据拼凑的URL，打开连接，URL.openConnection()函数会根据URL的类型，返回不同的URLConnection子类的对象，在这里我们的URL是一个http，因此它实际上返回的是HttpURLConnection
			connection = (HttpURLConnection) getUrl.openConnection();

			// 建立与服务器的连接，并未发送数据
			connection.connect();

			// 发送数据到服务器并使用Reader读取返回的数据
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "utf-8"));

			String lines;

			while ((lines = reader.readLine()) != null) {

				stringBuffer.append(lines);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 断开连接
				if (reader != null) {
					reader.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return stringBuffer.toString();

	}

	// GET请求
	public static String get(String url) {

		// 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
		String getURL = URL + url;// URLEncoder.encode(address, "utf-8")
		URL getUrl = null;
		BufferedReader reader = null;
		HttpURLConnection connection = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			getUrl = new URL(getURL);

			// 根据拼凑的URL，打开连接，URL.openConnection()函数会根据URL的类型，返回不同的URLConnection子类的对象，在这里我们的URL是一个http，因此它实际上返回的是HttpURLConnection
			connection = (HttpURLConnection) getUrl.openConnection();

			// 建立与服务器的连接，并未发送数据
			connection.connect();

			// 发送数据到服务器并使用Reader读取返回的数据
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "utf-8"));

			String lines;

			while ((lines = reader.readLine()) != null) {

				stringBuffer.append(lines);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 断开连接
				if (reader != null) {
					reader.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return stringBuffer.toString();

	}

	public static String post(String url, String parameters) {

		// Post请求的url，与get不同的是不需要带参数
		URL postUrl;
		BufferedReader reader = null;
		HttpURLConnection connection = null;
		DataOutputStream out = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			postUrl = new URL(URL + url);
			// 打开连接
			connection = (HttpURLConnection) postUrl.openConnection();

			// 打开读写属性，默认均为false
			connection.setDoOutput(true);
			connection.setDoInput(true);

			// 设置请求方式，默认为GET
			connection.setRequestMethod("POST");

			// Post 请求不能使用缓存
			connection.setUseCaches(false);

			// URLConnection.setInstanceFollowRedirects 是成员函数，仅作用于当前函数
			connection.setInstanceFollowRedirects(true);

			// 配置连接的Content-type，配置为application/x-
			// www-form-urlencoded的意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode进行编码
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			// 连接，从postUrl.openConnection()至此的配置必须要在 connect之前完成，
			// 要注意的是connection.getOutputStream()会隐含的进行调用 connect()，所以这里可以省略
			// connection.connect();

			out = new DataOutputStream(connection.getOutputStream());

			// 正文内容其实跟get的URL中'?'后的参数字符串一致
			// DataOutputStream.writeBytes将字符串中的16位的 unicode字符以8位的字符形式写道流里面
			out.writeBytes(parameters);
			out.flush();
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			String line;

			while ((line = reader.readLine()) != null) {
				stringBuffer.append(line);
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			} // flush and close
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (connection != null) {

				connection.disconnect();
			}
		}
		return stringBuffer.toString();

	}

}

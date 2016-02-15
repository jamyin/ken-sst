package com.tianfang.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

//import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.impl.Log4JLogger;

/**
 * beta 工具类
 * 
 * @url 网站url地址
 * @fileName 需要生成的图片信息
 * @filePath 图片保存路径
 * @width 图片像素
 * @heigh 图片像素
 * **/

@SuppressWarnings("unused")
public class UrlToImagesUtils {

	Log4JLogger log4j = new Log4JLogger();

	/**
	 ** 定义 window 下 exe 的 文件名称
	 ***/
	private String WIN_EXE_NAME = "CutyCapt.exe";
	private String LINUX_SH = "/usr/local/cutycapt/CutyCapt";

	private String url;
	private String fileName;
	private String filePath;
	private int width;
	private int height;

	public void init(String url, String fileName, String filePath, int width,
			int height) {
		this.url = url;
		this.fileName = fileName;
		this.filePath = filePath;
		this.width = width;
		this.height = height;
	}

	public UrlToImagesUtils(String url, String fileName, String filePath, int width,
			int height) {
		this.url = url;
		this.fileName = fileName;
		this.filePath = filePath;
		this.width = width;
		this.height = height;
	}

	/**
	 * 根据URL截取网站缩略图信息
	 * 
	 * @url 网站url地址
	 * @fileName 需要生成的图片信息
	 * @filePath 图片保存路径
	 * @width 图片像素
	 * @heigh 图片像素
	 * **/
	public String exceUrlImg() {
		// 当前为 win 系统
		if (OsUtils.isWindowsOS()) {
			return cmdExce();
		} else {
			return shExce();
		}
	}

	/**
	 * 判断传入参数是否存在
	 * **/
	public StringBuffer checkParam() {
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isBlank(url)) {
			sb.append("url不能为空");
		}
		if (StringUtils.isBlank(fileName)) {
			sb.append("文件名称不能为空");
		}
		return sb;
	}

	/**
	 * window 下 执行 cmd bat 文件
	 * **/
	public String cmdExce() {
		StringBuffer cp = checkParam();
		if (!StringUtils.isEmpty(checkParam().toString())) {
		} else {
			System.out.println("cmdExce" + url + fileName + filePath + width
					+ height);
			String filePathName = filePath + File.separator + fileName;
			System.out.println(filePathName + "cmdExce" + url + fileName
					+ filePath + width + height);
			String exePath = ClassLoader.getSystemResource(WIN_EXE_NAME)
					.toString();
			System.out.println("exePath" + exePath);
			String cmd = "cmd /c start " + exePath + " --url=" + url
					+ " --out=" + filePathName + "";
			Process p = null;
			try {
				p = Runtime.getRuntime().exec(cmd);
			} catch (IOException e) {
				cp.append("cmdExce-脚本执行错误");
			}
			if (p != null) {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						p.getInputStream()));
				String line = null;
				try {
					while ((line = in.readLine()) != null) {
						System.out.println(line);
					}
				} catch (IOException e) {
					cp.append("cmdExce-脚本执行错误");
				}
			}
		}
		return cp.toString();
	}

	/**
	 * linux 下 sh 文件 执行
	 * **/
	public String shExce() {
		StringBuffer cp = checkParam();
		if (!StringUtils.isEmpty(checkParam().toString())) {
		} else {
			System.out.println("shExce" + url + fileName + filePath + width
					+ height);
			String filePathName = filePath + File.separator + fileName;
			try {
				String[] cmd = new String[] {
						"/bin/sh",
						"-c",
						" xvfb-run --server-args='-screen 0, 1024x768x24' "
								+ LINUX_SH + " --url=" + url + " --out="
								+ filePathName + " " };
				Process ps = Runtime.getRuntime().exec(cmd);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						ps.getInputStream()));
				StringBuffer sb = new StringBuffer();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				String result = sb.toString();
				System.out.println(result);
			} catch (Exception e) {
				cp.append("shExce-脚本执行错误");
				// e.printStackTrace();
			}
		}
		return cp.toString();
	}
}
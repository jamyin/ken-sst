package com.tianfang.home.controller;

import com.google.gson.Gson;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.FileUtils;
import com.tianfang.common.util.LogWriter;
import com.tianfang.common.util.PropertiesUtils;
import com.tianfang.home.dto.UploadDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * 
 * 此类描述的是：用于上传对外接口
 * 
 * @author: cwftalus@163.com
 * @version: 2015年4月1日 上午11:40:00
 */

@Controller
@RequestMapping(value = "/api")
public class UploadController {
	protected static final Log logger = LogFactory.getLog(UploadController.class);

	@RequestMapping(value = "/upload.htm")
	private void upload(@RequestParam("file") MultipartFile myfile,
			HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("utf-8"); // 设置编码
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		Response<UploadDto> result = new Response<UploadDto>();
		UploadDto uploadDto = new UploadDto();
		if (!myfile.isEmpty()) {
			long fileSize = printFileInfo(myfile);//判断图片大小 超过1M不允许上传
			if(fileSize > DataStatus._FILESIZE_){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("上传图片大小不允许超过1M");
			}
			
			try {
				uploadDto.setUrl(uploadImage(myfile));
				result.setData(uploadDto);
				result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setMessage("图片上传成功");
			} catch (IOException e) {
				result.setData(uploadDto);
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("图片上传失败");
				logger.debug(LogWriter.getStackMsg(e.getStackTrace()));
			}
		}
		
		PrintWriter writer = response.getWriter();
		Gson gson = new Gson();
		writer.print(gson.toJson(result));		
		
		writer.close();		
	}
	
	@RequestMapping(value = "/uploadEx.do")
	private void uploadEx(@RequestParam("fileex") MultipartFile myfileex,
			HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("utf-8"); // 设置编码
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		Response<UploadDto> result = new Response<UploadDto>();
		UploadDto uploadDto = new UploadDto();
		if (!myfileex.isEmpty()) {
			long fileSize = printFileInfo(myfileex);//判断图片大小 超过1M不允许上传
			if(fileSize > DataStatus._FILESIZE_){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("上传图片大小不允许超过1M");
			}
			
			try {
				uploadDto.setUrl(uploadImage(myfileex));
				result.setData(uploadDto);
				result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setMessage("图片上传成功");
			} catch (IOException e) {
				result.setData(uploadDto);
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("图片上传失败");
				logger.debug(LogWriter.getStackMsg(e.getStackTrace()));
			}
		}
		
		PrintWriter writer = response.getWriter();
		Gson gson = new Gson();
		writer.print(gson.toJson(result));		
		
		writer.close();		
//		return result;
	}

	public static Response<UploadDto> uploadImg(MultipartFile myfile){
		Response<UploadDto> result = new Response<UploadDto>();
		UploadDto uploadDto = new UploadDto();
		if (!myfile.isEmpty()) {
			long fileSize = printFileInfo(myfile);//判断图片大小 超过1M不允许上传
			if(fileSize > DataStatus._FILESIZE_){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("上传图片大小不允许超过1M");
			}

			try {
				uploadDto.setUrl(uploadImage(myfile));
				result.setData(uploadDto);
				result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setMessage("图片上传成功");
			} catch (IOException e) {
				result.setData(uploadDto);
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("图片上传失败");
				logger.debug(LogWriter.getStackMsg(e.getStackTrace()));
			}
		}
		return result;
	}

	private static String uploadImage(MultipartFile myfile)
			throws IOException {
		// 如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解
		// 如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解
		String context = "/upload";
		String realPath = PropertiesUtils.getProperty("upload.url");
		String fileDe = DateUtils.format(new Date(), DateUtils.YMD);
		String fileName = FileUtils.getUploadFileName(myfile.getOriginalFilename());
		logger.info("filePaht :"+realPath + "/" +fileDe +"/"+ fileName);
		File file = new File(realPath + "/" +context+"/"+fileDe);
        //如果文件夹不存在则创建    
        if(!file.exists() && !file.isDirectory()) {
            file.mkdir();    
        }
        
		FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath + "/" +context+"/"+fileDe , fileName));
		return (context + "/" + fileDe + "/" + fileName);
	}

	private static long printFileInfo(MultipartFile myfile) {
		logger.info("文件长度: " + myfile.getSize());
		logger.info("文件类型: " + myfile.getContentType());
		logger.info("文件名称: " + myfile.getName());
		logger.info("文件原名: " + myfile.getOriginalFilename());
		logger.info("========================================");
		return myfile.getSize();
	}
}

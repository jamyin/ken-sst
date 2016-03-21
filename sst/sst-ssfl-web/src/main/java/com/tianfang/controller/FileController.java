package com.tianfang.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ffmpeg.FFmpegUtil;
import com.tianfang.common.util.PropertiesUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;

@Controller
public class FileController {

	private Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 图片或视频上传
	 * <br>图片格式包括：<b>.gif .png .jpg</b>
	 * <br>视频格式包括：<b>.flv
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value="/file/upload")
	public String fileuploadV02(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
        Map<String, Object> map = new HashMap<String, Object>();
        request.setCharacterEncoding("utf-8");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        String path = PropertiesUtils.getProperty("upload.url");
        factory.setRepository(new File(path));
        factory.setSizeThreshold(100*1024*1024);
        ServletFileUpload upload = new ServletFileUpload(factory);
        
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String context = "upload" + File.separator + format.format(new Date());
//      String fileName = FileUtils.getUploadFileNameBybase64(baseImg)+"."+prefix;
        
        StringBuffer rootPath = new StringBuffer(PropertiesUtils.getProperty("upload.url"));
        rootPath.append(File.separator).append(context);
        File file =new File(rootPath.toString());        
        //如果文件夹不存在则创建    
        if(!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
        try {
            //可以上传多个文件
            List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
            for(FileItem item : list){
                if(!item.isFormField()){
                    String name = item.getName() ;
                    String fileSuffix  = name.substring(name.lastIndexOf(".")+1,name.length());
                    if((!StringUtils.isFlvVideo(fileSuffix)) && (!StringUtils.isSpecPic(fileSuffix))){
                    	
                    	throw new Exception("不是指定格式的视频和图片");
                    }else{
                    	
                    	String newName = UUIDGenerator.getUUID32Bit() + "." + fileSuffix;
                        String filePath = rootPath.append(File.separator).append(newName).toString();
                        OutputStream out = new FileOutputStream(filePath);
                        InputStream in = item.getInputStream() ;
                        int length = 0 ;
                        byte [] buf = new byte[1024] ;
                        while( (length = in.read(buf) ) != -1){
                            out.write(buf, 0, length);
                        }
                        in.close();
                        out.close();
                        String fileStaticPath = (File.separator+context + File.separator + newName);
                        fileStaticPath = fileStaticPath.replaceAll("\\\\", "/");
                        map.put("filePath",fileStaticPath);
                        // 验证是否是指定格式的视频或图片
                        if(StringUtils.isFlvVideo(fileSuffix)){
                        	String imageSavePath = File.separator+context + File.separator + UUIDGenerator.getUUID32Bit() + ".jpg";
                        	map.put("imageSavePath",imageSavePath);
                        	new FFmpegUtil(PropertiesUtils.getProperty("upload.url")+File.separator+fileStaticPath).makeScreenCut(PropertiesUtils.getProperty("upload.url")+imageSavePath, 318, 290);
                        }
                        break;
                    }
                    
                }
            }
            map.put("status", DataStatus.HTTP_SUCCESS);
            map.put("data", "视频或图片上传成功");
        }catch (Exception e) {
        	log.error("文件上传出错", e);
            map.put("status", DataStatus.HTTP_FAILE);
            map.put("data", "不是指定格式的视频或图片");
        }
        Gson gson = new Gson();
        String msg = gson.toJson(map);
        return msg;
    }
}

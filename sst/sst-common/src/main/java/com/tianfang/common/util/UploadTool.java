package com.tianfang.common.util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tianfang.common.constants.DataStatus;

/**
 *@author  作者 E-mail:jamhihi@126.com 
 *@date 创建时间：2015年11月9日 下午2:30:19
 *@version V1.0
 *@parameter
 *@since
 *@return  文件路径的list
 *@deprecated   文件名乱码：servlet-context.xml 设置编码格式即可
 * <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
        <property name="maxUploadSize" value="10240000"/>    
        <property name="resolveLazily" value="true"/>
        <property name="defaultEncoding" value="UTF-8"/>
   </bean>
 */

@Controller  
@RequestMapping("/file") 
@ResponseBody
public class UploadTool {
    @ResponseBody
    @RequestMapping("/upload.do"   )  
    public Map<String, String> upload(@RequestParam("file") MultipartFile file,HttpServletRequest request){      	
    	//String context = "/upload";
		String realPath = PropertiesUtils.getProperty("upload.url");
		String fileDe = DateUtils.format(new Date(), DateUtils.YMD);
		String path = "";
		String filePath = "";
		String fileName = ""; //重新新命名
		String realName = "";
		Map<String, String> m = new HashMap<String, String>();
    	if(file.isEmpty()){
    		System.out.println("请选择需要上传的文件!");  
    		m.put("message", "请选择需要上传的文件!");
	       	return m;
    	}else{
    			realName = file.getOriginalFilename();
 	            System.out.println("fileName4---------->" + realName); 
 	            if(file.getSize()> DataStatus._FILESIZE_){
 	       		System.out.println("上传图片大小不允许超过1M");
 	       		m.put("message", "上传图片大小不允许超过1M");
 	       		return m;
 	            }
 	                int pre = (int) System.currentTimeMillis();  
 	                path = realPath + "/" + fileDe;
 	                fileName = this.getUploadFileName(file.getOriginalFilename());
 	                filePath = path  + "/" + fileName;
 	                File f = new File(path);
 	                //如果文件夹不存在则创建    
 	                if(!f.exists() && !f.isDirectory()) {
 	                  f.mkdir();    
 	                }
 	                try {  
 	                	file.transferTo(new File(path + "/" + fileName));
 	                    int finaltime = (int) System.currentTimeMillis();  
 	                    System.out.println("上传3共耗时：" + (finaltime - pre) + "毫秒");  
 	                }catch (FileNotFoundException e) {
 	                    e.printStackTrace();
 	                }catch (IOException e) {  
 	                    e.printStackTrace();  
 	                }  
    	}
        System.out.println("上传成功4"); 
        m.put("fileUrl", filePath);
        m.put("realName", realName);
        return m;  
    }
    
    public  String getUploadFileName(String fileName) {
		String tempFile = fileName.substring(fileName.lastIndexOf(".")+1);
		return UUIDGenerator.getUUID32Bit() + "." + tempFile;
	}
}  
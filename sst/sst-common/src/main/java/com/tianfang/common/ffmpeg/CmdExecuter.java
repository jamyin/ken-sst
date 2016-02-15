package com.tianfang.common.ffmpeg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;  
import java.util.List;  
  
/**		
 * <p>Title: CmdExecuter 命令执行器</p>
 * <p>Description: 类描述:封装对操作系统命令行发送指令相关操作</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2015年12月2日下午1:46:21
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class CmdExecuter {  
      
	/**
	 * 执行指令 
	 * @param cmd 执行指令
	 * @param getter 指令返回处理接口，若为null则不处理输出 
	 * @author xiang_wang
	 * 2015年12月2日下午1:47:11
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void exec(List<String> cmd, IStringGetter getter ) throws IOException, InterruptedException{  
        ProcessBuilder builder = new ProcessBuilder();    
        builder.command(cmd);  
        builder.redirectErrorStream(true);  
        Process proc = builder.start();  
        BufferedReader stdout = new BufferedReader(new InputStreamReader(proc.getInputStream()));  
        String line;  
        while ((line = stdout.readLine()) != null) {  
            if( getter != null ){
            	  getter.dealString(line);  
            } 
        }  
        proc.waitFor();     
        stdout.close();  
    }  
} 
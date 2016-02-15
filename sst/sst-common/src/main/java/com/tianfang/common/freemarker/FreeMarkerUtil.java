package com.tianfang.common.freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerUtil {
    Configuration freemarker_cfg = null;

	public static void writeTo(ServletContext context, Map map, String templatePath,
            String templateName, String htmlPath) throws IOException, TemplateException {
        Configuration freemarkerCfg = new Configuration();
        // 设置要解析的模板所在的目录，并加载模板文件
        freemarkerCfg.setServletContextForTemplateLoading(context, templatePath);
        freemarkerCfg.setEncoding(Locale.getDefault(), "UTF-8");
 
        // 获取模板,并设置编码方式
        Template template = freemarkerCfg.getTemplate(templateName);
        template.setEncoding("UTF-8");
        File htmlFilel = new File(context.getRealPath("") +File.separator +"html"+File.separator+"channel");
        if(! htmlFilel.exists()) {
            htmlFilel.mkdir();
        }
 
//        File htmlFile = new File(context.getRealPath("") + File.separator + htmlPath);
        File htmlFile = new File(htmlPath);
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(htmlFile), "UTF-8"));
 
        // 合并数据模型与模板
        template.process(map, out);
 
        out.flush();
        out.close();
    }
    
    
    // templatePath模板文件存放路径
    // templateName 模板文件名称 模板文件名,相对路径,例如"/tpxw/view.ftl"
    // @param rootMap 用于处理模板的属性Object映射
    // @param htmlFilePath 要生成的静态文件的路径,相对设置中的根路径,例如 "/tpxw/1/2005/4/"
    // @param htmlFileName 要生成的文件名,例如 "1.htm"

    public static void buildHtml(String templatePath, String templateName,
	    String sRootDir, String htmlFilePath, String htmlFileName,
	    Map<?, ?> rootMap) {
	Writer out=null;
	FileOutputStream fos=null;
	try {
	    // 如果根路径存在,则递归创建子目录
	    creatDirs(sRootDir, htmlFilePath);

	    Configuration config = new Configuration();
	    // 设置要解析的模板所在的目录，并加载模板文件
	    config.setDirectoryForTemplateLoading(new File(templatePath));
	    // 设置包装器，并将对象包装为数据模型
	    config.setObjectWrapper(new DefaultObjectWrapper());
	    // 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
	    // 否则会出现乱码
	    Template template = config.getTemplate(templateName, "UTF-8");
	    // 合并数据模型与模板
	    File afile = new File(sRootDir + htmlFilePath + htmlFileName);
	    fos = new FileOutputStream(afile);
	    out = new OutputStreamWriter(fos, "UTF-8");
	    template.process(rootMap, out);
	    out.flush();
	    
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (TemplateException e) {
	    e.printStackTrace();
	}finally{
	    try {
		out.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    try {
		fos.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    /**
     * 创建多级目录
     * 
     * @param aParentDir
     *            String
     * @param aSubDir
     *            以 / 开头
     * @return boolean 是否成功
     */
    public static boolean creatDirs(String aParentDir, String aSubDir) {
	File aFile = new File(aParentDir);
	if (aFile.exists()) {
	    File aSubFile = new File(aParentDir + aSubDir);
	    if (!aSubFile.exists()) {
		return aSubFile.mkdirs();
	    } else {
		return true;
	    }
	} else {
	    return false;
	}
    }

    /**
     * 获取freemarker的配置. freemarker本身支持classpath,目录和从ServletContext获取.
     */
    protected Configuration getFreeMarkerCFG() {

	if (null == freemarker_cfg) {
	    // Initialize the FreeMarker configuration;
	    // - Create a configuration instance
	    freemarker_cfg = new Configuration();
	    // - FreeMarker支持多种模板装载方式,可以查看API文档,都很简单:路径,根据Servlet上下文,classpath等等
	    // htmlskin是放在classpath下的一个目录
	    freemarker_cfg.setClassForTemplateLoading(this.getClass(),
		    " /htmlskin ");
	}

	return freemarker_cfg;
    }

}

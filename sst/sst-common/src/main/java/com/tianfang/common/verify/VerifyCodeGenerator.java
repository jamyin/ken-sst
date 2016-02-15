package com.tianfang.common.verify;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tianfang.common.exception.SystemException;
import com.tianfang.common.util.StringUtils;

/**
 * 验证码图片生成器
 * @author rkzhang
 */
public class VerifyCodeGenerator {

	/**
	* @param req
	* @param resp
	* @throws javax.servlet.ServletException
	* @throws java.io.IOException
	*/
	public static void createImage(HttpServletRequest req, HttpServletResponse resp, VerfiyCodeConfig config) {
		config.calculate();
		
		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(config.getWidth(), config.getHeight(),
		BufferedImage.TYPE_INT_RGB);
		Graphics2D gd = buffImg.createGraphics();
		 
		// 创建一个随机数生成器类
		Random random = new Random();
		 
		// 将图像填充为白色
		gd.setColor(Color.WHITE);
		gd.fillRect(0, 0, config.getWidth(), config.getHeight());
		 
		// 创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("Fixedsys", Font.PLAIN, config.getFontHeight());
		// 设置字体。
		gd.setFont(font);
		 
		// 画边框。
		gd.setColor(Color.BLACK);
		gd.drawRect(0, 0, config.getWidth() - 1, config.getHeight() - 1);
		 
		// 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。
		gd.setColor(Color.BLACK);
		for (int i = 0; i < 160; i++) {
		int x = random.nextInt(config.getWidth());
		int y = random.nextInt(config.getHeight());
		int xl = random.nextInt(12);
		int yl = random.nextInt(12);
		gd.drawLine(x, y, x + xl, y + yl);
		}
		 
		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		StringBuffer randomCode = new StringBuffer();
		int red = 0, green = 0, blue = 0;
		 
		// 随机产生codeCount数字的验证码。
		for (int i = 0; i < config.getCodeCount(); i++) {
		// 得到随机产生的验证码数字。
		String strRand = String.valueOf(config.getCodeSequence()[random.nextInt(36)]);
		// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
		red = random.nextInt(255);
		green = random.nextInt(255);
		blue = random.nextInt(255);
		 
		// 用随机产生的颜色将验证码绘制到图像中。
		gd.setColor(new Color(red, green, blue));
		gd.drawString(strRand, (i + 1) * config.getXx(), config.getCodeY());
		 
		// 将产生的四个随机数组合在一起。
		randomCode.append(strRand);
		}
		// 将四位数字的验证码保存到Session中。
		HttpSession session = req.getSession();
		String sessionKey = StringUtils.isEmpty(config.getSessionKey()) ? "validateCode" : config.getSessionKey();
		session.setAttribute(sessionKey, randomCode.toString());
		 
		// 禁止图像缓存。
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		 
		resp.setContentType("image/jpeg");
		 
		// 将图像输出到Servlet输出流中。
		ServletOutputStream sos = null;
		try {
			sos = resp.getOutputStream();
			ImageIO.write(buffImg, "jpeg", sos);
		} catch (Exception e) {
			throw new SystemException(e);
		} finally {
			 if(sos != null){
				 try {
					sos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			 }
		}
		
	}
}

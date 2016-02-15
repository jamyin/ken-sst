package com.tianfang.common.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import org.springframework.stereotype.Service;

/**
 * Created by user on 15-3-17.
 */
@Service
public class RandomPicTools  {


    private Random random = new Random();
    // 随机产生数字与字母组合的字符串
    private String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//


    /*
     * 获得颜色
     */
    public Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }

    /*
    * 获得字体
    */
    public Font getFont() {
        return new Font("Fixedsys", Font.CENTER_BASELINE, 18);
    }
    
    /*
     * 绘制字符串
     */
    public String drowString(Graphics g, String randomString, int i) {
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random
                .nextInt(121)));
        String rand = String.valueOf(getRandomString(random.nextInt(randString
                .length())));
        randomString += rand;
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, 13 * i, 16);
        return randomString;
    }
    
    /*
   * 绘制干扰线
   */
    public void drowLine(Graphics g,int width,int height) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }
    
    /*
    * 获取随机的字符
    */
    public String getRandomString(int num) {
        return String.valueOf(randString.charAt(num));
    }
}

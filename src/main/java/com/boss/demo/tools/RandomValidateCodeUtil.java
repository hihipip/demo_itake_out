package com.boss.demo.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class RandomValidateCodeUtil {
    public static final String RANDOMCODEKEY = "randomKey";//放到session中的key
    private String randString = "123456789ABCDEFGHIJKLMNPQRSTUVWXYZ";//随机产生数字与字母组合的字符串
    private int width = 95;// 图片宽
    private int height = 40;// 图片高
    private int stringNum = 4;// 随机产生字符数量
    private static final Logger logger = LoggerFactory.getLogger(RandomValidateCodeUtil.class);
    private Random random = new Random();

    /**
     * 获得字体
     */
    private Font getFont() {
        return new Font("Wide Latin", Font.PLAIN, 18);
    }

    /**
     * 获得颜色(粉色为主)
     */
    private Color getRandColor() {
        ArrayList<Color> colors = new ArrayList<Color>();
        colors.add(new Color(241, 158, 194));
        colors.add(new Color(255, 94, 226));
        colors.add(new Color(255, 156, 177));
        colors.add(new Color(245, 152, 217));
        colors.add(new Color(255, 85, 81));
        colors.add(new Color(245, 152, 251));
        int num = random.nextInt(6);
        return colors.get(num);
    }

    /**
     * 生成随机图片
     */
    public void getRandcode(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, width, height);//图片大小
        g.setFont(new Font("Wide Latin", Font.PLAIN, 18));//字体大小
        g.setColor(getRandColor());//字体颜色

        // 添加噪点
        float yawpRate = 0.01f;// 噪声率
        int area = (int) (yawpRate * width * height);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            image.setRGB(x, y, random.nextInt(255));
        }

        // 绘制随机字符
        String randomString = "";
        for (int i = 1; i <= stringNum; i++) {
            randomString = drowString(g, randomString, i);
        }
        logger.info(randomString);
        //将生成的随机字符串保存到session中
        session.removeAttribute(RANDOMCODEKEY);
        session.setAttribute(RANDOMCODEKEY, randomString);
        g.dispose();
        try {
            // 将内存中的图片通过流动形式输出到客户端
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (Exception e) {
            logger.error("将内存中的图片通过流动形式输出到客户端失败>>>>   ", e);
        }

    }

    /**
     * 绘制字符串
     */
    private String drowString(Graphics g, String randomString, int i) {
        g.setFont(getFont());
        g.setColor(getRandColor());
        String rand = String.valueOf(getRandomString(random.nextInt(randString
                .length())));
        randomString += rand;
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, 20 * (i - 1) + 3, 25);
        return randomString;
    }

    /**
     * 获取随机的字符
     */
    public String getRandomString(int num) {
        return String.valueOf(randString.charAt(num));
    }
}

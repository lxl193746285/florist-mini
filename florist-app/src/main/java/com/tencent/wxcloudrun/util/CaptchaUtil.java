package com.tencent.wxcloudrun.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Random;

/**
 * 图形验证码工具类
 */
public class CaptchaUtil {

    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;
    private static final int CODE_LENGTH = 4;
    private static final String CODE_CHARS = "0123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz";
    private static final Random RANDOM = new Random();

    /**
     * 生成验证码图片和文本
     */
    public static CaptchaResult generate() {
        // 创建图片
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 设置背景色
        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 生成验证码文本
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = RANDOM.nextInt(CODE_CHARS.length());
            char c = CODE_CHARS.charAt(index);
            code.append(c);

            // 绘制字符
            g.setColor(getRandomColor());
            g.setFont(new Font("Arial", Font.BOLD, 25 + RANDOM.nextInt(8)));
            int x = 20 + i * 25;
            int y = 25 + RANDOM.nextInt(10);
            g.drawString(String.valueOf(c), x, y);
        }

        // 绘制干扰线
        for (int i = 0; i < 8; i++) {
            g.setColor(getRandomColor());
            int x1 = RANDOM.nextInt(WIDTH);
            int y1 = RANDOM.nextInt(HEIGHT);
            int x2 = RANDOM.nextInt(WIDTH);
            int y2 = RANDOM.nextInt(HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }

        // 绘制干扰点
        for (int i = 0; i < 30; i++) {
            g.setColor(getRandomColor());
            int x = RANDOM.nextInt(WIDTH);
            int y = RANDOM.nextInt(HEIGHT);
            g.fillRect(x, y, 2, 2);
        }

        g.dispose();

        // 转换为Base64
        String imageBase64 = imageToBase64(image);

        return new CaptchaResult(code.toString(), imageBase64);
    }

    /**
     * 图片转Base64
     */
    private static String imageToBase64(BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] bytes = baos.toByteArray();
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取随机颜色
     */
    private static Color getRandomColor() {
        int r = RANDOM.nextInt(200);
        int g = RANDOM.nextInt(200);
        int b = RANDOM.nextInt(200);
        return new Color(r, g, b);
    }

    /**
     * 验证码结果
     */
    public static class CaptchaResult {
        private String code;
        private String imageBase64;

        public CaptchaResult(String code, String imageBase64) {
            this.code = code;
            this.imageBase64 = imageBase64;
        }

        public String getCode() {
            return code;
        }

        public String getImageBase64() {
            return imageBase64;
        }
    }
}

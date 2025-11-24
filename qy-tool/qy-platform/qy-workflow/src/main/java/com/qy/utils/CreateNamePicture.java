package com.qy.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据姓名创建图片
 */
public class CreateNamePicture {
    /**
     * @throws IOException
     * @throws
     **/
    public static void main(String[] args) throws Exception {
        /*String name = "成吉思汗";
        String fileName = "999";
       
        generateImg(name, "D://成吉思汗", fileName);*/
        String fileName = "3554454.jpg";
        zoomImage("https://www.sevwork.cn/avatar/382/avatar_big.jpg", "d://456", fileName, 100, 100);
    }

    /**
     * 绘制字体头像
     * 如果是英文名，只显示首字母大写
     * 如果是中文名，只显示最后两个字
     */
    public static void generateImg(String name, String outputPath, String outputName)
            throws IOException {
        String osName = System.getProperties().getProperty("os.name");
        File dirFile = new File(outputPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
            //dirFile.setReadable(true, false);
            if (osName.startsWith("Linux")) {
                Process dirProcess = Runtime.getRuntime().exec("chown -R www:www " + outputPath);
            }
        }
        int width = 100;
        int height = 100;
        int nameLen = name.length();
        String nameWritten;
        //如果用户输入的姓名少于等于2个字符，不用截取
        if (nameLen <= 2) {
            nameWritten = name;
        } else {
            //如果用户输入的姓名大于等于3个字符，截取后面两位
            String first = name.substring(0, 1);
            if (isChinese(first)) {
                //截取倒数两位汉字
                nameWritten = name.substring(nameLen - 2);
            } else {
                //截取前面的两个英文字母
                nameWritten = name.substring(0, 2).toUpperCase();
            }
        }
        String filename = outputPath + File.separator + outputName + ".jpg";
        File file = new File(filename);
        //Font font = new Font("微软雅黑", Font.PLAIN, 30);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setBackground(getRandomColor());
        g2.clearRect(0, 0, width, height);
        g2.setPaint(Color.WHITE);
        Font font = null;
        //两个字及以上
        if (nameWritten.length() >= 2) {
            font = new Font("微软雅黑", Font.PLAIN, 30);
            g2.setFont(font);
            String firstWritten = nameWritten.substring(0, 1);
            String secondWritten = nameWritten.substring(1, 2);
            //两个中文 如 言曌
            if (isChinese(firstWritten) && isChinese(secondWritten)) {
                g2.drawString(nameWritten, 20, 60);
            }
            //首中次英 如 罗Q
            else if (isChinese(firstWritten) && !isChinese(secondWritten)) {
                g2.drawString(nameWritten, 27, 60);
                //首英,如 AB
            } else {
                nameWritten = nameWritten.substring(0, 1);
            }
        }
        //一个字
        if (nameWritten.length() == 1) {
            //中文
            if (isChinese(nameWritten)) {
                font = new Font("微软雅黑", Font.PLAIN, 50);
                g2.setFont(font);
                g2.drawString(nameWritten, 25, 70);
            }
            //英文
            else {
                font = new Font("微软雅黑", Font.PLAIN, 55);
                g2.setFont(font);
                g2.drawString(nameWritten.toUpperCase(), 33, 67);
            }
        }
        BufferedImage rounded = makeRoundedCorner(bi, 99);
        ImageIO.write(rounded, "png", file);
        if (osName.startsWith("Linux")) {
            Process fileProcess = Runtime.getRuntime().exec("chown -R www:www " + filename);
        }
        //file.setReadable(true, false);
    }

    /**
     * 判断字符串是否为中文
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        String regEx = "[\\u4e00-\\u9fa5]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (m.find())
            return true;
        else
            return false;
    }

    /**
     * 获得随机颜色
     *
     * @return
     */
    private static Color getRandomColor() {
        String[] beautifulColors =
                new String[]{"232,221,203", "205,179,128", "3,101,100", "3,54,73", "3,22,52",
                        "237,222,139", "251,178,23", "96,143,159", "1,77,103", "254,67,101", "252,157,154",
                        "249,205,173", "200,200,169", "131,175,155", "229,187,129", "161,23,21", "34,8,7",
                        "118,77,57", "17,63,61", "60,79,57", "95,92,51", "179,214,110", "248,147,29",
                        "227,160,93", "178,190,126", "114,111,238", "56,13,49", "89,61,67", "250,218,141",
                        "3,38,58", "179,168,150", "222,125,44", "20,68,106", "130,57,53", "137,190,178",
                        "201,186,131", "222,211,140", "222,156,83", "23,44,60", "39,72,98", "153,80,84",
                        "217,104,49", "230,179,61", "174,221,129", "107,194,53", "6,128,67", "38,157,128",
                        "178,200,187", "69,137,148", "117,121,71", "114,83,52", "87,105,60", "82,75,46",
                        "171,92,37", "100,107,48", "98,65,24", "54,37,17", "137,157,192", "250,227,113",
                        "29,131,8", "220,87,18", "29,191,151", "35,235,185", "213,26,33", "160,191,124",
                        "101,147,74", "64,116,52", "255,150,128", "255,94,72", "38,188,213", "167,220,224",
                        "1,165,175", "179,214,110", "248,147,29", "230,155,3", "209,73,78", "62,188,202",
                        "224,160,158", "161,47,47", "0,90,171", "107,194,53", "174,221,129", "6,128,67",
                        "38,157,128", "201,138,131", "220,162,151", "137,157,192", "175,215,237", "92,167,186",
                        "255,66,93", "147,224,255", "247,68,97", "185,227,217"};
        int len = beautifulColors.length;
        Random random = new Random();
        String[] color = beautifulColors[random.nextInt(len)].split(",");
        return new Color(Integer.parseInt(color[0]), Integer.parseInt(color[1]),
                Integer.parseInt(color[2]));
    }

    /**
     * 图片做圆角处理
     *
     * @param image
     * @param cornerRadius
     * @return
     */
    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return output;
    }

    /**
     * 截取已有的图片
     *
     * @param file
     * @param urlstr
     * @return
     */
    public static boolean naiveDownloadPicture(File file, String urlstr) {
        URL url = null;
        try {
            // 生成图片链接的url类
            url = new URL(urlstr);
            // 将url链接下的图片以字节流的形式存储到 DataInputStream类中
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            // 为file生成对应的文件输出流
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            // 定义字节数组大小
            byte[] buffer = new byte[1024];

            // 从所包含的输入流中读取[buffer.length()]的字节，并将它们存储到缓冲区数组buffer 中。
            // dataInputStream.read()会返回写入到buffer的实际长度,若已经读完 则返回-1
            while (dataInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);// 将buffer中的字节写入文件中区
            }
            dataInputStream.close();// 关闭输入流
            fileOutputStream.close();// 关闭输出流

            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /*
     * 图片缩放,w，h为缩放的目标宽度和高度
     * src为源文件目录，dest为缩放后保存目录
     */
    public static void zoomImage(String urlstr, String dest, String fileName, int w, int h) throws Exception {
        String osName = System.getProperties().getProperty("os.name");
        URL url = new URL(urlstr);
        double wr = 0, hr = 0;
        DataInputStream dataInputStream = new DataInputStream(url.openStream());
        File destDir = new File(dest);
        if (!destDir.exists()) {
            destDir.mkdirs();
            if (osName.startsWith("Linux")) {
                Process dirProcess = Runtime.getRuntime().exec("chown -R www:www " + dest);
            }
        }
        File destFile = new File(destDir + "//" + fileName);
        dest = destDir + "//" + fileName;
        BufferedImage bufImg = ImageIO.read(dataInputStream); //读取图片
        Image Itemp = bufImg.getScaledInstance(w, h, bufImg.SCALE_SMOOTH);//设置缩放目标图片模板

        wr = w * 1.0 / bufImg.getWidth();     //获取缩放比例
        hr = h * 1.0 / bufImg.getHeight();

        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
        Itemp = ato.filter(bufImg, null);
        try {
            ImageIO.write((BufferedImage) Itemp, dest.substring(dest.lastIndexOf(".") + 1), destFile); //写入缩减后的图片
            //destFile.setReadable(true, false);
            //destDir.setReadable(true, false);
            if (osName.startsWith("Linux")) {
                Process fileProcess = Runtime.getRuntime().exec("chown -R www:www " + destDir + "//" + fileName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}

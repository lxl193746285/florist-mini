package com.qy.system.app.util;



import cn.hutool.extra.qrcode.BufferedImageLuminanceSource;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

/**
 * 二维码工具类 Created by fuli.shen on 2017/3/31.
 */
public class QRCodeUtil {

    private static final String CHARSET = "utf-8";
    // 生成文件后缀
    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 260;
    // LOGO宽度
    private static final int WIDTH = 40;
    // LOGO高度
    private static final int HEIGHT = 40;
    //底图尺寸
    private static final int BASE_QRCODE_SIZE = 364;

    /**
     * 生成二维码的方法
     *
     * @param content      目标URL
     * @param imgPath      LOGO图片地址
     * @param needCompress 是否压缩LOGO
     * @return 二维码图片
     * @throws Exception
     */
    public static BufferedImage createImage(String content, String imgPath, boolean needCompress,String pressText,String pressTextTop) throws Exception {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 0); // 外边距
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();

        // 重新定义一个BufferedImage 网图片上添加rgb颜色
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // 添加黑白色
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片
        if(imgPath!=null){
            QRCodeUtil.insertImage(image, imgPath, needCompress);
        }

//        if(pressText!=null){
//            QRCodeUtil.pressText(pressText,image,0,Color.red,22);
//        }
//        if(pressTextTop != null){
//            QRCodeUtil.pressTextTop(pressTextTop,image,0,Color.red,22);
//        }
        return image;
    }

    /**
     * 插入LOGO
     *
     * @param source       二维码图片
     * @param imgPath      LOGO图片地址
     * @param needCompress 是否压缩
     * @throws Exception
     */
    private static void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws Exception {
        /*
         * File file = new File(imgPath); if (!file.exists()) { System.err.println("" +
         * imgPath + "   该文件不存在！"); return; }
         */
        //Image src = ImageIO.read(ResourceUtils.getFile(imgPath));
        Image src = null;
        try {
            src = ImageIO.read(ResourceRenderer.resourceLoader(imgPath));
        }catch (Exception e){
            src = ImageIO.read(ResourceRenderer.resourceLoader("https://xdzoss.qiyunapp.com/upload/other/1598499824/thumb/anonymity.jpg"));
        }
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 用Graphics 在 BufferedImage 上指定位置绘制
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content      内容
     * @param imgPath      LOGO地址
     * @param destPath     存放目录
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String imgPath, String destPath, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath, needCompress,null,null);
        mkdirs(destPath);
        String file = new Random().nextInt(99999999) + ".jpg";
        ImageIO.write(image, FORMAT_NAME, new File(destPath + "/" + file));
    }

    /**
     * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
     *
     * @param destPath 存放目录
     */
    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content  内容
     * @param imgPath  LOGO地址
     * @param destPath 存储地址
     * @throws Exception
     */
    public static void encode(String content, String imgPath, String destPath) throws Exception {
        QRCodeUtil.encode(content, imgPath, destPath, false);
    }

    /**
     * 生成二维码
     *
     * @param content      内容
     * @param destPath     存储地址
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String destPath, boolean needCompress) throws Exception {
        QRCodeUtil.encode(content, null, destPath, needCompress);
    }

    /**
     * 生成二维码
     *
     * @param content  内容
     * @param destPath 存储地址
     * @throws Exception
     */
    public static void encode(String content, String destPath) throws Exception {
        QRCodeUtil.encode(content, null, destPath, false);
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content      内容
     * @param imgPath      LOGO地址
     * @param output       输出流
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String imgPath, OutputStream output, boolean needCompress)
            throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath, needCompress,null,null);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    /**
     * 生成二维码
     *
     * @param content 内容
     * @param output  输出流
     * @throws Exception
     */
    public static void encode(String content, OutputStream output) throws Exception {
        QRCodeUtil.encode(content, null, output, false);
    }

    /**
     * 解析二维码
     *
     * @param file 二维码图片
     * @return
     * @throws Exception
     */
    public static String decode(File file) throws Exception {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable hints = new Hashtable();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }

    /**
     * 解析二维码
     *
     * @param path 二维码图片地址
     * @return 不是二维码的内容返回null,是二维码直接返回识别的结果
     * @throws Exception
     */
    public static String decode(String path) throws Exception {
        return QRCodeUtil.decode(new File(path));
    }

    /**
     * Java拼接多张图片
     *
     * @param imgs     图片地址集合
     * @param type     图片类型
     * @param dst_pic  //输出的文件：F:/test2.jpg
     * @param rowCount //一行几张
     * @return
     */
    public static boolean merge(String[] imgs, String type, String dst_pic, int rowCount) {
        // 获取需要拼接的图片长度
        int len = imgs.length;
        // 判断长度是否大于0
        if (len < 1) {
            return false;
        }
        File[] src = new File[len];
        BufferedImage[] images = new BufferedImage[len];
        int[][] ImageArrays = new int[len][];
        for (int i = 0; i < len; i++) {
            try {
                src[i] = new File(imgs[i]);
                images[i] = ImageIO.read(src[i]);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            int width = images[i].getWidth() + 2;
            int height = images[i].getHeight() + 2;
            // 从图片中读取RGB 像素
            ImageArrays[i] = new int[width * height];
            ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
        }

        int dst_height = images[0].getHeight();
        int dst_width = 0;
        // 合成图片像素
        for (int i = 0; i < images.length; i++) {
            if ((i + 1) % rowCount == 0 && i > 0 && (i + 1) != images.length) {
                dst_height += images[i].getHeight();

            }
        }
        dst_width = images.length > rowCount ? rowCount * images[0].getWidth() : images.length * images[0].getWidth();
        // 合成后的图片
        System.out.println("宽度:" + dst_width);
        System.out.println("高度:" + dst_height);
        if (dst_height < 1) {
            System.out.println("dst_height < 1");
            return false;
        }
        // 生成新图片
        try {
            int startX = 0;
            BufferedImage ImageNew = new BufferedImage(dst_width, dst_height, BufferedImage.TYPE_INT_RGB);
            dst_width = images[0].getWidth();
            int height_i = 0;
            for (int i = 0; i < images.length; i++) {
                ImageNew.setRGB(startX, height_i, dst_width, images[i].getHeight(), ImageArrays[i], 0, dst_width);
                // height_i += images[i].getHeight();
                if ((i + 1) % rowCount == 0 && i > 0) {
                    height_i += images[i].getHeight();
                    startX = 0;
                } else {
                    startX += images[i].getWidth();
                }
                // System.out.println(startX+" "+height_i);
            }

            File outFile = new File(dst_pic);
            ImageIO.write(ImageNew, type, outFile);// 写图片 ，输出到硬盘
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Java拼接多张图片
     *
     * @param imgs     图片地址集合
     * @param type     图片类型
     * @param rowCount //一行几张
     * @param outputStream  //输出的文件：F:/test2.jpg
     * @return
     */
    public static boolean merge(BufferedImage[] imgs, String type, int rowCount, OutputStream outputStream) {
        // 获取需要拼接的图片长度
        int len = imgs.length;
        // 判断长度是否大于0
        if (len < 1) {
            return false;
        }
        BufferedImage[] images = imgs;
        int[][] ImageArrays = new int[len][];
        for (int i = 0; i < len; i++) {
            int width = images[i].getWidth();
            int height = images[i].getHeight();
            // 从图片中读取RGB 像素
            ImageArrays[i] = new int[width * height];
            ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
        }

        int dst_height = images[0].getHeight();
        int dst_width = 0;
        // 合成图片像素
        for (int i = 0; i < images.length; i++) {
            if ((i + 1) % rowCount == 0 && i > 0 && (i + 1) != images.length) {
                dst_height += images[i].getHeight();

            }
        }
        dst_width = images.length > rowCount ? rowCount * images[0].getWidth() : images.length * images[0].getWidth();
        // 合成后的图片
        System.out.println("宽度:" + dst_width);
        System.out.println("高度:" + dst_height);
        if (dst_height < 1) {
            System.out.println("dst_height < 1");
            return false;
        }
        // 生成新图片
        try {
            int startX = 0;
            BufferedImage ImageNew = new BufferedImage(dst_width, dst_height, BufferedImage.TYPE_INT_RGB);
            dst_width = images[0].getWidth();
            int height_i = 0;
            for (int i = 0; i < images.length; i++) {
                ImageNew.setRGB(startX, height_i, dst_width, images[i].getHeight(), ImageArrays[i], 0, dst_width);
                // height_i += images[i].getHeight();
                if ((i + 1) % rowCount == 0 && i > 0) {
                    height_i += images[i].getHeight();
                    startX = 0;
                } else {
                    startX += images[i].getWidth();
                }
            }

            ImageIO.write(ImageNew, type, outputStream);// 写图片 ，输出到输出流
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * Java拼接多张图片,不足的用空格补齐
     *
     * @param imgss     图片地址集合
     * @param type     图片类型
     * @param pageSize 一张有多少张
     * @param rowCount //一行几张
     * @return
     */
    public static BufferedImage merge(List<BufferedImage> imgss, String type, int rowCount,int pageSize, OutputStream outputStream) {
        if(imgss.size() < pageSize) {
            // 如果要生成的图片不足pageSize 则用空白图片补齐
            for(int i = 0;i < pageSize - imgss.size();i++) {
                Color color = new Color(255, 255, 255);
                imgss.add(QRCodeUtil.forceFill(color.getRGB()));
            }
        }
        // 开始接图片
        BufferedImage[] imgs = new BufferedImage[imgss.size()];
        // 获取需要拼接的图片长度
        int len = imgs.length;
        // 判断长度是否大于0
        if (len < 1) {
            return null;
        }
        BufferedImage[] images = imgs;
        int[][] ImageArrays = new int[len][];
        for (int i = 0; i < len; i++) {
            int width = images[i].getWidth();
            int height = images[i].getHeight();
            // 从图片中读取RGB 像素
            ImageArrays[i] = new int[width * height];
            ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
        }

        int dst_height = images[0].getHeight();
        int dst_width = 0;
        // 合成图片像素
        for (int i = 0; i < images.length; i++) {
            if ((i + 1) % rowCount == 0 && i > 0 && (i + 1) != images.length) {
                dst_height += images[i].getHeight();
            }
        }
        dst_width = images.length > rowCount ? rowCount * images[0].getWidth() : images.length * images[0].getWidth();
        // 合成后的图片
        System.out.println("宽度:" + dst_width);
        System.out.println("高度:" + dst_height);
        if (dst_height < 1) {
            System.out.println("dst_height < 1");
        }
        // 生成新图片
        try {
            int startX = 0;
            BufferedImage ImageNew = new BufferedImage(dst_width, dst_height, BufferedImage.TYPE_INT_RGB);
            dst_width = images[0].getWidth();
            int height_i = 0;
            for (int i = 0; i < images.length; i++) {
                ImageNew.setRGB(startX, height_i, dst_width, images[i].getHeight(), ImageArrays[i], 0, dst_width);
                // height_i += images[i].getHeight();
                if ((i + 1) % rowCount == 0 && i > 0) {
                    height_i += images[i].getHeight();
                    startX = 0;
                } else {
                    startX += images[i].getWidth();
                }
            }
            return ImageNew;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Java拼接多张图片
     *
     * @param imgs     图片地址集合
     * @param type     图片类型
     * @param dst_pic  //输出的文件：F:/test2.jpg
     * @param rowCount //一行几张
     * @return
     */
    public static boolean merge(BufferedImage[] imgs, String type, String dst_pic, int rowCount) {
        // 获取需要拼接的图片长度
        int len = imgs.length;
        // 判断长度是否大于0
        if (len < 1) {
            return false;
        }
        BufferedImage[] images = imgs;
        int[][] ImageArrays = new int[len][];
        for (int i = 0; i < len; i++) {
            int width = images[i].getWidth();
            int height = images[i].getHeight();
            // 从图片中读取RGB 像素
            ImageArrays[i] = new int[width * height];
            ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
        }

        int dst_height = images[0].getHeight();
        int dst_width = 0;
        // 合成图片像素
        for (int i = 0; i < images.length; i++) {
            if ((i + 1) % rowCount == 0 && i > 0 && (i + 1) != images.length) {
                dst_height += images[i].getHeight();

            }
        }
        dst_width = images.length > rowCount ? rowCount * images[0].getWidth() : images.length * images[0].getWidth();
        // 合成后的图片
        System.out.println("宽度:" + dst_width);
        System.out.println("高度:" + dst_height);
        if (dst_height < 1) {
            System.out.println("dst_height < 1");
            return false;
        }
        // 生成新图片
        try {
            int startX = 0;
            BufferedImage ImageNew = new BufferedImage(dst_width, dst_height, BufferedImage.TYPE_INT_RGB);
            dst_width = images[0].getWidth();
            int height_i = 0;
            for (int i = 0; i < images.length; i++) {
                ImageNew.setRGB(startX, height_i, dst_width, images[i].getHeight(), ImageArrays[i], 0, dst_width);
                // height_i += images[i].getHeight();
                if ((i + 1) % rowCount == 0 && i > 0) {
                    height_i += images[i].getHeight();
                    startX = 0;
                } else {
                    startX += images[i].getWidth();
                }

                // System.out.println(startX+" "+height_i);
            }

            File outFile = new File(dst_pic);
            ImageIO.write(ImageNew, type, outFile);// 写图片 ，输出到硬盘
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Java拼接多张图片
     *
     * @param imgs
     *            图片地址集合
     * @param type
     *            图片类型
     * @param rowCount
     *            //一行几张
     * @return
     */
    public static InputStream merge(BufferedImage[] imgs, String type, int rowCount) {
        // 获取需要拼接的图片长度
        int len = imgs.length;
        // 判断长度是否大于0
        if (len < 1) {
            System.out.println("要拼接的长度为零!!!!!!!!!!!!!!!!");
            return null;
        }
        BufferedImage[] images = imgs;
        int[][] ImageArrays = new int[len][];
        for (int i = 0; i < len; i++) {
            int width = images[i].getWidth();
            int height = images[i].getHeight();
            // 从图片中读取RGB 像素
            ImageArrays[i] = new int[width * height];
            ImageArrays[i] = images[i].getRGB(0, 0, width, height,
                    ImageArrays[i], 0, width);
        }

        int dst_height = images[0].getHeight();
        int dst_width = 0;
        // 合成图片像素
        for (int i = 0; i < images.length; i++) {
            if ((i + 1) % rowCount == 0 && i > 0 && (i + 1) != images.length) {
                dst_height += images[i].getHeight();

            }
        }
        dst_width = images.length > rowCount ? rowCount * images[0].getWidth()
                : images.length * images[0].getWidth();
        // 合成后的图片
        System.out.println("宽度:" + dst_width);
        System.out.println("高度:" + dst_height);
        if (dst_height < 1) {
            System.out.println("dst_height < 1");
            return null;
        }
        // 生成新图片
        try {
            int startX = 0;
            BufferedImage ImageNew = new BufferedImage(dst_width, dst_height,
                    BufferedImage.TYPE_INT_RGB);
            dst_width = images[0].getWidth();
            int height_i = 0;
            for (int i = 0; i < images.length; i++) {
                ImageNew.setRGB(startX, height_i, dst_width,
                        images[i].getHeight(), ImageArrays[i], 0, dst_width);
                // height_i += images[i].getHeight();
                if ((i + 1) % rowCount == 0 && i > 0) {
                    height_i += images[i].getHeight();
                    startX = 0;
                } else {
                    startX += images[i].getWidth();
                }

                // System.out.println(startX+" "+height_i);
            }

            // File outFile = new File(dst_pic);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(ImageNew, "jpg", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            // ImageIO.write(ImageNew, type, outputStream);// 写图片 ，输出到zip输出流
            return is;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 给二维码下方附加说明文字
     * @param pressText 文字
     * @param image     需要添加文字的图片
     * @为图片添加文字
     */
    public static void pressText(String pressText, BufferedImage image, int fontStyle, Color color, int fontSize) {

        //计算文字开始的位置
        //x开始的位置：（二维码-字体大小*字的个数）/2
        //int startX = (QRCODE_SIZE - (fontSize * pressText.length()))/3 - 28;
        int ci = (fontSize) * pressText.length();
        int startX = (BASE_QRCODE_SIZE-ci)/11*6+fontSize;
//        //y开始的位置：二维码高度 - 文字大小
//        int startY = QRCODE_SIZE - fontSize;
        //y开始的位置：二维码高度 - 文字大小
        int startY = BASE_QRCODE_SIZE - fontSize;

        System.out.println("startX: " + startX);
        System.out.println("startY: " + startY);
        System.out.println("fontSize: " + fontSize);
        System.out.println("pressText.length(): " + pressText.length());

        try {
            // 创建一个 Graphics
            Graphics g = image.createGraphics();
            // 设置 Graphics 的绘制颜色
            g.setColor(color);
            // 设置字体
            g.setFont(new Font("黑体", Font.BOLD, fontSize));
            // 开始绘制
            g.drawString(pressText, startX, startY);
            // 保存
            g.dispose();
            System.out.println("添加的pressText 为：【"+pressText+"】");
            System.out.println("image press success");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    /**
     * 给二维码上方方附加说明文字
     * @param pressText 文字
     * @param image     需要添加文字的图片
     * @为图片添加文字
     */
    public static void pressTextTop(String pressText, BufferedImage image, int fontStyle, Color color, int fontSize) {

        //计算文字开始的位置
        //x开始的位置：（二维码-字体大小*字的个数）/2
        //int startX = (QRCODE_SIZE - (fontSize * pressText.length()))/3 - 28;
        int ci = (fontSize+1) * pressText.length();
        int startX = (BASE_QRCODE_SIZE-ci)/2;
//        //y开始的位置：二维码高度 - 文字大小
//        int startY = QRCODE_SIZE - fontSize;
        //y开始的位置：二维码高度 - 文字大小
        int startY = fontSize*3/2;

        System.out.println("startX: " + startX);
        System.out.println("startY: " + startY);
        System.out.println("fontSize: " + fontSize);
        System.out.println("pressText.length(): " + pressText.length());

        try {
            // 创建一个 Graphics
            Graphics g = image.createGraphics();
            // 设置 Graphics 的绘制颜色
            g.setColor(color);
            // 设置字体
            g.setFont(new Font("黑体", Font.BOLD, fontSize));
            // 开始绘制
            g.drawString(pressText, startX, startY);
            // 保存
            g.dispose();
            System.out.println("添加的pressText 为：【"+pressText+"】");
            System.out.println("image press success");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    /**
     * 生成一张纯色图片
     * @param rgb
     * @return
     */
    public static BufferedImage forceFill( int rgb) {
        BufferedImage img = new BufferedImage(QRCODE_SIZE, QRCODE_SIZE, BufferedImage.TYPE_3BYTE_BGR);
        for(int x = 0; x < img.getWidth(); x++) {
            for(int y = 0; y < img.getHeight(); y++) {
                //img.setRGB(x, y, rgb);
                img.setRGB(x, y, rgb);
            }
        }
        return img;
    }
    /**
     * 生成一张纯色图片
     * @param rgb
     * @return
     */
    public static BufferedImage getQRCodeBaseImage( int rgb)  throws IOException {
        BufferedImage img = ImageIO.read(new File("D:\\qy-mall\\QRCode.jpg"));;
        return img;
    }
//

    /***
     * 在一张背景图上添加二维码
     */
    public static BufferedImage addWater(String content, String imgPath, boolean needCompress,String pressText,String pressTextTop) throws Exception {
        // 读取原图片信息
        //文件转化为图片
        Image srcImg = ImageIO.read(ResourceRenderer.resourceLoader("http://xdzoss.qiyunapp.com/upload/other/1608620166/QRCode.jpg"));
        //获取图片的宽
        int srcImgWidth = srcImg.getWidth(null);
        //获取图片的高
        int srcImgHeight = srcImg.getHeight(null);
        // 加水印
        BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufImg.createGraphics();
        g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
        //使用工具类生成二维码
        Image image = createImage(content,imgPath,needCompress,pressText,pressTextTop);
        //将小图片绘到大图片上,500,300 .表示你的小图片在大图片上的位置。
        int startX = (BASE_QRCODE_SIZE-QRCODE_SIZE)/2;
        int startY = (BASE_QRCODE_SIZE-QRCODE_SIZE)/2;
        g.drawImage(image, startX, startY, null);
        //设置颜色。
        g.setColor(Color.WHITE);
                if(pressText!=null){
            QRCodeUtil.pressText(pressText,bufImg,0,Color.red,20);
        }
        if(pressTextTop != null){
            QRCodeUtil.pressTextTop(pressTextTop,bufImg,0,Color.red,18);
        }
        g.dispose();
        return bufImg;
    }

    public static InputStream resourceLoader(String fileFullPath) throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        return resourceLoader.getResource(fileFullPath).getInputStream();
    }


    public static void main(String[] args) throws Exception {

        // 生成二维码
        String text = "https://www.xdzvip.cn/wechat/store/#/storeApply?pageType=apply&appid=wxd4aa991f1017d115&promoterCode=123321&fromPagePath=register";
        // String imagePath = System.getProperty("user.dir") + "/data/1.jpg";
        String destPath = "C:\\Users\\admin\\";
        List<BufferedImage> bufferedImages = new ArrayList<BufferedImage>();
        for (int i = 0; i < 12; i++) {
            BufferedImage bufferedImage = QRCodeUtil.addWater(text, "https://xdzoss.qiyunapp.com/upload/other/1598499824/thumb/anonymity.jpg",
                    true,"推荐码：321123","许颂健推荐您成为县区合伙人");
            bufferedImages.add(bufferedImage);
        }

        // QRCodeUtil.encode(content, output);
        // 验证图片是否含有二维码
        /*
         * String destPath1 = "C:\\Users\\admin\\Desktop\\3.jpg"; try { String result =
         * decode(destPath1); System.out.println(result); }catch (Exception e){
         * e.printStackTrace(); System.out.println(destPath1+"不是二维码"); }
         */
        // 输入图片地址
        String[] imgs = { "C:\\Users\\admin\\Desktop\\2.jpg", "C:\\Users\\admin\\Desktop\\1.jpg",
                "C:\\Users\\admin\\Desktop\\2.jpg", "C:\\Users\\admin\\Desktop\\1.jpg",
                "C:\\Users\\admin\\Desktop\\2.jpg", "C:\\Users\\admin\\Desktop\\1.jpg",
                "C:\\Users\\admin\\Desktop\\2.jpg", "C:\\Users\\admin\\Desktop\\1.jpg",
                "C:\\Users\\admin\\Desktop\\2.jpg", "C:\\Users\\admin\\Desktop\\1.jpg",
                "C:\\Users\\admin\\Desktop\\2.jpg", "C:\\Users\\admin\\Desktop\\1.jpg",
                "C:\\Users\\admin\\Desktop\\2.jpg", "C:\\Users\\admin\\Desktop\\1.jpg",
                "C:\\Users\\admin\\Desktop\\2.jpg" };
        BufferedImage[] bfs = new BufferedImage[bufferedImages.size()];
        // 调用方法生成图片
        merge(bufferedImages.toArray(bfs), "jpg", "C:\\Users\\admin\\test.jpg", 4);
    }

    public static BufferedImage createImage(String content) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }
}



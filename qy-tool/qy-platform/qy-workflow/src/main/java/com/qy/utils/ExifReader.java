package com.qy.utils;//package com.qy.utils;
//
//import com.drew.imaging.ImageMetadataReader;
//import com.drew.imaging.ImageProcessingException;
//import com.drew.metadata.Metadata;
//import com.drew.metadata.exif.ExifSubIFDDirectory;
//import com.drew.metadata.exif.GpsDirectory;
//import org.apache.commons.codec.binary.Hex;
//
//import java.io.File;
//import java.io.IOException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//public class ExifReader {
//
//    /**
//     * 是否有exif信息
//     * @param file
//     * @return
//     * @throws ImageProcessingException
//     * @throws IOException
//     */
//    public static boolean haveExif(File file) throws ImageProcessingException, IOException {
//        Metadata metadata = ImageMetadataReader.readMetadata(file);
//        ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
//        if (directory == null) {
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 获取二维码水印图片
//     * @param file 源文件
//     * @param codeInfo 二维码信息
//     * @param targetFilePath 生成的新文件
//     * @return
//     */
////    public static File getImageQrCode(File file,String codeInfo,String targetFilePath) {
////        File qrcodeFile = QRCode.from(codeInfo).file();
////        File targetFile = new File(targetFilePath);
////        EasyImageWaterMarkUtils.imageWatermark(qrcodeFile,file,targetFile);
////        return targetFile;
////    }
//
//    /**
//     * exif加密信息
//     * @param file
//     * @return
//     * @throws ImageProcessingException
//     * @throws IOException
//     */
//    public static String encryptExif(File file) throws ImageProcessingException, IOException {
//        Metadata metadata = ImageMetadataReader.readMetadata(file);
//        GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
//        if (gpsDirectory == null) {
//            return null;
//        }
//        //经度
//        String longitude = gpsDirectory.getString(GpsDirectory.TAG_LONGITUDE);
//        //纬度
//        String latitude = gpsDirectory.getString(GpsDirectory.TAG_LATITUDE);
//        //图片唯一值
//        String uniqueId = Md5Utils.getMD5(file);
//
//        //待加密信息（图片唯一值 + 经度 + 纬度 + 文件大小）
//        String codeInfo = uniqueId + longitude + latitude + file.length();
//
//        /*for (Directory Qdirectory : metadata.getDirectories()) {
//            for (Tag tag : Qdirectory.getTags()) {
//                System.out.format("[%s] - %s = %s",
//                        Qdirectory.getName(), tag.getTagName(), tag.getDescription());
//            }
//            if (Qdirectory.hasErrors()) {
//                for (String error : Qdirectory.getErrors()) {
//                    System.err.format("ERROR: %s", error);
//                }
//            }
//        }*/
//
//
//        return encode(codeInfo);
//    }
//
//
//    /**
//     * MD5加密
//     * @param src
//     * @return
//     */
//    private static String encode(String src) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            byte[] encodeBytes = md.digest(src.getBytes());
//
//            return Hex.encodeHexString(encodeBytes);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//}

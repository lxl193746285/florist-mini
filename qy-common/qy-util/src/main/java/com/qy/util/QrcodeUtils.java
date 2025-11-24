package com.qy.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 二维码工具类库
 */
public class QrcodeUtils {

	/**
	 * 创建二维码
	 *
	 * @param opt
	 * @param content
	 * @param size
	 * @throws Exception
	 */
	public static void create(OutputStream opt, String content, Integer size) throws Exception
	{
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		hints.put(EncodeHintType.MARGIN, 0);

		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, size, size, hints);
		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", opt);
	}

	/**
	 * 创建二维码
	 *
	 * @param opt
	 * @param content
	 * @throws Exception
	 */
	public static void create(OutputStream opt, String content) throws Exception
	{
		create(opt, content, 280);
	}


	/**
	 * 生成二维码
	 *
	 * @param response
	 * @param content
	 * @throws Exception
	 */
	public static void qrcode(HttpServletResponse response, String content) throws Exception {
		String type = "png";
		fileHead(response, type);
		OutputStream os = response.getOutputStream();
		create(os, content);
	}

	/**
	 * 文件头参数配置
	 *
	 * @param response
	 * @param type
	 */
	public static void fileHead(HttpServletResponse response, String type) {
		String fileType;

		switch (type.toLowerCase()) {
			case "pdf":
				fileType = "application/pdf";
				break;
			case "exe":
				fileType = "application/octet-stream";
				break;
			case "zip":
				fileType = "application/zip";
				break;
			case "doc":
				fileType = "application/msword";
				break;
			case "xls":
			case "xlsx":
				fileType = "application/vnd.ms-excel";
				break;
			case "ppt":
				fileType = "application/vnd.ms-powerpoint";
				break;
			case "gif":
				fileType = "image/gif";
				break;
			case "png":
				fileType = "image/png";
				break;
			case "jpg":
				fileType = "image/jp";
				break;
			default:
				fileType = "application/force-download";
				break;
		}

		response.setHeader("Content-Typ", fileType);
	}

	public static void updateTXT(String filePath, String outputPath){
        try {

			File file = new File(filePath);
			Scanner scanner = new Scanner(file);
			StringBuilder update = new StringBuilder();
			StringBuilder time = new StringBuilder();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line != null && !line.isEmpty()){
					if (line.contains("ADD COLUMN")){
						continue;
					}
					if (line.contains(" AFTER ")){
						line = line.substring(0, line.indexOf(" AFTER "));
						line = line + ";";
					}
					if (line.contains("_time`")){
						time.append(line).append("\n\n");
					}else {
						update.append(line).append("\n\n");
					}
				}
			}
			scanner.close();

			// 步骤1：创建文件对象
			File updateSql = new File("C://Users/Administrator/Desktop/update-no-time.sql");
			FileOutputStream updateSqlFile = new FileOutputStream(updateSql);
			updateSqlFile.write(update.toString().getBytes());
			updateSqlFile.close();

			// 步骤1：创建文件对象
			File timeSql = new File("C://Users/Administrator/Desktop/time.sql");
			FileOutputStream timeSqlFile = new FileOutputStream(timeSql);
			timeSqlFile.write(time.toString().getBytes());
			timeSqlFile.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

	public static void main(String[] args) {
		updateTXT("C://Users/Administrator/Desktop/sql/update-new.sql", "C://Users/Administrator/Desktop/sql/update-new.sql");
	}

}

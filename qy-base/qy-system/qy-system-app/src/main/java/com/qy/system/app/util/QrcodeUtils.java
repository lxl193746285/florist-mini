package com.qy.system.app.util;

import java.io.OutputStream;

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
//		QRCode qrcode = QRCode.from(content)
//				.withErrorCorrection(ErrorCorrectionLevel.L)
//				.withSize(size, size)
//				.to(ImageType.PNG)
//				//.withHint(EncodeHintType.QR_VERSION, 4) //版本 4-40
//				.withHint(EncodeHintType.MARGIN,0) //边框
//				;
//		qrcode.writeTo(opt);
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

}

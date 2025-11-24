package com.qy.attachment.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 附件设置
 */
@Component
public class AttachmentConfig {
    /**
     * 附件存储方式
	 */
	@Value("${qy.attachment.storage-mode}")
	public int storageMode;
	/**
     * 上传最大文件大小
	 */
	@Value("${qy.attachment.upload-max-size}")
	public int uploadMaxSize;
    /**
     * 本地上传文件路径
     */
	@Value("${qy.attachment.local.upload-path}")
	public String localUploadPath;
	/**
	 * 本地文件访问域名
	 */
	@Value("${qy.attachment.local.access-domain}")
	public String localAccessDomain;
	/**
     * 阿里云oss end point
	 */
	@Value("${qy.attachment.aliyun-oss.endpoint}")
	public String aliyunOssEndpoint;
	/**
	 * 阿里云oss access key id
	 */
	@Value("${qy.attachment.aliyun-oss.access-key-id}")
	public String aliyunOssAccessKeyId;
	/**
	 * 阿里云oss access key secret
	 */
	@Value("${qy.attachment.aliyun-oss.access-key-secret}")
	public String aliyunOssAccessKeySecret;
	/**
	 * 阿里云oss access bucket name
	 */
	@Value("${qy.attachment.aliyun-oss.bucket-name}")
	public String aliyunOssBucketName;
	/**
	 * 阿里云oss access access domain
	 */
	@Value("${qy.attachment.aliyun-oss.access-domain}")
	public String aliyunOssAccessDomain;
	/**
	 * 阿里云oss 上传文件回调url
	 */
	@Value("${qy.attachment.aliyun-oss.callback-url}")
	public String aliyunOssCallbackUrl;
	/**
	 * 阿里云oss 上传文件policy失效时间(秒)
	 */
	@Value("${qy.attachment.aliyun-oss.policy-expire-time}")
	public long aliyunOssPolicyExpireTime;
	/**
	 * 阿里云oss 上传文件绑定域名
	 */
	@Value("${qy.attachment.aliyun-oss.domain-name}")
	public String aliyunOssDomainName;
}

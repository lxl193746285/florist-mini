package com.qy.attachment.app.application.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.*;
import com.qy.attachment.app.application.dto.AliyunOssPolicyDTO;
import com.qy.attachment.app.application.dto.AttachmentBasicDTO;
import com.qy.attachment.app.application.dto.AttachmentThumbBasicDTO;
import com.qy.attachment.app.application.dto.AttachmentThumbUrlRelationDTO;
import com.qy.attachment.app.application.service.AliyunOssService;
import com.qy.attachment.app.application.service.AttachmentQueryService;
import com.qy.attachment.app.application.service.AttachmentThumbQueryService;
import com.qy.attachment.app.application.service.ThumbConfigQueryService;
import com.qy.attachment.app.config.AttachmentConfig;
import com.qy.attachment.app.domain.entity.FileAttachment;
import com.qy.attachment.app.domain.entity.ImageAttachment;
import com.qy.attachment.app.domain.entity.ImageThumbAttachment;
import com.qy.attachment.app.domain.enums.StorageMode;
import com.qy.attachment.app.domain.valueobject.AttachmentId;
import com.qy.attachment.app.domain.valueobject.ThumbAttachmentId;
import com.qy.attachment.app.domain.valueobject.User;
import com.qy.attachment.app.infrastructure.persistence.AttachmentDomainRepository;
import com.qy.attachment.app.infrastructure.persistence.AttachmentThumbDataRepository;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentDO;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentThumbConfigDO;
import com.qy.identity.api.client.UserClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 阿里云oss服务实现
 *
 * @author legendjw
 */
@Service
public class AliyunOssServiceImpl implements AliyunOssService {
    private static final Logger logger = LoggerFactory.getLogger(AliyunOssServiceImpl.class);
    private AttachmentConfig attachmentConfig;
    private AttachmentQueryService attachmentQueryService;
    private AttachmentDomainRepository attachmentDomainRepository;
    private ThumbConfigQueryService thumbConfigQueryService;
    private AttachmentThumbDataRepository attachmentThumbDataRepository;
    private UserClient userClient;
    private final RestTemplate restTemplate;
    private AttachmentThumbQueryService attachmentThumbQueryService;

    public AliyunOssServiceImpl(AttachmentConfig attachmentConfig,
                                AttachmentQueryService attachmentQueryService,
                                AttachmentDomainRepository attachmentDomainRepository,
                                ThumbConfigQueryService thumbConfigQueryService,
                                AttachmentThumbDataRepository attachmentThumbDataRepository,
                                UserClient userClient,
                                RestTemplate restTemplate,
                                AttachmentThumbQueryService attachmentThumbQueryService) {
        this.attachmentConfig = attachmentConfig;
        this.attachmentQueryService = attachmentQueryService;
        this.attachmentDomainRepository = attachmentDomainRepository;
        this.thumbConfigQueryService = thumbConfigQueryService;
        this.attachmentThumbDataRepository = attachmentThumbDataRepository;
        this.userClient = userClient;
        this.restTemplate = restTemplate;
        this.attachmentThumbQueryService = attachmentThumbQueryService;
    }

    @Override
    public AliyunOssPolicyDTO getAliyunOssPolicy() {
        String accessId = attachmentConfig.aliyunOssAccessKeyId; // 请填写您的AccessKeyId。
        String accessKey = attachmentConfig.aliyunOssAccessKeySecret; // 请填写您的AccessKeySecret。
        String endpoint = attachmentConfig.aliyunOssEndpoint; // 请填写您的 endpoint。
        String bucket = attachmentConfig.aliyunOssBucketName; // 请填写您的 bucketname 。
        String host = "https://" + bucket + "." + endpoint; // host的格式为 bucketname.endpoint
        // callbackUrl为 上传回调服务器的URL，请将下面的IP和Port配置为您自己的真实信息。
        String callbackUrl = attachmentConfig.aliyunOssCallbackUrl;
        String dir = ""; // 用户上传文件时指定的前缀。

        OSSClient client = new OSSClient(endpoint, accessId, accessKey);
        try {
            long expireTime = attachmentConfig.aliyunOssPolicyExpireTime;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);

            AliyunOssPolicyDTO aliyunOssPolicyDTO = new AliyunOssPolicyDTO();
            aliyunOssPolicyDTO.setAccessid(accessId);
            aliyunOssPolicyDTO.setPolicy(encodedPolicy);
            aliyunOssPolicyDTO.setSignature(postSignature);
            aliyunOssPolicyDTO.setDir(dir);
            aliyunOssPolicyDTO.setHost(host);
            aliyunOssPolicyDTO.setExpire(String.valueOf(expireEndTime / 1000));

            JSONObject jasonCallback = new JSONObject();
            jasonCallback.put("callbackUrl", callbackUrl);
            jasonCallback.put("callbackBody",
                    "{\"object\":${object},\"filename\":${x:filename},\"mimeType\":${mimeType},\"size\":${size},\"height\":\"${imageInfo.height}\",\"width\":\"${imageInfo.width}\",\"moduleId\":${x:moduleId},\"userId\":${x:userId},\"uid\":${x:uid}}");
            jasonCallback.put("callbackBodyType", "application/json");
            String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());
            aliyunOssPolicyDTO.setCallback(base64CallbackBody);

            return aliyunOssPolicyDTO;

        } catch (Exception e) {
            String errorMessage = "获取阿里云请求上传Policy参数失败";
            logger.error(errorMessage, e);
            throw new RuntimeException(errorMessage);
        }
    }

    @Override
    public void uploadCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ossCallbackBody = getPostBody(request);
        logger.info("阿里云回调：" + ossCallbackBody);
        boolean ret = verifyOSSCallbackRequest(request, ossCallbackBody);
        ObjectMapper objectMapper = getObjectMapper();
        Map fileMap = objectMapper.readValue(ossCallbackBody, Map.class);
        if (ret) {
//            String fileUrl = String.format("%s/%s", attachmentConfig.aliyunOssAccessDomain, fileMap.get("object").toString());
            String fileUrl = String.format("%s/%s", attachmentConfig.aliyunOssDomainName, fileMap.get("object").toString());
            // URL url = new URL(fileUrl);
            // HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Long userId = fileMap.get("userId") != null ? (Long) fileMap.get("userId") : 0L;
            String userName = fileMap.get("userName") != null ? (String) fileMap.get("userName") : "";
            String uid = fileMap.get("uid") != null ? (String) fileMap.get("uid") : "";
            String moduleId = fileMap.get("moduleId") != null ? (String) fileMap.get("moduleId") : "";

            FileAttachment attachment = FileAttachment.builder()
                    .storageMode(StorageMode.ALIYUN_OSS)
                    .name(fileMap.get("filename") != null ? fileMap.get("filename").toString() : "")
                    .path(fileMap.get("object").toString())
                    .url(fileUrl)
                    .mimeType(fileMap.get("mimeType").toString())
                    .size(Integer.valueOf(fileMap.get("size").toString()))
                    // .md5(DigestUtils.md5Hex(conn.getInputStream()))
                    .creator(userId != null ? new User(userId, userName) : null)
                    .build();

            if (attachment.isImage()) {
                int width = Integer.valueOf(fileMap.get("width").toString());
                int height = Integer.valueOf(fileMap.get("height").toString());

                ImageAttachment imageAttachment = ImageAttachment.builder()
                        .organizationId(attachment.getOrganizationId())
                        .storageMode(attachment.getStorageMode())
                        .name(attachment.getName())
                        .path(attachment.getPath())
                        .url(attachment.getUrl())
                        .mimeType(attachment.getMimeType())
                        .size(attachment.getSize())
                        // .md5(attachment.getMd5())
                        .width(width)
                        .height(height)
                        .creator(attachment.getCreator())
                        .build();
                AttachmentId attachmentId = attachmentDomainRepository.saveImageAttachment(imageAttachment);
                imageAttachment.setId(attachmentId);

                //缩略图片
                thumbImage(moduleId, imageAttachment);

                AttachmentBasicDTO attachmentBasicDTO = attachmentQueryService.getBasicAttachmentById(attachmentId);
                attachmentBasicDTO.setUid(uid);
                logger.info("阿里云回调返回：" + objectMapper.writeValueAsString(attachmentBasicDTO));
                response(request, response, objectMapper.writeValueAsString(attachmentBasicDTO), HttpServletResponse.SC_OK);
            } else {
                AttachmentId attachmentId = attachmentDomainRepository.saveFileAttachment(attachment);
                AttachmentBasicDTO attachmentBasicDTO = attachmentQueryService.getBasicAttachmentById(attachmentId);
                attachmentBasicDTO.setUid(uid);
                logger.info("阿里云回调返回：" + objectMapper.writeValueAsString(attachmentBasicDTO));
                response(request, response, objectMapper.writeValueAsString(attachmentBasicDTO), HttpServletResponse.SC_OK);
            }
        } else {
            response(request, response, "{\"Status\":\"verify not ok\"}", HttpServletResponse.SC_BAD_REQUEST);
        }
    }


    @Override
    public String uploadOss(MultipartFile file, String savePath, String fileType) {
        String url = null;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(attachmentConfig.aliyunOssEndpoint,
                attachmentConfig.aliyunOssAccessKeyId, attachmentConfig.aliyunOssAccessKeySecret);
        try {
            // yourObjectName 表示上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
            String yourObjectName = null;

            if (StringUtils.isNotEmpty(savePath)) {
                yourObjectName = savePath;
            }
            PutObjectRequest putObjectRequest = new PutObjectRequest(attachmentConfig.aliyunOssBucketName, yourObjectName, file.getInputStream());
            // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(fileType);
            metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            metadata.setObjectAcl(CannedAccessControlList.PublicRead);
            putObjectRequest.setMetadata(metadata);

            // 上传文件。
            ossClient.putObject(putObjectRequest);
            url = attachmentConfig.aliyunOssDomainName + "/" + yourObjectName;
            // log.error("阿里云上传图片访问URL：{}", url);
        } catch (Exception e) {
            // log.error("阿里云上传图片失败信息：{}", e.getMessage());
        }

        return url;
    }

    /**
     * 获取public key
     *
     * @param url
     * @return
     */
    @SuppressWarnings({"finally"})
    public String executeGet(String url) {
        BufferedReader in = null;

        String content = null;
        try {
            // 定义HttpClient
            @SuppressWarnings("resource")
            DefaultHttpClient client = new DefaultHttpClient();
            // 实例化HTTP方法
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            content = sb.toString();
        } catch (Exception e) {
        } finally {
            if (in != null) {
                try {
                    in.close();// 最后要关闭BufferedReader
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return content;
        }
    }

    /**
     * 获取Post消息体
     *
     * @param request
     * @return
     */
    public String getPostBody(HttpServletRequest request) {
        StringBuilder buffer = new StringBuilder("");
        try {
            request.setCharacterEncoding("UTF-8");
            BufferedReader reader = null;
            reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            logger.error("获取阿里云附件回调内容异常", e);
        }
        return buffer.toString();
    }

    /**
     * 验证上传回调的Request
     *
     * @param request
     * @param ossCallbackBody
     * @return
     * @throws NumberFormatException
     * @throws IOException
     */
    protected boolean verifyOSSCallbackRequest(HttpServletRequest request, String ossCallbackBody)
            throws NumberFormatException, IOException {
        boolean ret = false;
        String autorizationInput = new String(request.getHeader("Authorization"));
        String pubKeyInput = request.getHeader("x-oss-pub-key-url");
        byte[] authorization = BinaryUtil.fromBase64String(autorizationInput);
        byte[] pubKey = BinaryUtil.fromBase64String(pubKeyInput);
        String pubKeyAddr = new String(pubKey);
        if (!pubKeyAddr.startsWith("http://gosspublic.alicdn.com/")
                && !pubKeyAddr.startsWith("https://gosspublic.alicdn.com/")) {
            System.out.println("pub key addr must be oss addrss");
            return false;
        }
        String retString = executeGet(pubKeyAddr);
        retString = retString.replace("-----BEGIN PUBLIC KEY-----", "");
        retString = retString.replace("-----END PUBLIC KEY-----", "");
        String queryString = request.getQueryString();
        String uri = request.getRequestURI();
        String decodeUri = java.net.URLDecoder.decode(uri, "UTF-8");
        String authStr = decodeUri;
        if (queryString != null && !queryString.equals("")) {
            authStr += "?" + queryString;
        }
        authStr += "\n" + ossCallbackBody;
        ret = doCheck(authStr, authorization, retString);
        return ret;
    }

    /**
     * 验证RSA
     *
     * @param content
     * @param sign
     * @param publicKey
     * @return
     */
    public static boolean doCheck(String content, byte[] sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature.getInstance("MD5withRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes());
            boolean bverify = signature.verify(sign);
            return bverify;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 服务器响应结果
     *
     * @param request
     * @param response
     * @param results
     * @param status
     * @throws IOException
     */
    private void response(HttpServletRequest request, HttpServletResponse response, String results, int status)
            throws IOException {
        String callbackFunName = request.getParameter("callback");
        response.addHeader("Content-Length", String.valueOf(results.length()));
        //response.addHeader("Content-Type", "application/json; charset=UTF-8");
        if (callbackFunName == null || callbackFunName.equalsIgnoreCase(""))
            response.getWriter().println(results);
        else
            response.getWriter().println(callbackFunName + "( " + results + " )");
        response.setStatus(status);
        response.flushBuffer();
    }

    /**
     * 服务器响应结果
     */
    private void response(HttpServletRequest request, HttpServletResponse response, String results) throws IOException {
        String callbackFunName = request.getParameter("callback");
        if (callbackFunName == null || callbackFunName.equalsIgnoreCase(""))
            response.getWriter().println(results);
        else
            response.getWriter().println(callbackFunName + "( " + results + " )");
        response.setStatus(HttpServletResponse.SC_OK);
        response.flushBuffer();
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        //Long类型转换为String
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    /**
     * 缩略图片
     *
     * @param imageAttachment
     * @return
     */
    private List<ThumbAttachmentId> thumbImage(String moduleId, ImageAttachment imageAttachment) throws IOException {
        List<AttachmentThumbConfigDO> thumbConfigDOS = thumbConfigQueryService.getModuleThumbConfigs(moduleId);
        if (thumbConfigDOS.isEmpty()) {
            return new ArrayList<>();
        }

        String accessId = attachmentConfig.aliyunOssAccessKeyId; // 请填写您的AccessKeyId。
        String accessKey = attachmentConfig.aliyunOssAccessKeySecret; // 请填写您的AccessKeySecret。
        String endpoint = attachmentConfig.aliyunOssEndpoint; // 请填写您的 endpoint。
        String bucketName = attachmentConfig.aliyunOssBucketName; // 请填写您的 bucketname 。
        OSSClient ossClient = new OSSClient(endpoint, accessId, accessKey);

        List<ImageThumbAttachment> thumbAttachments = new ArrayList<>();
        String parentPath = imageAttachment.getPath().substring(0, imageAttachment.getPath().lastIndexOf("/"));
        String pathName = imageAttachment.getPath().substring(imageAttachment.getPath().lastIndexOf("/") + 1);
        //循环读取配置进行不通场景下的缩略图缩略
        try {
            for (AttachmentThumbConfigDO thumbConfigDO : thumbConfigDOS) {
                String thumbFileName = String.format("%s.%s.%s", "thumb", thumbConfigDO.getScenario(), pathName);
                String thumbFilePath = String.format("%s%s%s", parentPath, "/", thumbFileName);
                //图片大于设置的阀值才进行压缩
                if (thumbConfigDO.getIsThumb().intValue() == 1 && imageAttachment.getSize() > thumbConfigDO.getSizeThreshold().intValue()) {
                    //如果图片尺寸小于设置的缩略尺寸则使用原图尺寸
                    int width = Integer.min(thumbConfigDO.getWidth().intValue(), imageAttachment.getWidth());
                    int height = Integer.min(thumbConfigDO.getHeight().intValue(), imageAttachment.getHeight());

                    StringBuilder sbStyle = new StringBuilder();
                    Formatter styleFormatter = new Formatter(sbStyle);
                    String styleType = "";
                    //缩略图片
                    if (thumbConfigDO.getIsResize().intValue() == 1) {
                        styleType = String.format("image/resize,w_%s,h_%s,m_mfit", width, height);
                    }
                    //裁剪图片
                    else if (thumbConfigDO.getIsCrop().intValue() == 1) {
                        styleType = String.format("image/crop,w_%s,h_%s,g_center", width, height);
                    }

                    styleFormatter.format("%s|sys/saveas,o_%s", styleType,
                            BinaryUtil.toBase64String(thumbFilePath.getBytes()));
                    ProcessObjectRequest request = new ProcessObjectRequest(bucketName, imageAttachment.getPath(), sbStyle.toString());
                    GenericResult processResult = ossClient.processObject(request);
                    String json = IOUtils.readStreamAsString(processResult.getResponse().getContent(), "UTF-8");
                    processResult.getResponse().getContent().close();

                    //获取图片信息
//                    String fileUrl = String.format("%s/%s", attachmentConfig.aliyunOssAccessDomain, thumbFilePath);
                    String fileUrl = String.format("%s/%s", attachmentConfig.aliyunOssDomainName, thumbFilePath);
                    String getFileInfoUrl = fileUrl + "?x-oss-process=image/info";
//                    Map fileInfo = restTemplate.getForObject(getFileInfoUrl, Map.class);
//                    int fileSize = 0;
//                    if (fileInfo != null && fileInfo.get("FileSize") != null) {
//                        Map<String, Object> fileSizeMap = (Map<String, Object>) fileInfo.get("FileSize");
//                        fileSize = Integer.valueOf(fileSizeMap.get("value").toString());
//                    }

                    ImageThumbAttachment thumbAttachment = ImageThumbAttachment.builder()
                            .organizationId(imageAttachment.getOrganizationId())
                            .imageAttachmentId(imageAttachment.getId())
                            .storageMode(imageAttachment.getStorageMode())
                            .scenario(thumbConfigDO.getScenario())
                            .name(thumbFileName)
                            .path(thumbFilePath)
                            .url(fileUrl)
                            .mimeType(imageAttachment.getMimeType())
                            .width(width)
                            .height(height)
//                            .size(fileSize)
                            .creator(imageAttachment.getCreator())
                            .build();
                    thumbAttachments.add(thumbAttachment);
                }
                //直接使用原图作为缩略图
                else {
                    ImageThumbAttachment thumbAttachment = ImageThumbAttachment.builder()
                            .organizationId(imageAttachment.getOrganizationId())
                            .imageAttachmentId(imageAttachment.getId())
                            .storageMode(imageAttachment.getStorageMode())
                            .scenario(thumbConfigDO.getScenario())
                            .name(imageAttachment.getName())
                            .path(imageAttachment.getPath())
                            .url(imageAttachment.getUrl())
                            .mimeType(imageAttachment.getMimeType())
                            .width(imageAttachment.getWidth())
                            .height(imageAttachment.getHeight())
                            .size(imageAttachment.getSize())
                            .md5(imageAttachment.getMd5())
                            .creator(imageAttachment.getCreator())
                            .build();
                    thumbAttachments.add(thumbAttachment);
                }
            }
        } catch (Exception e) {
            logger.error("阿里云oss缩略图片出错", e);
        }
        ossClient.shutdown();

        List<ThumbAttachmentId> thumbAttachmentIds = new ArrayList<>();
        for (ImageThumbAttachment thumbAttachment : thumbAttachments) {
            thumbAttachmentIds.add(attachmentThumbDataRepository.save(thumbAttachment));
        }

        return thumbAttachmentIds;
    }

    /**
     * 缩略图片
     *
     * @param imageAttachmentIds
     * @return
     */
    public List<AttachmentThumbUrlRelationDTO> repairThumbImage(String moduleId, List<Long> imageAttachmentIds) {
        if (imageAttachmentIds == null || imageAttachmentIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<AttachmentThumbConfigDO> thumbConfigDOS = thumbConfigQueryService.getModuleThumbConfigs(moduleId);
        if (thumbConfigDOS.isEmpty()) {
            return new ArrayList<>();
        }
        List<AttachmentThumbUrlRelationDTO> relationDTOS = new ArrayList<>();
        List<AttachmentDO> attachmentDOS = attachmentDomainRepository.findByIds(imageAttachmentIds.stream().map(id -> new AttachmentId(id)).collect(Collectors.toList()));
        String accessId = attachmentConfig.aliyunOssAccessKeyId; // 请填写您的AccessKeyId。
        String accessKey = attachmentConfig.aliyunOssAccessKeySecret; // 请填写您的AccessKeySecret。
        String endpoint = attachmentConfig.aliyunOssEndpoint; // 请填写您的 endpoint。
        String bucketName = attachmentConfig.aliyunOssBucketName; // 请填写您的 bucketname 。
        OSSClient ossClient = new OSSClient(endpoint, accessId, accessKey);

        List<ImageThumbAttachment> thumbAttachments = new ArrayList<>();
        for (AttachmentDO imageAttachment : attachmentDOS) {
            String parentPath = imageAttachment.getPath().substring(0, imageAttachment.getPath().lastIndexOf("/"));
            String pathName = imageAttachment.getPath().substring(imageAttachment.getPath().lastIndexOf("/") + 1);
            //循环读取配置进行不通场景下的缩略图缩略
            try {
                for (AttachmentThumbConfigDO thumbConfigDO : thumbConfigDOS) {
                    logger.error(thumbConfigDO.toString());
                    String thumbFileName = String.format("%s.%s.%s", "thumb", thumbConfigDO.getScenario(), pathName);
                    String thumbFilePath = String.format("%s%s%s", parentPath, "/", thumbFileName);
                    //图片大于设置的阀值才进行压缩
                    if (thumbConfigDO.getIsThumb().intValue() == 1 && imageAttachment.getSize() > thumbConfigDO.getSizeThreshold().intValue()) {
                        //如果图片尺寸小于设置的缩略尺寸则使用原图尺寸
                        int width = thumbConfigDO.getWidth().intValue();
                        int height = thumbConfigDO.getHeight().intValue();

                        StringBuilder sbStyle = new StringBuilder();
                        Formatter styleFormatter = new Formatter(sbStyle);
                        String styleType = "";
                        //缩略图片
                        if (thumbConfigDO.getIsResize().intValue() == 1) {
                            styleType = String.format("image/resize,w_%s,h_%s,m_mfit", width, height);
                        }
                        //裁剪图片
                        else if (thumbConfigDO.getIsCrop().intValue() == 1) {
                            styleType = String.format("image/crop,w_%s,h_%s,g_center", width, height);
                        }

                        styleFormatter.format("%s|sys/saveas,o_%s", styleType,
                                BinaryUtil.toBase64String(thumbFilePath.getBytes()));
                        ProcessObjectRequest request = new ProcessObjectRequest(bucketName, imageAttachment.getPath(), sbStyle.toString());
                        GenericResult processResult = ossClient.processObject(request);
                        String json = IOUtils.readStreamAsString(processResult.getResponse().getContent(), "UTF-8");
                        processResult.getResponse().getContent().close();

                        //获取图片信息
//                        String fileUrl = String.format("%s/%s", attachmentConfig.aliyunOssAccessDomain, thumbFilePath);
                        String fileUrl = String.format("%s/%s", attachmentConfig.aliyunOssDomainName, thumbFilePath);
                        String getFileInfoUrl = fileUrl + "?x-oss-process=image/info";
                        Map<String, Object> fileInfo = restTemplate.getForObject(getFileInfoUrl, Map.class);
                        int fileSize = 0;
                        if (fileInfo != null && fileInfo.get("FileSize") != null) {
                            Map<String, Object> fileSizeMap = (Map<String, Object>) fileInfo.get("FileSize");
                            fileSize = Integer.valueOf(fileSizeMap.get("value").toString());
                        }
                        List<AttachmentThumbBasicDTO> attachmentThumbBasicDTOS = attachmentThumbQueryService.getAttachmentThumbsByAttachmentId(imageAttachment.getId());
                        ImageThumbAttachment thumbAttachment = ImageThumbAttachment.builder()
                                .id(attachmentThumbBasicDTOS != null && !attachmentThumbBasicDTOS.isEmpty() ? new ThumbAttachmentId(attachmentThumbBasicDTOS.get(0).getId()) : null)
                                .organizationId(imageAttachment.getOrganizationId())
                                .imageAttachmentId(new AttachmentId(imageAttachment.getId()))
                                .storageMode(StorageMode.getById(imageAttachment.getStorageMode()))
                                .scenario(thumbConfigDO.getScenario())
                                .name(thumbFileName)
                                .path(thumbFilePath)
                                .url(fileUrl)
                                .mimeType(imageAttachment.getMimeType())
                                .width(width)
                                .height(height)
                                .size(fileSize)
                                .build();
                        thumbAttachments.add(thumbAttachment);
                    }
                }
            } catch (Exception e) {
                logger.error("阿里云oss缩略图片出错", e);
            }
        }

        ossClient.shutdown();

        List<ThumbAttachmentId> thumbAttachmentIds = new ArrayList<>();
        for (ImageThumbAttachment thumbAttachment : thumbAttachments) {
            ThumbAttachmentId thumbAttachmentId = attachmentThumbDataRepository.save(thumbAttachment);
            thumbAttachmentIds.add(thumbAttachmentId);
            AttachmentThumbUrlRelationDTO attachmentThumbUrlRelationDTO = new AttachmentThumbUrlRelationDTO();
            attachmentThumbUrlRelationDTO.setAttachmentId(thumbAttachment.getImageAttachmentId().getId());
            attachmentThumbUrlRelationDTO.setThumbUrl(thumbAttachment.getUrl());
            relationDTOS.add(attachmentThumbUrlRelationDTO);
        }
        return relationDTOS;
    }
}

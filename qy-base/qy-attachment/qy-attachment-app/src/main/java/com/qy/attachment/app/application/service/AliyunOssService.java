package com.qy.attachment.app.application.service;

import com.qy.attachment.app.application.dto.AliyunOssPolicyDTO;
import com.qy.attachment.app.application.dto.AttachmentThumbUrlRelationDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 阿里云oss服务
 *
 * @author legendjw
 */
public interface AliyunOssService {
    /**
     * 获取阿里云oss请求上传Policy和回调
     *
     * @return
     */
    AliyunOssPolicyDTO getAliyunOssPolicy();

    /**
     * 阿里云oss上传回调
     *
     * @param request
     * @param response
     */
    void uploadCallback(HttpServletRequest request, HttpServletResponse response) throws IOException;



    /**
     * 修复图片压缩
     * @param moduleId
     * @param imageAttachmentIds
     */
    List<AttachmentThumbUrlRelationDTO> repairThumbImage(String moduleId, List<Long> imageAttachmentIds);

    String uploadOss(MultipartFile file, String savePath, String fileType);
}

package com.qy.base.interfaces.attachment.web;

import com.qy.attachment.app.application.dto.AliyunOssPolicyDTO;
import com.qy.attachment.app.application.service.AliyunOssService;
import com.qy.rest.util.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 阿里云OSS附件
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/attachment/aliyun-oss")
public class AliyunOssController {
    private AliyunOssService aliyunOssService;

    public AliyunOssController(AliyunOssService aliyunOssService) {
        this.aliyunOssService = aliyunOssService;
    }

    /**
     * 获取阿里云OSS附件设置
     *
     * @return
     */
    @GetMapping("/configs")
    public ResponseEntity<AliyunOssPolicyDTO> getConfig() {
        return ResponseUtils.ok().body(aliyunOssService.getAliyunOssPolicy());
    }

    /**
     * 阿里云OSS上传文件回调
     *
     * @param request
     * @param response
     */
    @PostMapping("/upload-callback")
    public void uploadCallback(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        aliyunOssService.uploadCallback(request, response);
    }
}

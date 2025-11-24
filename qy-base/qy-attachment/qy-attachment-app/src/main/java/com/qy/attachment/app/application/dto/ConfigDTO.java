package com.qy.attachment.app.application.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 配置
 *
 * @author legendjw
 */
@Data
public class ConfigDTO implements Serializable {
    /**
     * 储存模式: 1: 本地 2: 阿里云OSS
     */
    private int storageMode;

    /**
     * 上传文件大小限制，单位byte
     */
    private int uploadMaxSize;

    /**
     * 上传文件格式限制
     */
    private List<String> uploadFormatLimit = new ArrayList<>();

    /**
     * 限制图片宽度，单位px
     */
    private int limitImageWidth;

    /**
     * 限制图片高度，单位px
     */
    private int limitImageHeight;

    /**
     * 保存文件路径
     */
    private String saveFilePath;
    /**
     * 保存文件路径
     */
    private Integer imageHandle;
}
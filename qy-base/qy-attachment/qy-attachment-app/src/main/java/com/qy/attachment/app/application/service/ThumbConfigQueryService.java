package com.qy.attachment.app.application.service;

import com.qy.attachment.app.application.dto.AttachmentThumbConfigDTO;
import com.qy.attachment.app.application.query.ThumbConfigQuery;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentThumbConfigDO;
import com.qy.rest.pagination.Page;

import java.util.List;

/**
 * 缩略图配置查询服务
 *
 * @author legendjw
 */
public interface ThumbConfigQueryService {
    /**
     * 查询客户缩略图配置
     *
     * @param query
     * @return
     */
    Page<AttachmentThumbConfigDTO> getAttachmentThumbConfigs(ThumbConfigQuery query);

    /**
     * 根据ID查询客户缩略图配置
     *
     * @param id
     * @return
     */
    AttachmentThumbConfigDTO getAttachmentThumbConfigById(Long id);

    /**
     * 获取指定模块的缩略图配置
     *
     * @param moduleId
     * @return
     */
    List<AttachmentThumbConfigDO> getModuleThumbConfigs(String moduleId);

    /**
     * 获取指定模块的缩略图配置
     *
     * @param moduleId
     * @param scenario
     * @return
     */
    AttachmentThumbConfigDO getModuleThumbConfigs(String moduleId, String scenario);
}
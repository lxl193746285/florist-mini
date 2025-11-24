package com.qy.attachment.app.application.service;

import com.qy.attachment.app.application.dto.AttachmentBasicDTO;
import com.qy.attachment.app.application.dto.AttachmentDTO;
import com.qy.attachment.app.application.dto.ConfigDTO;
import com.qy.attachment.app.domain.valueobject.AttachmentId;

import java.util.List;

/**
 * 附件查询服务
 *
 * @author legendjw
 */
public interface AttachmentQueryService {
    /**
     * 获取附件配置
     *
     * @param moduleId
     * @param type
     * @param scenario
     * @return
     */
    ConfigDTO getConfigs(String moduleId, String type, String scenario);

    /**
     * 根据id获取附件
     *
     * @param id
     * @return
     */
    AttachmentDTO getAttachmentById(AttachmentId id);

    /**
     * 根据id获取附件基本信息
     *
     * @param id
     * @return
     */
    AttachmentBasicDTO getBasicAttachmentById(AttachmentId id);

    /**
     * 根据id集合获取附件基本信息
     *
     * @param ids
     * @return
     */
    List<AttachmentBasicDTO> getBasicAttachmentByIds(List<AttachmentId> ids);

    /**
     * 获取关联的单附件信息
     *
     * @param moduleId
     * @param dataId
     * @param type
     * @return
     */
    AttachmentBasicDTO getRelatedBasicAttachment(String moduleId, Long dataId, String type);

    /**
     * 获取关联的多附件信息
     *
     * @param moduleId
     * @param dataId
     * @param type
     * @return
     */
    List<AttachmentBasicDTO> getRelatedBasicAttachments(String moduleId, Long dataId, String type);
}

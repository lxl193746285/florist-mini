package com.qy.attachment.app.infrastructure.persistence;

import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentRelationDO;

import java.util.List;

/**
 * 附件关联数据资源库
 *
 * @author legendjw
 */
public interface AttachmentRelationDataRepository {
    /**
     * 获取模块数据关联的单附件信息
     *
     * @param moduleId
     * @param dataId
     * @param type
     * @return
     */
    AttachmentRelationDO getAttachmentRelation(String moduleId, Long dataId, String type);

    /**
     * 获取模块数据关联的多附件信息
     *
     * @param moduleId
     * @param dataId
     * @param type
     * @return
     */
    List<AttachmentRelationDO> getAttachmentRelations(String moduleId, Long dataId, String type);

    /**
     * 创建指定单附件关联
     *
     * @param attachmentRelationDOS
     */
    void createAttachmentRelation(List<AttachmentRelationDO> attachmentRelationDOS);

    /**
     * 删除指定模块数据关联
     *
     * @param moduleId
     * @param dataId
     * @param type
     */
    void deleteAttachmentRelation(String moduleId, Long dataId, String type);
}

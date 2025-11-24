package com.qy.attachment.app.infrastructure.persistence;

import com.qy.attachment.app.application.query.ThumbConfigQuery;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentThumbConfigDO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 附件缩略图设置资源库
 *
 * @author legendjw
 */
public interface AttachmentThumbConfigDataRepository {
    /**
     * 根据模块id获取缩略设置
     *
     * @param moduleId
     * @return
     */
    List<AttachmentThumbConfigDO> findByModule(String moduleId);

    /**
     * 根据模块id以及场景获取设置
     *
     * @param moduleId
     * @param scenario
     * @return
     */
    AttachmentThumbConfigDO findByModuleAndScenario(String moduleId, String scenario);

    /**
     * 分页查询缩略图配置
     *
     * @param query
     * @return
     */
    Page<AttachmentThumbConfigDO> findByQuery(ThumbConfigQuery query);

    /**
     * 根据id集合查询缩略图配置
     *
     * @param ids
     * @return
     */
    List<AttachmentThumbConfigDO> findByIds(List<Long> ids);

    /**
     * 根据ID查询缩略图配置
     *
     * @param id
     * @return
     */
    AttachmentThumbConfigDO findById(Long id);

    /**
     * 保存一个缩略图配置
     *
     * @param attachmentThumbConfigDO
     * @return
     */
    Long save(AttachmentThumbConfigDO attachmentThumbConfigDO);

    /**
     * 移除一个缩略图配置
     *
     * @param id
     * @param identity
     */
    void remove(Long id, Identity identity);

    /**
     * 查询指定类型模块场景的数量
     *
     * @param typeId
     * @param moduleId
     * @param scenario
     * @param excludeId
     * @return
     */
    int countByTypeIdAndModuleIdAndScenario(Integer typeId, String moduleId, String scenario, Long excludeId);
}

package com.qy.attachment.app.application.service.impl;

import com.qy.attachment.app.application.assembler.ThumbConfigAssembler;
import com.qy.attachment.app.application.dto.AttachmentThumbConfigDTO;
import com.qy.attachment.app.application.query.ThumbConfigQuery;
import com.qy.attachment.app.application.service.ThumbConfigQueryService;
import com.qy.attachment.app.domain.enums.ThumbConfigType;
import com.qy.attachment.app.infrastructure.persistence.AttachmentThumbConfigDataRepository;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentThumbConfigDO;
import com.qy.rest.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 缩略图配置查询服务实现
 *
 * @author legendjw
 */
@Service
public class ThumbConfigQueryServiceImpl implements ThumbConfigQueryService {
    private ThumbConfigAssembler attachmentThumbConfigAssembler;
    private AttachmentThumbConfigDataRepository attachmentThumbConfigDataRepository;

    public ThumbConfigQueryServiceImpl(ThumbConfigAssembler attachmentThumbConfigAssembler, AttachmentThumbConfigDataRepository attachmentThumbConfigDataRepository) {
        this.attachmentThumbConfigAssembler = attachmentThumbConfigAssembler;
        this.attachmentThumbConfigDataRepository = attachmentThumbConfigDataRepository;
    }

    @Override
    public Page<AttachmentThumbConfigDTO> getAttachmentThumbConfigs(ThumbConfigQuery query) {
        Page<AttachmentThumbConfigDO> attachmentThumbConfigDOPage = attachmentThumbConfigDataRepository.findByQuery(query);
        Page<AttachmentThumbConfigDTO> attachmentThumbConfigDTOPage = attachmentThumbConfigDOPage.map(attachmentThumbConfig -> fillRelatedData(attachmentThumbConfigAssembler.toDTO(attachmentThumbConfig)));
        return attachmentThumbConfigDTOPage;
    }

    @Override
    public AttachmentThumbConfigDTO getAttachmentThumbConfigById(Long id) {
        AttachmentThumbConfigDO attachmentThumbConfigDO = attachmentThumbConfigDataRepository.findById(id);
        return attachmentThumbConfigDO == null ? null : fillRelatedData(attachmentThumbConfigAssembler.toDTO(attachmentThumbConfigDO));
    }

    @Override
    public List<AttachmentThumbConfigDO> getModuleThumbConfigs(String moduleId) {
        List<AttachmentThumbConfigDO> configDOS = attachmentThumbConfigDataRepository.findByModule(moduleId);
        if (configDOS.isEmpty()) { return new ArrayList<>(); }
        List<AttachmentThumbConfigDO> thumbConfigDOS = new ArrayList<>();
        //判断是否有模块下的场景值覆盖了系统场景
        for (AttachmentThumbConfigDO configDO : configDOS) {
            if (configDO.getTypeId().intValue() == ThumbConfigType.SYSTEM.getId()) {
                if (!configDOS.stream().anyMatch(c -> c.getScenario().equals(configDO.getScenario()) && c.getTypeId().equals(ThumbConfigType.MODULE.getId()))) {
                    thumbConfigDOS.add(configDO);
                }
            }
            else {
                thumbConfigDOS.add(configDO);
            }
        }
        return thumbConfigDOS;
    }

    @Override
    public AttachmentThumbConfigDO getModuleThumbConfigs(String moduleId, String scenario) {
        return attachmentThumbConfigDataRepository.findByModuleAndScenario(moduleId, scenario);
    }

    private AttachmentThumbConfigDTO fillRelatedData(AttachmentThumbConfigDTO attachmentThumbConfigDTO) {
        return attachmentThumbConfigDTO;
    }
}

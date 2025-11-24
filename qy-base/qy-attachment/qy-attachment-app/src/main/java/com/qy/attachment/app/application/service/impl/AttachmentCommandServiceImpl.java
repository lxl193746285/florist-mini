package com.qy.attachment.app.application.service.impl;

import com.qy.attachment.app.application.command.RelateAttachmentCommand;
import com.qy.attachment.app.application.command.RelateAttachmentsCommand;
import com.qy.attachment.app.application.service.AttachmentCommandService;
import com.qy.attachment.app.infrastructure.persistence.AttachmentRelationDataRepository;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentRelationDO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 附件命令服务实现
 *
 * @author legendjw
 */
@Service
public class AttachmentCommandServiceImpl implements AttachmentCommandService {
    private AttachmentRelationDataRepository attachmentRelationDataRepository;

    public AttachmentCommandServiceImpl(AttachmentRelationDataRepository attachmentRelationDataRepository) {
        this.attachmentRelationDataRepository = attachmentRelationDataRepository;
    }

    @Override
    public void relateAttachment(RelateAttachmentCommand command) {
        attachmentRelationDataRepository.deleteAttachmentRelation(command.getModuleId(), command.getDataId(), command.getType());

        List<AttachmentRelationDO> relationDOS = new ArrayList<>();
        AttachmentRelationDO relationDO = new AttachmentRelationDO();
        relationDO.setAttachmentId(command.getAttachmentId());
        relationDO.setModuleId(command.getModuleId());
        relationDO.setDataId(command.getDataId());
        relationDO.setType(command.getType());
        relationDOS.add(relationDO);
        attachmentRelationDataRepository.createAttachmentRelation(relationDOS);
    }

    @Override
    public void relateAttachments(RelateAttachmentsCommand command) {
        attachmentRelationDataRepository.deleteAttachmentRelation(command.getModuleId(), command.getDataId(), command.getType());

        List<AttachmentRelationDO> relationDOS = new ArrayList<>();
        for (Long attachmentId : command.getAttachmentIds()) {
            AttachmentRelationDO relationDO = new AttachmentRelationDO();
            relationDO.setAttachmentId(attachmentId);
            relationDO.setModuleId(command.getModuleId());
            relationDO.setDataId(command.getDataId());
            relationDO.setType(command.getType());
            relationDOS.add(relationDO);
        }
        attachmentRelationDataRepository.createAttachmentRelation(relationDOS);
    }
}

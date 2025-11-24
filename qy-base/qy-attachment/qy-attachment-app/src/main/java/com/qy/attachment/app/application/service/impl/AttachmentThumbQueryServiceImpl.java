package com.qy.attachment.app.application.service.impl;

import com.qy.attachment.app.application.assembler.AttachmentAssembler;
import com.qy.attachment.app.application.dto.AttachmentThumbBasicDTO;
import com.qy.attachment.app.application.service.AttachmentThumbQueryService;
import com.qy.attachment.app.infrastructure.persistence.AttachmentThumbDataRepository;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentThumbDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 附件缩略图查询服务实现
 *
 * @author legendjw
 */
@Service
public class AttachmentThumbQueryServiceImpl implements AttachmentThumbQueryService {
    private AttachmentAssembler attachmentAssembler;
    private AttachmentThumbDataRepository attachmentThumbDataRepository;

    public AttachmentThumbQueryServiceImpl(AttachmentAssembler attachmentAssembler, AttachmentThumbDataRepository attachmentThumbDataRepository) {
        this.attachmentAssembler = attachmentAssembler;
        this.attachmentThumbDataRepository = attachmentThumbDataRepository;
    }

    @Override
    public List<AttachmentThumbBasicDTO> getAttachmentThumbsByAttachmentId(Long attachmentId) {
        List<AttachmentThumbDO> thumbDOS = attachmentThumbDataRepository.findByAttachmentId(attachmentId);
        return thumbDOS.stream().map(t -> attachmentAssembler.toBasicDTO(t)).collect(Collectors.toList());
    }
}

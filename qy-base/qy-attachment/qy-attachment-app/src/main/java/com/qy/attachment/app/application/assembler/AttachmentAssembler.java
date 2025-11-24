package com.qy.attachment.app.application.assembler;

import com.qy.attachment.app.application.dto.AttachmentBasicDTO;
import com.qy.attachment.app.application.dto.AttachmentDTO;
import com.qy.attachment.app.application.dto.AttachmentThumbBasicDTO;
import com.qy.attachment.app.application.service.AttachmentThumbQueryService;
import com.qy.attachment.app.config.AttachmentConfig;
import com.qy.attachment.app.domain.enums.StorageMode;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentDO;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentThumbDO;
import com.qy.rest.constant.DateTimeFormatConstant;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 附件汇编器
 *
 * @author legendjw
 */
@Mapper
public abstract class AttachmentAssembler {
    @Autowired
    private AttachmentConfig attachmentConfig;
    @Autowired
    private AttachmentThumbQueryService attachmentThumbQueryService;

    @Mapping(source = "attachmentDO", target = "path", qualifiedByName = "mapPath")
    @Mapping(source = "createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    abstract public AttachmentDTO toDTO(AttachmentDO attachmentDO);
    @Mapping(source = "createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    abstract public AttachmentBasicDTO toBasicDTO(AttachmentDO attachmentDO);
    @Mapping(source = "createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    abstract public AttachmentThumbBasicDTO toBasicDTO(AttachmentThumbDO attachmentThumbDO);

    @Named("mapPath")
    public String mapPath(AttachmentDO attachmentDO) {
        if (attachmentDO.getStorageMode().intValue() == StorageMode.LOCAL.getId()) {
            return String.format("%s%s%s", attachmentConfig.localUploadPath, java.io.File.separator, attachmentDO.getPath());
        }
        else {
            return attachmentDO.getPath();
        }
    }

    @AfterMapping
    public void fillRelatedData(AttachmentDO attachmentDO, @MappingTarget AttachmentDTO target) {
        if (attachmentDO.getIsImage().intValue() == 1) {
            List<AttachmentThumbBasicDTO> thumbBasicDTOS = attachmentThumbQueryService.getAttachmentThumbsByAttachmentId(attachmentDO.getId());
            if (!thumbBasicDTOS.isEmpty()) {
                target.setThumbUrl(thumbBasicDTOS.get(0).getUrl());
            }
            target.setThumbs(thumbBasicDTOS);
        }
    }

    @AfterMapping
    public void fillRelatedData(AttachmentDO attachmentDO, @MappingTarget AttachmentBasicDTO target) {
        if (attachmentDO.getIsImage().intValue() == 1) {
            List<AttachmentThumbBasicDTO> thumbBasicDTOS = attachmentThumbQueryService.getAttachmentThumbsByAttachmentId(attachmentDO.getId());
            if (!thumbBasicDTOS.isEmpty()) {
                target.setThumbUrl(thumbBasicDTOS.get(0).getUrl());
            }
            target.setThumbs(thumbBasicDTOS);
        }
    }
}
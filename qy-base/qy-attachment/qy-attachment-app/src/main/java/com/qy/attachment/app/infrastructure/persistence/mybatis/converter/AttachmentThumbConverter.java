package com.qy.attachment.app.infrastructure.persistence.mybatis.converter;

import com.qy.attachment.app.domain.entity.ImageThumbAttachment;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentThumbDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 附件缩略图转化器
 *
 * @author legendjw
 */
@Mapper
public interface AttachmentThumbConverter {
    @Mappings({
            @Mapping(source = "id.id", target = "id"),
            @Mapping(source = "imageAttachmentId.id", target = "attachmentId"),
            @Mapping(source = "storageMode.id", target = "storageMode"),
            @Mapping(source = "creator.id", target = "creatorId"),
            @Mapping(source = "creator.name", target = "creatorName"),
    })
    AttachmentThumbDO toDO(ImageThumbAttachment attachment);
}
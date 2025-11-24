package com.qy.attachment.app.infrastructure.persistence.mybatis.converter;

import com.qy.attachment.app.domain.entity.FileAttachment;
import com.qy.attachment.app.domain.entity.ImageAttachment;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 附件转化器
 *
 * @author legendjw
 */
@Mapper
public interface AttachmentConverter {
    @Mappings({
            @Mapping(source = "id.id", target = "id"),
            @Mapping(source = "storageMode.id", target = "storageMode"),
            @Mapping(source = "creator.id", target = "creatorId"),
            @Mapping(source = "creator.name", target = "creatorName"),
    })
    AttachmentDO toDO(FileAttachment attachment);

    @Mappings({
            @Mapping(source = "id.id", target = "id"),
            @Mapping(source = "storageMode.id", target = "storageMode"),
            @Mapping(source = "creator.id", target = "creatorId"),
            @Mapping(source = "creator.name", target = "creatorName"),
    })
    AttachmentDO toDO(ImageAttachment attachment);
}
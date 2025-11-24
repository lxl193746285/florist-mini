package com.qy.message.app.application.assembler;

import com.qy.message.app.application.dto.InternalMessageDTO;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageWithUserDO;
import com.qy.rest.constant.DateTimeFormatConstant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 消息汇编器
 *
 * @author legendjw
 */
@Mapper
public abstract class MessageAssembler {
    @Mapping(source = "createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    public abstract InternalMessageDTO toDTO(MessageWithUserDO messageWithUserDO);
}
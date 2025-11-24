package com.qy.member.app.application.assembler;

import com.qy.member.app.application.dto.WeixinSessionDTO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.WeixinSessionDO;
import org.mapstruct.Mapper;

/**
 * 微信会话汇编器
 *
 * @author legendjw
 */
@Mapper
public abstract class WeixinSessionAssembler {
    public abstract WeixinSessionDTO toDTO(WeixinSessionDO weixinSessionDO);
}
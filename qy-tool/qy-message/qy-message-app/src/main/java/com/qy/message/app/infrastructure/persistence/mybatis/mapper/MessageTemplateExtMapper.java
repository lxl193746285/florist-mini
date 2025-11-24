package com.qy.message.app.infrastructure.persistence.mybatis.mapper;

import com.qy.message.app.application.dto.MessageTemplateVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MessageTemplateExtMapper {

    List<MessageTemplateVo> selectTemplateList(Map<String,Object> params);

    MessageTemplateVo selectOne(@Param("id")Integer id);
}
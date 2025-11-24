package com.qy.message.app.infrastructure.persistence.mybatis.mapper;

import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplateCustomParameter;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplateCustomParameterExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageTemplateCustomParameterMapper {
    long countByExample(MessageTemplateCustomParameterExample example);

    int deleteByExample(MessageTemplateCustomParameterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MessageTemplateCustomParameter record);

    int insertSelective(MessageTemplateCustomParameter record);

    List<MessageTemplateCustomParameter> selectByExample(MessageTemplateCustomParameterExample example);

    MessageTemplateCustomParameter selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MessageTemplateCustomParameter record, @Param("example") MessageTemplateCustomParameterExample example);

    int updateByExample(@Param("record") MessageTemplateCustomParameter record, @Param("example") MessageTemplateCustomParameterExample example);

    int updateByPrimaryKeySelective(MessageTemplateCustomParameter record);

    int updateByPrimaryKey(MessageTemplateCustomParameter record);
}
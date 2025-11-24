package com.qy.message.app.infrastructure.persistence.mybatis.mapper;

import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplateParameter;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplateParameterExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageTemplateParameterMapper {
    long countByExample(MessageTemplateParameterExample example);

    int deleteByExample(MessageTemplateParameterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MessageTemplateParameter record);

    int insertSelective(MessageTemplateParameter record);

    List<MessageTemplateParameter> selectByExample(MessageTemplateParameterExample example);

    MessageTemplateParameter selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MessageTemplateParameter record, @Param("example") MessageTemplateParameterExample example);

    int updateByExample(@Param("record") MessageTemplateParameter record, @Param("example") MessageTemplateParameterExample example);

    int updateByPrimaryKeySelective(MessageTemplateParameter record);

    int updateByPrimaryKey(MessageTemplateParameter record);
}
package com.qy.message.app.infrastructure.persistence.mybatis.mapper;

import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplateRule;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplateRuleExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageTemplateRuleMapper {
    long countByExample(MessageTemplateRuleExample example);

    int deleteByExample(MessageTemplateRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MessageTemplateRule record);

    int insertSelective(MessageTemplateRule record);

    List<MessageTemplateRule> selectByExample(MessageTemplateRuleExample example);

    MessageTemplateRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MessageTemplateRule record, @Param("example") MessageTemplateRuleExample example);

    int updateByExample(@Param("record") MessageTemplateRule record, @Param("example") MessageTemplateRuleExample example);

    int updateByPrimaryKeySelective(MessageTemplateRule record);

    int updateByPrimaryKey(MessageTemplateRule record);
}
package com.qy.message.app.infrastructure.persistence.mybatis.mapper;

import com.qy.message.app.application.command.MessageLogCommand;
import com.qy.message.app.application.dto.MessageLogDTO;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageLog;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageLogExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageLogMapper {
    long countByExample(MessageLogExample example);

    int deleteByExample(MessageLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MessageLog record);

    int insertSelective(MessageLog record);

    List<MessageLog> selectByExample(MessageLogExample example);

    MessageLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MessageLog record, @Param("example") MessageLogExample example);

    int updateByExample(@Param("record") MessageLog record, @Param("example") MessageLogExample example);

    int updateByPrimaryKeySelective(MessageLog record);

    int updateByPrimaryKey(MessageLog record);

    List<MessageLogDTO> getMessageLogs(@Param("param") MessageLogCommand messageLogCommand);
}
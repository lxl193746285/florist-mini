package com.qy.message.app.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.qy.message.app.application.command.MessageLogCommand;
import com.qy.message.app.application.dto.MessageLogDTO;
import com.qy.message.app.application.enums.MessageLogType;
import com.qy.message.app.application.service.MessageLogService;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageLog;
import com.qy.message.app.infrastructure.persistence.mybatis.mapper.MessageLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MessageLogServiceImpl implements MessageLogService {
  @Autowired
  private MessageLogMapper messageLogMapper;
  private static final Logger logger = LoggerFactory.getLogger(MessageLogServiceImpl.class);

  @Override
  public Integer createMessageLog(Integer templateId, Byte messageType, String title,
      String content, Long receiveUserId, Byte sendStatus, String uniqueKey, Integer feebackTime,
      Integer createTime,String errorMsg,String srcId,Long srcRowId) {
    MessageLog messageLog = new MessageLog();
    messageLog.setTemplateId(templateId);
    messageLog.setMessageType(messageType);
    messageLog.setTitle(title);
    messageLog.setContent(content);
    messageLog.setSendTime(LocalDateTime.now());
    messageLog.setReceiveUserId(receiveUserId);
    messageLog.setSendStatus(sendStatus);
    messageLog.setUniqueKey(uniqueKey);
    messageLog.setFeebackTime(feebackTime);
    messageLog.setErrorMsg(errorMsg);
    messageLog.setSrcId(srcId);
    messageLog.setSrcRowId(srcRowId);
    return messageLogMapper.insertSelective(messageLog);
  }

  @Override
  public List<MessageLogDTO> getMessageLogs(MessageLogCommand messageLogCommand) {
    System.out.println("开始========");
    System.out.println("===========获取消息日志记录列表=============messageLogCommandimpl" + JSON.toJSONString(messageLogCommand));
    List<MessageLogDTO> messageLogList = messageLogMapper.getMessageLogs(messageLogCommand);
    for (MessageLogDTO messageLogDTO : messageLogList) {
        if (messageLogDTO.getMessageType() != null) {
          messageLogDTO.setMessageTypeName(MessageLogType.idToName(messageLogDTO.getMessageType()));
        }
        if (messageLogDTO.getSendStatus().intValue() == 0) {
          messageLogDTO.setSendStatusName("失败");
        } else if (messageLogDTO.getSendStatus().intValue() == 1) {
          messageLogDTO.setSendStatusName("成功");
        }
        if (messageLogDTO.getSendTime() != null) {
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
          String dateStr = formatter.format(messageLogDTO.getSendTime());
          messageLogDTO.setSendTimeName(dateStr);
        }
    }
    return messageLogList;
  }
}

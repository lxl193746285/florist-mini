package com.qy.message.app.application.service;

import com.qy.message.app.application.command.MessageLogCommand;
import com.qy.message.app.application.dto.MessageLogDTO;

import java.util.List;

public interface MessageLogService {
  /**
   * 加入消息日志
   * 
   * @param templateId 模版id
   * @param messageType 消息类型
   * @param title 标题
   * @param content 内容
   * @param receiveUserId 接受人id
   * @param sendStatus 发送状态
   * @param uniqueKey 唯一码
   * @param feebackTime 反馈时间
   * @param createTime 创建时间
   * @param errorMsg 错误信息
   * @return
   */
  public Integer createMessageLog(Integer templateId, Byte messageType, String title,
      String content, Long receiveUserId, Byte sendStatus, String uniqueKey,
      Integer feebackTime,Integer createTime,String errorMsg,String srcId,Long srcRowId);

  List<MessageLogDTO> getMessageLogs(MessageLogCommand messageLogCommand);
}

package com.qy.message.app.application.service;

import com.qy.message.app.application.dto.User;
import com.qy.message.app.application.result.ResultBean;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplateCustomParameter;

import java.util.Map;

public interface MessageTemplateCustomParameterService {
  /**
   * 解析自定义变量参数
   * 
   * @param templateId
   * @param map
   * @return
   * @throws ClassNotFoundException
   */
  public Map<String, Object> customParameterParse(Integer templateId, Map<String, Object> map);

  /**
   * 获取自定义参数列表
   * 
   * @param page
   * @param perPage
   * @param keyMode
   * @param templateId
   * @param user
   * @return
   */
  public ResultBean<MessageTemplateCustomParameter> getMessageTemplateCustomParameters(Integer page,
                                                                                       Integer perPage, Byte keyMode, Integer templateId, User user);

  /**
   * 获取自定义参数详情
   * 
   * @param id
   * @return
   */
  public MessageTemplateCustomParameter getMessageTemplateCustomParametersById(Integer id);

  /**
   * 添加自定义参数
   * 
   * @param messageTemplateParameter
   * @return
   */
  public MessageTemplateCustomParameter createMessageTemplateCustomParameter(
      MessageTemplateCustomParameter messageTemplateParameter);

  /**
   * 修改自定义参数
   * 
   * @param messageTemplateParameter
   * @return
   */
  public MessageTemplateCustomParameter updateMessageTemplateCustomParameter(
      MessageTemplateCustomParameter messageTemplateParameter);

  /**
   * 删除自定义参数
   * 
   * @param id
   * @return
   */
  public Integer deleteMessageTemplateCustomParameter(Integer id);
}

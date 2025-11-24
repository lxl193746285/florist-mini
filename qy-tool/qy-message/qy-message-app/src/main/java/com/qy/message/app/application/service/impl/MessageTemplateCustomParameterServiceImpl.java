package com.qy.message.app.application.service.impl;

import com.qy.message.app.application.dto.User;
import com.qy.message.app.application.enums.MessageTemplateCustomParameterAction;
import com.qy.message.app.application.parse.ParseUtils;
import com.qy.message.app.application.result.ResultBean;
import com.qy.message.app.application.service.MessageTemplateCustomParameterService;
import com.qy.message.app.application.service.SqlService;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplateCustomParameter;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplateCustomParameterExample;
import com.qy.message.app.infrastructure.persistence.mybatis.mapper.MessageTemplateCustomParameterMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class MessageTemplateCustomParameterServiceImpl
        implements MessageTemplateCustomParameterService {
    @Autowired
    private MessageTemplateCustomParameterMapper messageTemplateCustomParameterMapper;
    @Autowired
    private SqlService sqlService;

    private String prefix = "backend/backend/messageTemplateParameter/";
    private String is_view_auth = prefix + "view";
    private String is_edit_auth = prefix + "update";
    private String is_delete_auth = prefix + "delete";
    private String is_add_auth = prefix + "create";


    @Override
    public Map<String, Object> customParameterParse(Integer templateId, Map<String, Object> map) {
        Map<String, Object> retuenMap = new HashMap<String, Object>();
        MessageTemplateCustomParameterExample example = new MessageTemplateCustomParameterExample();
        example.createCriteria().andTemplatelIdEqualTo(templateId);
        List<MessageTemplateCustomParameter> customParameters =
                messageTemplateCustomParameterMapper.selectByExample(example);
        for (MessageTemplateCustomParameter messageTemplateCustomParameter : customParameters) {
            if (messageTemplateCustomParameter.getKeyMode() == 1) {
                retuenMap.put(messageTemplateCustomParameter.getKeyId(),
                        messageTemplateCustomParameter.getKeyValue());
            } else if (messageTemplateCustomParameter.getKeyMode() == 2) {
                String msg = ParseUtils.getDataSys(messageTemplateCustomParameter.getKeyValue(), map);
                retuenMap.put(messageTemplateCustomParameter.getKeyId(), msg);
            }
            if (messageTemplateCustomParameter.getKeyMode() == 3) {
                String msg = ParseUtils.getDataSys(messageTemplateCustomParameter.getKeyValue(), map);
                retuenMap.put(messageTemplateCustomParameter.getKeyId(), sqlService.getContentMsg(msg));
            }
        }
        return retuenMap;
    }

    @Override
    public ResultBean<MessageTemplateCustomParameter> getMessageTemplateCustomParameters(Integer page,
                                                                                         Integer perPage, Byte keyMode, Integer templateId, User user) {
        ResultBean<MessageTemplateCustomParameter> result =
                new ResultBean<MessageTemplateCustomParameter>();
        PageHelper.startPage(page, perPage);
        MessageTemplateCustomParameterExample messageTemplateParameterExample =
                new MessageTemplateCustomParameterExample();
        MessageTemplateCustomParameterExample.Criteria criteria = messageTemplateParameterExample.createCriteria();
        criteria.andTemplatelIdEqualTo(templateId);
        if (null != keyMode) {
            criteria.andKeyModeEqualTo(keyMode);
        }
        List<MessageTemplateCustomParameter> messageTemplateParameters =
                messageTemplateCustomParameterMapper.selectByExample(messageTemplateParameterExample);
        if (CollectionUtils.isEmpty(messageTemplateParameters)) {
            return result;
        }
        PageInfo<MessageTemplateCustomParameter> pageInfo = new PageInfo<>(messageTemplateParameters);
        result.setTotalCount(pageInfo.getTotal());
        result.setPageCount((long) pageInfo.getPages());
        for (MessageTemplateCustomParameter messageTemplateParameter : messageTemplateParameters) {
            List<MessageTemplateCustomParameterAction> actions =
                    getMessageTemplateCustomParameterActions(messageTemplateParameter, user);
            messageTemplateParameter.setMessageTemplateCustomParameterActions(actions);
        }
        result.setData(messageTemplateParameters);
        return result;
    }

    private List<MessageTemplateCustomParameterAction> getMessageTemplateCustomParameterActions(
            MessageTemplateCustomParameter messageTemplateParameter, User user) {
        List<MessageTemplateCustomParameterAction> actions =
                new ArrayList<MessageTemplateCustomParameterAction>();
        actions.add(MessageTemplateCustomParameterAction.VIEW);
        actions.add(MessageTemplateCustomParameterAction.ADD);
        actions.add(MessageTemplateCustomParameterAction.EDIT);
        actions.add(MessageTemplateCustomParameterAction.DELETE);
        Collections.sort(actions, (a, b) -> b.getSort().compareTo(a.getSort()));
        Collections.reverse(actions);
        return actions;
    }

    @Override
    public MessageTemplateCustomParameter getMessageTemplateCustomParametersById(Integer id) {
        return messageTemplateCustomParameterMapper.selectByPrimaryKey(id);
    }

    @Override
    public MessageTemplateCustomParameter createMessageTemplateCustomParameter(
            MessageTemplateCustomParameter messageTemplateParameter) {
        Integer result = messageTemplateCustomParameterMapper.insertSelective(messageTemplateParameter);
        return result > 0 ? messageTemplateParameter : null;
    }

    @Override
    public MessageTemplateCustomParameter updateMessageTemplateCustomParameter(
            MessageTemplateCustomParameter messageTemplateParameter) {
        Integer result =
                messageTemplateCustomParameterMapper.updateByPrimaryKeySelective(messageTemplateParameter);
        return result > 0 ? messageTemplateParameter : null;
    }

    @Override
    public Integer deleteMessageTemplateCustomParameter(Integer id) {
        return messageTemplateCustomParameterMapper.deleteByPrimaryKey(id);
    }

}

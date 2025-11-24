package com.qy.message.app.application.service.impl;

import com.qy.message.app.application.dto.User;
import com.qy.message.app.application.enums.MessageTemplateParameterAction;
import com.qy.message.app.application.parse.ParseUtils;
import com.qy.message.app.application.result.ResultBean;
import com.qy.message.app.application.service.MessageTemplateParameterService;
import com.qy.message.app.application.service.SqlService;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplateParameter;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplateParameterExample;
import com.qy.message.app.infrastructure.persistence.mybatis.mapper.MessageTemplateParameterMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class MessageTemplateParameterServiceImpl implements MessageTemplateParameterService {
    @Autowired
    private MessageTemplateParameterMapper messageTemplateParameterMapper;
    @Autowired
    private SqlService sqlService;
    private String prefix = "backend/backend/messageTemplateParameter/";
    private String is_view_auth = prefix + "view";
    private String is_edit_auth = prefix + "update";
    private String is_delete_auth = prefix + "delete";
    private String is_add_auth = prefix + "create";

    @Override
    public ResultBean<MessageTemplateParameter> getMessageTemplateParameters(Integer page,
                                                                             Integer perPage, Byte keyMode, Integer templateId, User user, Byte parameterType) {
        ResultBean<MessageTemplateParameter> result = new ResultBean<MessageTemplateParameter>();
        PageHelper.startPage(page, perPage);
        MessageTemplateParameterExample messageTemplateParameterExample = new MessageTemplateParameterExample();
        MessageTemplateParameterExample.Criteria criteria = messageTemplateParameterExample.createCriteria();
        criteria.andTemplatelIdEqualTo(templateId);
        if (null != keyMode) {
            criteria.andKeyModeEqualTo(keyMode);
        }
        if (null != parameterType) {
            criteria.andParameterTypeEqualTo(parameterType);
        }
        List<MessageTemplateParameter> messageTemplateParameters =
                messageTemplateParameterMapper.selectByExample(messageTemplateParameterExample);
        if (CollectionUtils.isEmpty(messageTemplateParameters)) {
            return result;
        }
        PageInfo<MessageTemplateParameter> pageInfo = new PageInfo<>(messageTemplateParameters);
        result.setTotalCount(pageInfo.getTotal());
        result.setPageCount((long) pageInfo.getPages());
        for (MessageTemplateParameter messageTemplateParameter : messageTemplateParameters) {
            List<MessageTemplateParameterAction> actions =
                    getMessageTemplateParameterActions(messageTemplateParameter, user);
            messageTemplateParameter.setMessageTemplateParameterActions(actions);
        }
        result.setData(messageTemplateParameters);
        return result;
    }

    @Override
    public MessageTemplateParameter getMessageTemplateParametersById(Integer id) {
        return messageTemplateParameterMapper.selectByPrimaryKey(id);
    }

    @Override
    public MessageTemplateParameter createMessageTemplateParameter(
            MessageTemplateParameter messageTemplateParameter) {
        Integer result = messageTemplateParameterMapper.insertSelective(messageTemplateParameter);
        return result > 0 ? messageTemplateParameter : null;
    }

    @Override
    public MessageTemplateParameter updateMessageTemplateParameter(
            MessageTemplateParameter messageTemplateParameter) {
        Integer result = messageTemplateParameterMapper.updateByPrimaryKeySelective(messageTemplateParameter);
        return result > 0 ? messageTemplateParameter : null;
    }

    @Override
    public Integer deleteMessageTemplateParameter(Integer id) {
        return messageTemplateParameterMapper.deleteByPrimaryKey(id);
    }


    private List<MessageTemplateParameterAction> getMessageTemplateParameterActions(
            MessageTemplateParameter messageTemplateParameter, User user) {
        List<MessageTemplateParameterAction> actions = new ArrayList<MessageTemplateParameterAction>();
        actions.add(MessageTemplateParameterAction.VIEW);
        actions.add(MessageTemplateParameterAction.ADD);
        actions.add(MessageTemplateParameterAction.EDIT);
        actions.add(MessageTemplateParameterAction.DELETE);
        Collections.sort(actions, (a, b) -> b.getSort().compareTo(a.getSort()));
        Collections.reverse(actions);
        return actions;
    }

    @Override
    public Map<String, Object> parameterParse(Integer templateId, Map<String, Object> map) {
        Map<String, Object> retuenMap = new HashMap<String, Object>();
        MessageTemplateParameterExample example = new MessageTemplateParameterExample();
        example.createCriteria().andTemplatelIdEqualTo(templateId);
        List<MessageTemplateParameter> customParameters =
                messageTemplateParameterMapper.selectByExample(example);
        for (MessageTemplateParameter messageTemplateParameter : customParameters) {
            if (messageTemplateParameter.getKeyMode() == 1) {
                retuenMap.put(messageTemplateParameter.getKeyId(),
                        messageTemplateParameter.getKeyValue());
            } else if (messageTemplateParameter.getKeyMode() == 2) {
                String msg = ParseUtils.getDataSys(messageTemplateParameter.getKeyValue(), map);
                retuenMap.put(messageTemplateParameter.getKeyId(), msg);
            }
            if (messageTemplateParameter.getKeyMode() == 3) {
                String msg = ParseUtils.getDataSys(messageTemplateParameter.getKeyValue(), map);
                retuenMap.put(messageTemplateParameter.getKeyId(), sqlService.getContentMsg(msg));
            }
        }
        return retuenMap;

    }

}

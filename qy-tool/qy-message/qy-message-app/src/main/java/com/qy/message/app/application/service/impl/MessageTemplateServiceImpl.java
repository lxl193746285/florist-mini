package com.qy.message.app.application.service.impl;

import com.qy.message.app.application.dto.MessageTemplateVo;
import com.qy.message.app.application.dto.PlatformDTO;
import com.qy.message.app.application.enums.Template;
import com.qy.message.app.application.enums.TemplateButton;
import com.qy.message.app.application.parse.ParseUtils;
import com.qy.message.app.application.result.ResultBean;
import com.qy.message.app.application.service.MessageTemplateService;
import com.qy.message.app.application.service.PlatformQueryService;
import com.qy.message.app.application.service.SqlService;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplate;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplateExample;
import com.qy.message.app.infrastructure.persistence.mybatis.mapper.MessageTemplateExtMapper;
import com.qy.message.app.infrastructure.persistence.mybatis.mapper.MessageTemplateMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageTemplateServiceImpl implements MessageTemplateService {

    @Autowired
    private MessageTemplateMapper messageTemplateMapper;
    @Autowired
    private MessageTemplateExtMapper messageTemplateExtMapper;
    @Autowired
    private SqlService sqlService;
    @Autowired
    private PlatformQueryService platformQueryService;


    @Override
    public ResultBean<MessageTemplateVo> selectTemplateList(Integer page, Integer perPage, Map<String,Object> params) {
        PageHelper.startPage(page, perPage);
        List<MessageTemplateVo> messageTemplateVos = messageTemplateExtMapper.selectTemplateList(params);
        // 处理状态信息
        if (messageTemplateVos != null && messageTemplateVos.size() > 0) {
            // 处理权限
            getPermission(messageTemplateVos);
        }
        PageInfo<MessageTemplateVo> pageInfo = new PageInfo<MessageTemplateVo>(messageTemplateVos);
        ResultBean<MessageTemplateVo> resultBean = new ResultBean<MessageTemplateVo>();
        resultBean.setData(messageTemplateVos);
        resultBean.setTotalCount(pageInfo.getTotal());
        resultBean.setPageCount((long) pageInfo.getPages());
        return resultBean;
    }

    @Override
    public MessageTemplateVo selectOne(Integer id) {
        return messageTemplateExtMapper.selectOne(id);
    }

    @Override
    public MessageTemplateVo createMessageTemplateVo(MessageTemplateVo messageTemplateVo) {
        messageTemplateMapper.insertSelective(messageTemplateVo);
        return messageTemplateVo;
    }

    @Override
    public MessageTemplateVo updateMessageTemplateVo(MessageTemplateVo messageTemplateVo) {
        MessageTemplateExample messageTemplateExample = new MessageTemplateExample();
        messageTemplateExample.createCriteria().andIdEqualTo(messageTemplateVo.getId());
        int count = messageTemplateMapper.updateByExampleSelective(messageTemplateVo,messageTemplateExample);
        if(count == 1){
            return messageTemplateVo;
        }
        return null;
    }

    @Override
    public int deleteMessageTemplateVo(Integer id) {
        return messageTemplateMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<MessageTemplateVo> selectTemplates() {
        Map<String,Object> params = new HashMap<>();
        List<MessageTemplateVo> messageTemplateVos = messageTemplateExtMapper.selectTemplateList(params);
        return messageTemplateVos;
    }

    @Override
    public MessageTemplate getMessageTemplateByModuleFunction(String module, String function, String weixinAppId) {
        MessageTemplateExample messageTemplateExample = new MessageTemplateExample();
        MessageTemplateExample.Criteria criteria = messageTemplateExample.createCriteria();
        criteria.andModelIdEqualTo(module);
        criteria.andFunctionIdEqualTo(function);
        if (StringUtils.isNotBlank(weixinAppId)) {
            PlatformDTO platformDTO = platformQueryService.getPlatformByWeixinAppId(weixinAppId);
            criteria.andClientTypeEqualTo(platformDTO != null ? platformDTO.getId().intValue() : 0);
        }

        List<MessageTemplate> messageTemplates = messageTemplateMapper.selectByExample(messageTemplateExample);
        if (messageTemplates.isEmpty()) {
            return null;
        }
        return messageTemplates.get(0);
    }

    @Override
    public String getParsedTitle(MessageTemplate messageTemplate, Map<String, Object> params) {
        String title = "";
        //判断标题取值模式
        if (messageTemplate.getTitleMode().intValue() == Template.MODEL_TXT.getId()) {
            title = messageTemplate.getTitle();
        } else if (messageTemplate.getTitleMode().intValue() == Template.MODEL_PARAMS.getId()) {
            //参数置换
            title = ParseUtils.getDataSys(messageTemplate.getTitle(), params);
        } else if (messageTemplate.getTitleMode().intValue() == Template.MODEL_SQL.getId()) {
            //参数置换后进行sql转换
            title = sqlService.getContentMsg(ParseUtils.getDataSys(messageTemplate.getTitle(), params));
        }
        return title;
    }

    @Override
    public String getParsedContent(MessageTemplate messageTemplate, Map<String, Object> params) {
        String content = "";
        //判断内容取值模式
        if (messageTemplate.getContentMode().intValue() == Template.MODEL_TXT.getId()) {
            content = messageTemplate.getContent();
        } else if (messageTemplate.getContentMode().intValue() == Template.MODEL_PARAMS.getId()) {
            //参数置换
            content = ParseUtils.getDataSys(messageTemplate.getContent(), params);
        } else if (messageTemplate.getContentMode().intValue() == Template.MODEL_SQL.getId()) {
            //参数置换后进行sql转换
            content = sqlService.getContentMsg(ParseUtils.getDataSys(messageTemplate.getContent(), params));
        }
        return content;
    }

    /**
     * 获取权限按钮
     *
     * @param messageTemplates
     */
    private void getPermission(List<MessageTemplateVo> messageTemplates) {
        for (MessageTemplateVo messageTemplateVo : messageTemplates) {
            // 获取按钮
            List<TemplateButton> actions = new ArrayList<>();
            actions.add(TemplateButton.VIEW);
            actions.add(TemplateButton.UPDATE);
            actions.add(TemplateButton.PARAMS);
            actions.add(TemplateButton.RULES);
            actions.add(TemplateButton.CUSTOMIZE_PARAMS);
            actions.add(TemplateButton.DELETE);
            messageTemplateVo.setCanPerformActions(actions);
        }
    }
}

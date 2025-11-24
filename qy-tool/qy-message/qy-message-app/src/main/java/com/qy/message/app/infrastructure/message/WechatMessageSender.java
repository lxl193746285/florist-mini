package com.qy.message.app.infrastructure.message;

import com.qy.message.app.application.dto.PlatformDTO;
import com.qy.message.app.application.dto.SendWechatMessageDTO;
import com.qy.message.app.application.service.PlatformQueryService;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 微信消息发送器
 *
 * @author legendjw
 */
@Component
public class WechatMessageSender {
    private final ConcurrentMap<String, WxMpService> wxServiceCacheMap = new ConcurrentHashMap<>();
    private PlatformQueryService platformQueryService;

    public WechatMessageSender(PlatformQueryService platformQueryService) {
        this.platformQueryService = platformQueryService;
    }

    /**
     * 发送消息
     *
     * @param message
     */
    public void sendMessage(SendWechatMessageDTO message) throws WxErrorException {
        PlatformDTO platformDTO = platformQueryService.getPlatformById(message.getPlatformId());
        if (platformDTO == null) {
            throw new NotFoundException("未找到指定的消息平台");
        }
        String weixinAppId = platformDTO.getConfig().getWeixinAppId();
        String weixinAppSecret = platformDTO.getConfig().getWeixinAppSecret();
        if (StringUtils.isBlank(weixinAppId) || StringUtils.isBlank(weixinAppSecret)) {
            throw new ValidationException("发送微信消息微信配置错误");
        }

        WxMpService wxMpService = getWxMpService(weixinAppId, weixinAppSecret);
        if (StringUtils.isBlank(message.getOpenId())) {
            throw new ValidationException("发送微信消息用户openid不能为空");
        }
        List<WxMpTemplateData> templateData = new ArrayList<>();
        for (Map.Entry<String, Object> m : message.getData().entrySet()) {
            templateData.add(new WxMpTemplateData(m.getKey(), m.getValue().toString()));
        }
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(message.getOpenId())
                .templateId(message.getWechatTemplateId())
                .data(templateData)
                .url(message.getData().containsKey("url") ? message.getData().get("url").toString() : "")
                .build();
        wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
    }

    /**
     * 获取微信服务
     *
     * @param appId
     * @param appSecret
     * @return
     */
    private WxMpService getWxMpService(String appId, String appSecret) {
        String wxServiceKey = String.format("%s-%s", appId, appSecret);

        if (wxServiceCacheMap.containsKey(wxServiceKey)) {
            return wxServiceCacheMap.get(wxServiceKey);
        }

        WxMpDefaultConfigImpl wxMpConfigStorage = new WxMpDefaultConfigImpl();
        wxMpConfigStorage.setAppId(appId);
        wxMpConfigStorage.setSecret(appSecret);
        WxMpService wxService = new WxMpServiceImpl();
        wxService.setWxMpConfigStorage(wxMpConfigStorage);

        wxServiceCacheMap.put(wxServiceKey, wxService);

        return wxService;
    }
}
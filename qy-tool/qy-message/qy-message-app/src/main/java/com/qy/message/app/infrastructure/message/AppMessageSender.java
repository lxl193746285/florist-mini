package com.qy.message.app.infrastructure.message;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.qy.message.app.application.dto.PlatformDTO;
import com.qy.message.app.application.dto.SendAppMessageDTO;
import com.qy.message.app.application.service.PlatformQueryService;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * APP消息发送器
 *
 * @author legendjw
 */
@Component
public class AppMessageSender {
    private final ConcurrentMap<String, JPushClient> jpushCacheMap = new ConcurrentHashMap<>();
    private PlatformQueryService platformQueryService;

    public AppMessageSender(PlatformQueryService platformQueryService) {
        this.platformQueryService = platformQueryService;
    }

    /**
     * 发送消息
     *
     * @param message
     */
    public void sendMessage(SendAppMessageDTO message) throws APIConnectionException, APIRequestException {
        PlatformDTO platformDTO = platformQueryService.getPlatformById(message.getPlatformId());
        if (platformDTO == null) {
            throw new NotFoundException("未找到指定的消息平台");
        }
        String appPushKey = platformDTO.getConfig().getAppPushKey();
        String appPushSecret = platformDTO.getConfig().getAppPushSecret();
        if (StringUtils.isBlank(appPushKey) || StringUtils.isBlank(appPushSecret)) {
            throw new ValidationException("发送APP消息配置错误");
        }

        if (StringUtils.isBlank(message.getMobileId())) {
            throw new ValidationException("发送APP消息手机id不能为空");
        }

        JPushClient jPushClient = getJpushClient(appPushKey, appPushSecret);

        JsonObject object = new JsonObject();
        for (Map.Entry<String, String> entry : message.getData().entrySet()) {
            object.addProperty(entry.getKey(), entry.getValue());
        }
        PushPayload pushPayload = buildPushObjectWithRegistrationId(
                message.getMobileId(),
                message.getTitle(),
                message.getContent(),
                object
        );
        PushResult pushResult = jPushClient.sendPush(pushPayload);
        if (pushResult.getResponseCode() != 200) {
            throw new RuntimeException(String.format("app推送消息失败：%s", pushResult.error.getMessage()));
        }
    }

    /**
     * 建立以唯一设备标识符推送的对象
     *
     * @param registrationId     唯一设备标识
     * @param notification_alert 通知内容
     * @param notification_title 通知标题
     * @param extraParam         扩展字段
     * @return 返回推送对象
     */
    private static PushPayload buildPushObjectWithRegistrationId(String registrationId, String notification_alert, String notification_title, JsonObject extraParam) {
        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.all())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.registrationId(registrationId))
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(notification_alert)   //设置通知内容（必填）
                                .setTitle(notification_title)    //设置通知标题（可选）
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("extra_data", extraParam)
                                .build())

                        //指定当前推送的iOS通知
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(notification_alert)
                                //直接传alert
                                //此项是指定此推送的badge（应用角标）自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("sound.caf")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("extra_data", extraParam)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                //取消此注释，消息推送时ios将无法在锁屏情况接收
                                // .setContentAvailable(true)
                                .build())

                        //指定当前推送的winPhone通知
                        /*.addPlatformNotification(WinphoneNotification.newBuilder()
                              .setAlert(notification_alert)
                              //.setTitle(""))  //设置通知标题（可选）此标题将取代显示app名称的地方
                            .build())*/
                        .build())
                .build();
    }

    /**
     * 获取推送服务
     *
     * @param appPushKey
     * @param appPushSecret
     * @return
     */
    private JPushClient getJpushClient(String appPushKey, String appPushSecret) {
        String clientKey = String.format("%s-%s", appPushKey, appPushSecret);

        if (jpushCacheMap.containsKey(clientKey)) {
            return jpushCacheMap.get(clientKey);
        }

        JPushClient jPushClient = new JPushClient(appPushSecret, appPushKey);
        jpushCacheMap.put(clientKey, jPushClient);

        return jPushClient;
    }
}
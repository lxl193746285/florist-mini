package com.qy.verification.app.infrastructure.sender;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.qy.verification.app.domain.entity.VerificationCode;
import com.qy.verification.app.domain.sender.SendResult;
import com.qy.verification.app.domain.sender.SmsSender;
import com.qy.verification.app.domain.valueobject.Receiver;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 阿里云短信验证码发送器
 *
 * @author legendjw
 */
@Component
public class AliyunSmsSender implements SmsSender {
    private static final Logger logger = LoggerFactory.getLogger(AliyunSmsSender.class);
    @Value("${qy.sms.access-key-id}")
    private String accessKeyId;
    @Value("${qy.sms.access-key-secret}")
    private String accessKeySecret;
    @Value("${qy.sms.sign-name}")
    private String signName;
    @Value("${qy.sms.template-code}")
    private String templateCode;
    @Value("${qy.sms.code-name}")
    private String codeName;

    @Override
    public SendResult send(Receiver receiver, VerificationCode verificationCode) {
        // 设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        // 初始化ascClient需要的几个参数
        final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
        final String accessKeyId = this.accessKeyId;// 你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = this.accessKeySecret;// 你的accessKeySecret，参考本文档步骤2
        try {
            // 初始化ascClient,暂时不支持多region（请勿修改）
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            // 组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            // 使用post提交
            request.setMethod(MethodType.POST);
            // 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
            request.setPhoneNumbers(receiver.getAddress());
            // 必填:短信签名-可在短信控制台中找到
            request.setSignName(this.signName);
            // 必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(this.templateCode);
            // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            // 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            String code = verificationCode.getCode();
            if(StringUtils.isNotBlank(this.codeName)){
                request.setTemplateParam("{\""+this.codeName+"\":\"" + code + "\"}");
            }else{
                request.setTemplateParam("{\"vercode\":\"" + code + "\"}");
            }
            // 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
            // request.setSmsUpExtendCode("90997");
            // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            // request.setOutId("yourOutId");
            // 请求失败这里会抛ClientException异常
            SendSmsResponse sendSmsResponse;
            sendSmsResponse = acsClient.getAcsResponse(request);
            Map<String, String> errorMessages = new HashMap<>();
            errorMessages.put("isv.OUT_OF_SERVICE", "业务停机");
            errorMessages.put("isv.ACCOUNT_ABNORMAL", "账户异常");
            errorMessages.put("isv.SYSTEM_ERROR", "系统错误");
            errorMessages.put("isv.MOBILE_NUMBER_ILLEGAL", "手机号格式不正确");
            errorMessages.put("isv.MOBILE_COUNT_OVER_LIMIT", "手机号码数量超过限制");
            errorMessages.put("isv.BUSINESS_LIMIT_CONTROL", "业务限流");
            errorMessages.put("isv.BLACK_KEY_CONTROL_LIMIT", "黑名单管控");
            errorMessages.put("isv.AMOUNT_NOT_ENOUGH", "账户余额不足");
            errorMessages.put("isv.SMS_TEMPLATE_ILLEGAL", "短信模板错误");

            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                return new SendResult(true, receiver, verificationCode);
            }
            String errorMessage = sendSmsResponse.getCode() != null && errorMessages.containsKey(sendSmsResponse.getCode()) ? errorMessages.get(sendSmsResponse.getCode()) : "验证码发送失败";
            return new SendResult(false, errorMessage, receiver, verificationCode);
        } catch (Exception e) {
            logger.error("短信验证码发送失败", e);
            return new SendResult(false, "验证码发送失败", receiver, verificationCode);
        }
    }
}
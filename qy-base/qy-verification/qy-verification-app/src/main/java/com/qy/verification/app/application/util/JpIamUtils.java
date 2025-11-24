package com.qy.verification.app.application.util;

import com.alibaba.fastjson.JSONObject;
import com.qy.rest.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class JpIamUtils {

    private static String clientId = "jinzhangtong";

    private static String url = "https://iam.jpddc.com/sms/";


    public static String sendSms(String mobile, String content, String code) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phoneNum", mobile);
        jsonObject.put("message", content);
        jsonObject.put("smsCode", code);
        jsonObject.put("client_id", clientId);
        return HttpUtils.postJson(url + "send", jsonObject, null);
    }

    public static String validateSms(String mobile, String content, String code) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phoneNum", mobile);
        jsonObject.put("yzm", content);
        jsonObject.put("smsCode", code);
        jsonObject.put("client_id", clientId);
        return HttpUtils.postJson(url + "yzm_validate", jsonObject, null);
    }

}

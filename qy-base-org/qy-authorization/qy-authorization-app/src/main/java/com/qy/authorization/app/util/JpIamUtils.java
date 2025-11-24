package com.qy.authorization.app.util;

import com.alibaba.fastjson.JSONObject;
import com.qy.rest.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class JpIamUtils {

    @Value("${jp.iam.client-id}")
    private String clientId;

    @Value("${jp.iam.client-secret}")
    private String clientSecret;

    @Value("${jp.iam.scope}")
    private String scope;

    @Value("${jp.iam.url}")
    private String url;

    @Value("${jp.iam.redirect-uri}")
    private String redirectUri;

    /**
     * 获取客户端token
     */
    public String getClientToken() throws Exception {
        String requestUrl = url + "/oauth2/token";
        Map<String, String> formDataText = new HashMap<>();
        formDataText.put("grant_type", "client_credentials");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString((clientId + ":" + clientSecret).getBytes()));
        String respText = HttpUtils.postFormData(requestUrl, formDataText, headers, null);
        String accessToken = "";
        int expiresIn = 0;
        if (respText != null && !StringUtils.isEmpty(respText)) {
            JSONObject dic = JSONObject.parseObject(respText);
            if (dic.containsKey("access_token")) {
                accessToken = dic.getString("access_token");//令牌
                expiresIn = Integer.parseInt(dic.getString("expires_in")); //过期时间
            }
        }
        if (StringUtils.isEmpty(accessToken)) {
            throw new ValidationException("获取客户端token失败");
        }
        return accessToken;
    }

    /**
     * 注册用户
     *
     * @param username 登录账号
     * @param password 密码
     * @param nickName 昵称
     * @param mobile   手机号
     * @return
     * @throws Exception
     */
    public Boolean userRegister(String username, String password, String nickName, String mobile) throws Exception {
        String requestUrl = url + "/user/signup";
        JSONObject body = new JSONObject();
        body.put("username", username);
        body.put("password", password);
        body.put("nickName", nickName);
        body.put("mobile", mobile);
        String respText = HttpUtils.postJson(requestUrl, body, null);
        if (respText != null && !StringUtils.isEmpty(respText)) {
            JSONObject dic = JSONObject.parseObject(respText);
            return dic.containsKey("success");
        }
        return false;
    }

    /**
     * 获取token
     *
     * @param username 用户名
     * @param password 密码
     * @return String 授权码
     * @throws Exception
     */
    public String userLogin(String username, String password) throws Exception {
        String requestUrl = url + "/login";
        Map<String, String> formData = new HashMap<>();
        formData.put("username", username);
        formData.put("password", password);
        formData.put("redirect_uri", redirectUri);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString((clientId + ":" + clientSecret).getBytes()));
        String respText = HttpUtils.postFormData(requestUrl, formData, headers, null);
        if (respText != null && !StringUtils.isEmpty(respText)) {
            JSONObject dic = JSONObject.parseObject(respText);
            if (dic.containsKey("access_code")) {
                return dic.getString("access_code");
            }
        }
        return null;
    }

    /**
     * 获取token
     *
     * @param code 授权码
     * @return JSONObject access_token令牌,refresh_token刷新令牌,token_type令牌类型,expires_in令牌过期时间（秒）
     * @throws Exception
     */
    public JSONObject getAccessTokeByCode(String code) throws Exception {
        String requestUrl = url + "/oauth2/token";
        Map<String, String> formData = new HashMap<>();
        formData.put("grant_type", "authorization_code");
        formData.put("code", code);
        formData.put("redirect_uri", redirectUri);
        formData.put("scope", scope);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString((clientId + ":" + clientSecret).getBytes()));
        String respText = HttpUtils.postFormData(requestUrl, formData, headers, null);
        if (respText != null && !StringUtils.isEmpty(respText)) {
            JSONObject dic = JSONObject.parseObject(respText);
            if (dic.containsKey("access_token")) {
                return dic;
            }
        }
        return null;
    }

    /**
     * 刷新token
     *
     * @param refreshAccessToken 刷新token
     * @return JSONObject access_token令牌,refresh_token刷新令牌,token_type令牌类型,expires_in令牌过期时间（秒）
     * @throws Exception
     */
    public JSONObject refreshAccessToken(String refreshAccessToken) throws Exception {
        String requestUrl = url + "/oauth2/token";
        Map<String, String> formData = new HashMap<>();
        formData.put("grant_type", "refresh_token");
        formData.put("refresh_token", refreshAccessToken);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString((clientId + ":" + clientSecret).getBytes()));
        String respText = HttpUtils.postFormData(requestUrl, formData, headers, null);
        if (respText != null && !StringUtils.isEmpty(respText)) {
            JSONObject dic = JSONObject.parseObject(respText);
            if (dic.containsKey("access_token")) {
                return dic;
            }
        }
        return null;
    }

    /**
     * 验证token是否有效
     *
     * @param accessToken token字符串
     * @return
     * @throws Exception
     */
    public Boolean validAccessToke(String accessToken) throws Exception {
        String requestUrl = url + "/validate/token";
        Map<String, String> formData = new HashMap<>();
        formData.put("grant_type", "access_token");
        formData.put("access_token", accessToken);
        String respText = HttpUtils.postFormData(requestUrl, formData, null, null);
        if (respText != null && !StringUtils.isEmpty(respText)) {
            JSONObject dic = JSONObject.parseObject(respText);
            return dic.containsKey("success");
        }
        return false;
    }


    /**
     * 更新用户在职状态
     *
     * @param username 账户名
     * @param status   true:在职，false:离职
     * @return
     * @throws Exception
     */
    public Boolean updateUserJobStatus(String username, Boolean status) throws Exception {
        String requestUrl = url + "/update/enabled";
        JSONObject body = new JSONObject();
        body.put("username", username);
        body.put("enabled", status);
        String respText = HttpUtils.postJson(requestUrl, body, null);
        if (respText != null && !StringUtils.isEmpty(respText)) {
            JSONObject dic = JSONObject.parseObject(respText);
            return dic.containsKey("success");
        }
        return false;
    }


    /**
     * 更新用户在职状态
     *
     * @param oldPassword   原密码
     * @param newPassword   新密码
     * @return
     * @throws Exception
     */
    public Boolean modifyPassword(String oldPassword, String newPassword, String accessToken) throws Exception {
        String requestUrl = url + "/user/update/password";
        JSONObject body = new JSONObject();
        body.put("oldPassword", oldPassword);
        body.put("newPassword", newPassword);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        String respText = HttpUtils.postJson(requestUrl, body, null);
        if (respText != null && !StringUtils.isEmpty(respText)) {
            JSONObject dic = JSONObject.parseObject(respText);
            return dic.containsKey("success");
        }
        return false;
    }


}

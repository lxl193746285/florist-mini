package com.qy.member.app.application.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaRedisBetterConfigImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaRedisConfigImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qy.attachment.api.client.AttachmentClient;
import com.qy.authorization.api.client.AuthorizationClient;
import com.qy.authorization.api.command.GenerateAccessTokenCommand;
import com.qy.member.app.application.command.CreateMemberCommand;
import com.qy.member.app.application.command.LoginByMiniCommand;
import com.qy.member.app.application.command.MiniQrcodeCommand;
import com.qy.member.app.application.command.OpenMemberCommand;
import com.qy.member.app.application.dto.*;
import com.qy.member.app.application.query.*;
import com.qy.member.app.application.repository.MemberDataRepository;
import com.qy.member.app.application.repository.MemberSystemDataRepository;
import com.qy.member.app.application.repository.MemberSystemWeixinAppDataRepository;
import com.qy.member.app.application.repository.MemberWeixinDataRepository;
import com.qy.member.app.application.service.MemberCommandService;
import com.qy.member.app.application.service.WeixinService;
import com.qy.member.app.domain.entity.AccountWeixin;
import com.qy.member.app.domain.entity.MemberAccount;
import com.qy.member.app.domain.enums.WeixinAppType;
import com.qy.member.app.domain.enums.WeixinLoginStatus;
import com.qy.member.app.domain.repository.AccountWexinDomainRepository;
import com.qy.member.app.domain.valueobject.*;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemWeixinAppDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberWeixinDO;
import com.qy.rbac.api.client.ClientClient;
import com.qy.rbac.api.dto.ClientDTO;
import com.qy.rest.exception.BusinessException;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.MemberSystemSessionContext;
import com.qy.util.MdzwUtils;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.redis.RedisTemplateWxRedisOps;
import me.chanjar.weixin.common.service.WxService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import me.chanjar.weixin.mp.config.impl.WxMpRedisConfigImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import static cn.binarywang.wx.miniapp.constant.WxMaApiUrlConstants.Qrcode.GET_WXACODE_UNLIMIT_URL;

/**
 * 微信服务实现
 *
 * @author legendjw
 */
@Service
public class WeixinServiceImpl implements WeixinService {
    private static final Logger logger = LoggerFactory.getLogger(WeixinServiceImpl.class);
    private final ConcurrentMap<String, WxServiceWithVersionDTO> wxServiceCacheMap = new ConcurrentHashMap<>();
    @Value("${qy.qrcode.path}")
    private String qrcodePath;
    @Value("${qy.qrcode.url}")
    private String qrcodeUrl;

    private MemberSystemDataRepository memberSystemDataRepository;
    private MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository;
    private MemberWeixinDataRepository memberWeixinDataRepository;
    private AuthorizationClient authorizationClient;
    private MemberDataRepository memberDataRepository;
    private MemberCommandService memberCommandService;
    private AccountWexinDomainRepository accountWexinDomainRepository;
    private ClientClient clientClient;
    private AttachmentClient attachmentClient;
    private StringRedisTemplate redisTemplate;


    public WeixinServiceImpl(MemberSystemDataRepository memberSystemDataRepository,
                             MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository,
                             MemberWeixinDataRepository memberWeixinDataRepository,
                             AuthorizationClient authorizationClient,
                             MemberDataRepository memberDataRepository,
                             MemberCommandService memberCommandService,
                             AccountWexinDomainRepository accountWexinDomainRepository,
                             ClientClient clientClient,
                             AttachmentClient attachmentClient,
                             StringRedisTemplate redisTemplate) {
        this.memberSystemDataRepository = memberSystemDataRepository;
        this.memberSystemWeixinAppDataRepository = memberSystemWeixinAppDataRepository;
        this.memberWeixinDataRepository = memberWeixinDataRepository;
        this.authorizationClient = authorizationClient;
        this.memberDataRepository = memberDataRepository;
        this.memberCommandService = memberCommandService;
        this.accountWexinDomainRepository = accountWexinDomainRepository;
        this.clientClient = clientClient;
        this.attachmentClient = attachmentClient;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public WxService getWxService(SystemIdAndWeixinAppIdQuery query) {
        MemberSystemWeixinAppDO weixinAppDO = getMemberSystemWeixinApp(query.getSystemId(), query.getAppId());
        LocalDateTime version = weixinAppDO.getUpdateTime() != null ? weixinAppDO.getUpdateTime() : weixinAppDO.getCreateTime();

        String wxServiceKey = String.format("%s-%s", weixinAppDO.getSystemId(), weixinAppDO.getAppId());

        if (wxServiceCacheMap.containsKey(wxServiceKey) && wxServiceCacheMap.get(wxServiceKey).getVersion().isEqual(version)) {
            return wxServiceCacheMap.get(wxServiceKey).getWxService();
        }

        if (weixinAppDO.getTypeId().equals(WeixinAppType.MP.getId()) || weixinAppDO.getTypeId().equals(WeixinAppType.OPEN.getId())) {
            WxMpRedisConfigImpl wxMpConfigStorage = new WxMpRedisConfigImpl(new RedisTemplateWxRedisOps(redisTemplate), weixinAppDO.getAppId());
            wxMpConfigStorage.setAppId(weixinAppDO.getAppId());
            wxMpConfigStorage.setSecret(weixinAppDO.getAppSecret());
            WxMpService wxService = new WxMpServiceImpl();
            wxService.setWxMpConfigStorage(wxMpConfigStorage);

            wxServiceCacheMap.put(wxServiceKey, WxServiceWithVersionDTO.builder()
                    .version(version)
                    .wxService(wxService)
                    .build());
            return wxService;
        } else if (weixinAppDO.getTypeId().equals(WeixinAppType.MINI.getId())) {
            WxMaRedisBetterConfigImpl config = new WxMaRedisBetterConfigImpl(new RedisTemplateWxRedisOps(redisTemplate), weixinAppDO.getAppId());
            config.setAppid(weixinAppDO.getAppId());
            config.setSecret(weixinAppDO.getAppSecret());
            WxMaService wxService = new WxMaServiceImpl();
            wxService.setWxMaConfig(config);

            wxServiceCacheMap.put(wxServiceKey, WxServiceWithVersionDTO.builder()
                    .version(version)
                    .wxService(wxService)
                    .build());
            return wxService;
        }
        return null;
    }

    @Override
    public String getAuthorizationUrl(WeixinAppAuthorizationUrlQuery query) {
        MemberSystemWeixinAppDO weixinAppDO = memberSystemWeixinAppDataRepository.findByClientId(query.getClientId());
        SystemIdAndWeixinAppIdQuery systemIdAndWeixinAppIdQuery = new SystemIdAndWeixinAppIdQuery(weixinAppDO.getSystemId().toString(), weixinAppDO.getAppId());

        WxMpService wxMpService = (WxMpService) getWxService(systemIdAndWeixinAppIdQuery);
        return wxMpService.getOAuth2Service().buildAuthorizationUrl(query.getUrl(), WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
    }

    @Override
    public WxAuthUser getWxUserInfo(WeixinAppUserQuery query) {
        SystemIdAndWeixinAppIdQuery systemIdAndWeixinAppIdQuery = new SystemIdAndWeixinAppIdQuery(query.getSystemId(), query.getAppId());
        WxService wxService = getWxService(systemIdAndWeixinAppIdQuery);
        MemberSystemWeixinAppDO weixinAppDO = getMemberSystemWeixinApp(query.getSystemId(), query.getAppId());
        try {
            //微信开放平台
            if (weixinAppDO.getTypeId().equals(WeixinAppType.OPEN.getId())) {
                WxMpService wxMpService = (WxMpService) wxService;
                WxOAuth2AccessToken wxOAuth2AccessToken = wxMpService.getOAuth2Service().getAccessToken(query.getCode());
                WxOAuth2UserInfo userInfo = wxMpService.getOAuth2Service().getUserInfo(wxOAuth2AccessToken, null);
                WxAuthUser wxAuthUser = WxAuthUser.builder()
                        .openId(userInfo.getOpenid())
                        .unionId(userInfo.getUnionId())
                        .country(userInfo.getCountry())
                        .province(userInfo.getProvince())
                        .city(userInfo.getCity())
                        .nickname(userInfo.getNickname())
                        .headImgUrl(userInfo.getHeadImgUrl())
                        .sex(userInfo.getSex())
                        .build();
                return wxAuthUser;
            }
            //微信公众号
            else if (weixinAppDO.getTypeId().equals(WeixinAppType.MP.getId())) {
                WxMpService wxMpService = (WxMpService) wxService;
                WxOAuth2AccessToken wxOAuth2AccessToken = wxMpService.getOAuth2Service().getAccessToken(query.getCode());
                WxOAuth2UserInfo userInfo = wxMpService.getOAuth2Service().getUserInfo(wxOAuth2AccessToken, null);
                //公众号需要单独获取用户信息来查看是否绑定了公众号
                WxMpUser mpUserInfo = wxMpService.getUserService().userInfo(userInfo.getOpenid());
                WxAuthUser wxAuthUser = WxAuthUser.builder()
                        .openId(userInfo.getOpenid())
                        .unionId(userInfo.getUnionId())
                        .country(userInfo.getCountry())
                        .province(userInfo.getProvince())
                        .city(userInfo.getCity())
                        .nickname(userInfo.getNickname())
                        .headImgUrl(userInfo.getHeadImgUrl())
                        .sex(userInfo.getSex())
                        .subscribe(mpUserInfo.getSubscribe())
                        .build();
                return wxAuthUser;
            }
            //微信小程序
            else if (weixinAppDO.getTypeId().equals(WeixinAppType.MINI.getId())) {
                WxMaService wxMaService = (WxMaService) wxService;
                WxMaJscode2SessionResult sessionResult = wxMaService.jsCode2SessionInfo(query.getCode());
                WxAuthUser wxAuthUser = WxAuthUser.builder()
                        .openId(sessionResult.getOpenid())
                        .unionId(sessionResult.getUnionid())
                        .sessionKey(sessionResult.getSessionKey())
                        .build();
                return wxAuthUser;
            }
            return null;
        } catch (WxErrorException e) {
            logger.error("会员系统获取微信用户信息出错", e);
            return null;
        }
    }

    @Override
    public WxJsapiSignature getJsapiSignature(WeixinAppJsapiQuery query) {
        SystemIdAndWeixinAppIdQuery systemIdAndWeixinAppIdQuery = new SystemIdAndWeixinAppIdQuery(query.getSystemId(), query.getAppId());
        WxMpService wxMpService = (WxMpService) getWxService(systemIdAndWeixinAppIdQuery);
        try {
            return wxMpService.createJsapiSignature(query.getUrl());
        } catch (WxErrorException e) {
            logger.error("会员系统获取微信jsapi信息出错", e);
            return null;
        }
    }

    @Override
    public WeixinPhoneDTO getMiniWxUserPhone(WeixinMiniUserPhoneQuery query) {
        SystemIdAndWeixinAppIdQuery systemIdAndWeixinAppIdQuery = new SystemIdAndWeixinAppIdQuery(null, query.getWxAppId());
        WxService wxService = getWxService(systemIdAndWeixinAppIdQuery);
        WxMaService wxMaService = (WxMaService) wxService;
        WeixinPhoneDTO dto = new WeixinPhoneDTO();
        try {
            Map<String, String> param = new HashMap();
            param.put("code", query.getCode());
            String data = JSON.toJSONString(param);
            String result = wxMaService.post("https://api.weixin.qq.com/wxa/business/getuserphonenumber", data);
            Map map = JSONObject.parseObject(result);
            if ("ok".equals(map.get("errmsg"))) {
                if (map.get("phone_info") != null) {
                    Map phone = JSONObject.parseObject(map.get("phone_info").toString());
                    if (phone.get("purePhoneNumber") != null) {
                        dto.setPhone(phone.get("purePhoneNumber").toString());
                    }
                }
            } else {
                throw new ValidationException("获取失败：" + map.get("errmsg"));
            }
        } catch (WxErrorException e) {
            logger.error("获取手机号出错", e);
            throw new ValidationException("获取失败");
        }
        return dto;
    }

    @Override
    public WeixinLoginDTO miniLogin(LoginByMiniCommand command) {
        //查找会员系统
        ClientDTO clientDTO = clientClient.getClient(command.getClientId());
        if (clientDTO == null) {
            throw new ValidationException("未找到指定的客户端");
        }

//        MemberSystemDO memberSystemDO = memberSystemDataRepository.findByAppId(command.getAppId());
//        if (memberSystemDO == null) {
//            throw new ValidationException("非法的应用系统");
//        }
        MemberSystemWeixinAppDO memberSystemWeixinAppDO = memberSystemWeixinAppDataRepository.findBySystemIdAndAppTypeAnClientId(clientDTO.getSystemId(),
                WeixinAppType.MINI.getId(), command.getClientId());
        if (memberSystemWeixinAppDO == null) {
            throw new ValidationException("非法的微信应用");
        }
        SystemIdAndWeixinAppIdQuery systemIdAndWeixinAppIdQuery = new SystemIdAndWeixinAppIdQuery(clientDTO.getSystemId().toString(), memberSystemWeixinAppDO.getAppId());
        WxService wxService = getWxService(systemIdAndWeixinAppIdQuery);
        WxMaService wxMaService = (WxMaService) wxService;
        try {
            WeixinLoginDTO loginDTO = new WeixinLoginDTO();
            WxMaJscode2SessionResult result = wxMaService.getUserService().getSessionInfo(command.getCode());
            MemberSystemWeixinAppDO weixinAppDO = getMemberSystemWeixinApp(clientDTO.getSystemId().toString(), memberSystemWeixinAppDO.getAppId());
            MemberWeixinDO memberWeixinDO = null;
            if (command.getOrganizationId() == null) {
                memberWeixinDO = memberWeixinDataRepository.findByMemberSystemAndOpenId(weixinAppDO.getSystemId(), result.getOpenid());
            } else {
                memberWeixinDO = memberWeixinDataRepository.findByMemberSystemAndOpenIdAndOrgId(command.getOrganizationId(), weixinAppDO.getSystemId(), result.getOpenid());
            }

            if (memberWeixinDO != null) {
                GenerateAccessTokenCommand accessTokenCommand = new GenerateAccessTokenCommand();
                accessTokenCommand.setClientId(command.getClientId());
                accessTokenCommand.setUserId(memberWeixinDO.getAccountId().toString());
                accessTokenCommand.setContextId(MemberSystemSessionContext.contextId);
                Map<String, String> extraData = new HashMap<>();
                extraData.put("member_id", memberWeixinDO.getMemberId().toString());
                extraData.put("organization_id", command.getOrganizationId().toString());
                extraData.put("system_id", memberWeixinDO.getSystemId().toString());
                extraData.put("app_id", command.getAppId());
                accessTokenCommand.setExtraData(extraData);
                loginDTO.setAccessToken(authorizationClient.generateAccessToken(accessTokenCommand));
                loginDTO.setMemberId(memberWeixinDO.getMemberId());
                loginDTO.setStatus(WeixinLoginStatus.SUCCESS.getId());
                loginDTO.setOpenId(result.getOpenid());
                return loginDTO;
            }

            if (StringUtils.isEmpty(command.getPhone())) {
                loginDTO.setStatus(WeixinLoginStatus.NOT_BIND.getId());
                return loginDTO;
            }

            MemberDO memberDO = memberDataRepository.findByPhoneSystem(command.getPhone(), weixinAppDO.getSystemId(), command.getOrganizationId());
            MemberIdDTO dto = new MemberIdDTO();
            if (memberDO == null) {
                OpenMemberCommand openMemberCommand = new OpenMemberCommand();
                openMemberCommand.setName(command.getPhone());
                openMemberCommand.setPhone(command.getPhone());
                openMemberCommand.setPasswordHash("12345678");
                openMemberCommand.setAuditStatus(1);
                openMemberCommand.setSystemId(weixinAppDO.getSystemId().toString());
                openMemberCommand.setOrganizationId(command.getOrganizationId().toString());
                openMemberCommand.setMemberTypeId(command.getMemberType());
                dto = memberCommandService.openMember(openMemberCommand);
            } else {
                dto.setAccountId(memberDO.getAccountId());
                dto.setId(memberDO.getId());
            }
            GenerateAccessTokenCommand accessTokenCommand = new GenerateAccessTokenCommand();
            accessTokenCommand.setClientId(command.getClientId());
            accessTokenCommand.setUserId(dto.getAccountId().toString());
            accessTokenCommand.setContextId(MemberSystemSessionContext.contextId);
            Map<String, String> extraData = new HashMap<>();
            extraData.put("member_id", dto.getId().toString());
            extraData.put("organization_id", command.getOrganizationId().toString());
            extraData.put("system_id", weixinAppDO.getSystemId().toString());
            extraData.put("app_id", command.getAppId());
            accessTokenCommand.setExtraData(extraData);
            loginDTO.setAccessToken(authorizationClient.generateAccessToken(accessTokenCommand));
            loginDTO.setMemberId(dto.getId());
            loginDTO.setStatus(WeixinLoginStatus.SUCCESS.getId());

            MemberAccount account = MemberAccount.builder()
                    .accountId(new AccountId(dto.getAccountId()))
                    .memberId(new MemberId(dto.getId()))
                    .memberSystemId(new MemberSystemId(weixinAppDO.getSystemId()))
                    .organizationId(new OrganizationId(command.getOrganizationId()))
                    .build();
            WxAuthUser wxAuthUser = WxAuthUser.builder()
                    .openId(result.getOpenid())
                    .unionId(result.getUnionid())
                    .build();
            AccountWeixin accountWeixin = new AccountWeixin(weixinAppDO.getAppId(), account, wxAuthUser);
            accountWexinDomainRepository.save(accountWeixin);
            loginDTO.setOpenId(result.getOpenid());
            return loginDTO;
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw new ValidationException("登录失败：" + e.getMessage());
        }
    }

    @Override
    public WeixinMiniQrcodeDTO getMiniQrcode(MiniQrcodeCommand command) {
        //查找会员系统
        ClientDTO clientDTO = clientClient.getClient(command.getClientId());
        if (clientDTO == null) {
            throw new ValidationException("未找到指定的客户端");
        }

//        MemberSystemDO memberSystemDO = memberSystemDataRepository.findByAppId(command.getAppId());
//        if (memberSystemDO == null) {
//            throw new ValidationException("非法的应用系统");
//        }
        MemberSystemWeixinAppDO memberSystemWeixinAppDO = memberSystemWeixinAppDataRepository.findBySystemIdAndAppTypeAnClientId(clientDTO.getSystemId(),
                WeixinAppType.MINI.getId(), command.getClientId());
        if (memberSystemWeixinAppDO == null) {
            throw new ValidationException("非法的微信应用");
        }

        SystemIdAndWeixinAppIdQuery systemIdAndWeixinAppIdQuery = new SystemIdAndWeixinAppIdQuery(memberSystemWeixinAppDO.getSystemId().toString(),
                memberSystemWeixinAppDO.getAppId());
        WxMaService wxService = (WxMaService) getWxService(systemIdAndWeixinAppIdQuery);
        try {
            String miniUnlimitUrl = GET_WXACODE_UNLIMIT_URL + "?access_token=" + wxService.getAccessToken();
            String base64Image = MdzwUtils.getEw(command.getScene(), miniUnlimitUrl, command.getPage(), command.getEnvVersion(), command.getWidth());
            String name = UUID.randomUUID().toString() + ".png";
            String newFileName = qrcodePath + "/" + name;
            if (!GenerateImage(base64Image, newFileName)) {
                throw new ValidationException("获取失败");
            }
            MultipartFile file = new MockMultipartFile(name, new FileInputStream(newFileName));

            WeixinMiniQrcodeDTO qrcodeDTO = new WeixinMiniQrcodeDTO();
            qrcodeDTO.setQrcode(attachmentClient.publicOssUploadFile(file, "upload/qrcode/" + name, "image/png"));
            return qrcodeDTO;
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw new ValidationException("获取失败：" + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean GenerateImage(String base64str, String savepath) {
        //对字节数组字符串进行Base64解码并生成图片
        if (base64str == null) //图像数据为空
            return false;
        // System.out.println("开始解码");
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(base64str);
            //  System.out.println("解码完成");
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            // System.out.println("开始生成图片");
            //生成jpeg图片
            OutputStream out = new FileOutputStream(savepath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取会员系统
     *
     * @param systemId
     * @return
     */
    private MemberSystemDO getMemberSystem(String systemId) {
        MemberSystemDO memberSystemDO = memberSystemDataRepository.findBySystemId(systemId);
        if (memberSystemDO == null) {
            throw new BusinessException("非法的会员系统");
        }
        return memberSystemDO;
    }

    /**
     * 通过会员系统id和微信APPID获取会员系统微信应用
     *
     * @param systemId
     * @param appId
     * @return
     */
    private MemberSystemWeixinAppDO getMemberSystemWeixinApp(String systemId, String appId) {
        MemberSystemWeixinAppDO weixinAppDO = memberSystemWeixinAppDataRepository.findBySystemIdAndAppId(systemId != null ? Long.valueOf(systemId) : null, appId);
        if (weixinAppDO == null) {
            throw new BusinessException("非法的微信应用");
        }
        return weixinAppDO;
    }
}
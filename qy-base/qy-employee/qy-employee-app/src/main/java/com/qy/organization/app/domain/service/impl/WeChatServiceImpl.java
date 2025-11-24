package com.qy.organization.app.domain.service.impl;

import com.qy.authorization.api.client.AuthorizationClient;
import com.qy.authorization.api.command.GenerateAccessTokenCommand;
import com.qy.authorization.api.dto.AccessTokenDTO;
import com.qy.identity.api.client.UserClient;
import com.qy.member.api.dto.MemberDTO;
import com.qy.organization.app.application.dto.EmployeeBasicDTO;
import com.qy.organization.app.domain.entity.ResDto;
import com.qy.organization.app.domain.entity.UserDetailDTO;
import com.qy.organization.app.domain.entity.UserWeiXin;
import com.qy.organization.app.domain.enums.WechatUserStatus;
import com.qy.organization.app.domain.service.WeChatService;
import com.qy.organization.app.infrastructure.persistence.mybatis.mapper.UserWeiXinMapper;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.MemberSystemSessionContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeChatServiceImpl implements WeChatService {
//    @Value("${qy.wechat.appid}")
//    private String AppId;
//    @Value("${qy.wechat.appsecret}")
//    private String AppSecret;
//    @Value("${qy.wechat.appid}")
    private static final String AppId = "wx2537a5e18d63ed6c";
    private static final String AppSecret = "2955a9ab089c57d437f68f25ed110d95";
//    @Value("${qy.wechat.appsecret}")
//    private String AppSecret;
    @Autowired
    private UserClient userClient;
    @Autowired
    private UserWeiXinMapper userWeiXinMapper;
    @Autowired
    private AuthorizationClient authorizationClient;


    private WxMpService wxMpService;

    @Override
    public WxMpService getWxMpService() {
        if (wxMpService != null) {
            return wxMpService;
        }
        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
        config.setAppId(AppId);
        config.setSecret(AppSecret);
        WxMpService wxService = new WxMpServiceImpl();
        wxService.setWxMpConfigStorage(config);
        this.wxMpService = wxService;
        return this.wxMpService;
    }

    @Override
    public ResDto getUserInfo(String code) {

        ResDto resDto = new ResDto();
        WxMpUser wxMpUser = getWxMpUser(code);
        return isBind(resDto,wxMpUser);

    }

    public WxMpUser getWxMpUser(String code) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
        WxMpUser wxMpUser = null;
        try {
            //获取token
            wxMpOAuth2AccessToken = this.getWxMpService().oauth2getAccessToken(code);
            //获取微信用户信息
            wxMpUser = this.getWxMpService().getUserService().userInfo(wxMpOAuth2AccessToken.getOpenId());
        } catch (WxErrorException e) {
            throw new ValidationException("非法code");
        }
        return wxMpUser;
    }

    private ResDto isBind(ResDto resDto,WxMpUser wxMpUser){
        //是否关注公众号
        if(wxMpUser != null){
            //已关注
            if(wxMpUser.getSubscribe() != null) {
                if (wxMpUser.getSubscribe()) {
                    UserDetailDTO user = getUserByOpenid(wxMpUser.getOpenId());
                    //已绑定
                    if (user != null) {
                        if (user.getId() != null) {
                            //获取员工信息
                            EmployeeBasicDTO employeeBasicDTO = userWeiXinMapper.getEmployeeInfo(user.getId());

                            if (employeeBasicDTO != null && employeeBasicDTO.getJobStatusId() != null && employeeBasicDTO.getJobStatusId() == 0) {
                                //调用解绑操作
                                LambdaQueryWrapper<UserWeiXin> queryWrapper = new LambdaQueryWrapper<>();
                                queryWrapper.eq(UserWeiXin::getUserId, user.getId())
                                        .eq(UserWeiXin::getOpenid, wxMpUser.getOpenId());
                                UserWeiXin userWeiXin = userWeiXinMapper.selectOne(queryWrapper);

                                if (userWeiXin == null) {
                                    throw new ValidationException("用户未绑定微信，无法解绑");
                                }
                                LambdaQueryWrapper<UserWeiXin> deleteMapper = new LambdaQueryWrapper<>();
                                deleteMapper.eq(UserWeiXin::getUserId, user.getId())
                                        .eq(UserWeiXin::getOpenid, wxMpUser.getOpenId());
                                userWeiXinMapper.delete(deleteMapper);

                                throw new ValidationException("员工已离职！");
                            }
                        }


                        resDto.setStatus(WechatUserStatus.BIND.getId());
                        resDto.setMessage(WechatUserStatus.BIND.getName());
                        resDto.setWxMpUser(wxMpUser);
                        resDto.setUser(user);
                        //获取token
//                        AccessTokenDTO accessTokenDTO = userClient.getAccessToken("web", user.getId());
//                        resDto.setAccessTokenDTO(accessTokenDTO);

                        AccessTokenDTO accessTokenDTO1 = generateAccessToken("web", user.getId());
                        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
                        BeanUtils.copyProperties(accessTokenDTO1, accessTokenDTO);
                        resDto.setAccessTokenDTO(accessTokenDTO);
                        return resDto;
                    } else {
                        resDto.setStatus(WechatUserStatus.UNBOUND.getId());
                        resDto.setMessage(WechatUserStatus.UNBOUND.getName());
                        resDto.setWxMpUser(wxMpUser);
                        return resDto;
                    }
                }
            }
            //未关注
            resDto.setStatus(WechatUserStatus.NOT_CONCERNED.getId());
            resDto.setMessage(WechatUserStatus.NOT_CONCERNED.getName());
        }
        resDto.setStatus(WechatUserStatus.NOT_FOUND_USER.getId());
        resDto.setMessage(WechatUserStatus.NOT_FOUND_USER.getName());
        return resDto;
    }

    public AccessTokenDTO generateAccessToken(String clientId, Long accountId) {
        //获取需要的参数(根据accountId获取member数据)
        MemberDTO memberDTO = userWeiXinMapper.getMemberByAccountId(accountId);
        if (memberDTO == null) {
            throw new ValidationException("用户不存在！");
        }
        GenerateAccessTokenCommand userAccessTokenCommand = new GenerateAccessTokenCommand();
        userAccessTokenCommand.setContextId(MemberSystemSessionContext.contextId);
        userAccessTokenCommand.setClientId(clientId);
        userAccessTokenCommand.setUserId(accountId.toString());
        Map<String, String> extraData = new HashMap<>();
        extraData.put("member_id", memberDTO.getId().toString());
        extraData.put("organization_id", memberDTO.getOrganizationId().toString());
        extraData.put("system_id", "1");
        userAccessTokenCommand.setExtraData(extraData);
        return authorizationClient.generateAccessToken(userAccessTokenCommand);
    }

    private com.qy.organization.app.domain.entity.UserDetailDTO getUserByOpenid(String openId) {
        LambdaQueryWrapper<UserWeiXin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserWeiXin::getOpenid, openId);
        UserWeiXin userWeiXin = userWeiXinMapper.selectOne(queryWrapper);
        if (userWeiXin == null) {
            return null;
        }
        //获取用户信息
        UserDetailDTO userDetailDTO = userWeiXinMapper.getUserDetailById(userWeiXin.getUserId());
        if (userDetailDTO == null) {
            return null;
        }
        return userDetailDTO;
    }
}

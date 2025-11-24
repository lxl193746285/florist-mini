package com.qy.member.app.application.service.impl;

import com.qy.member.app.application.dto.SystemPassportQrcodeDTO;
import com.qy.member.app.application.query.WeixinAppAuthorizationUrlQuery;
import com.qy.member.app.application.repository.MemberSystemDataRepository;
import com.qy.member.app.application.repository.MemberSystemWeixinAppDataRepository;
import com.qy.member.app.application.repository.SystemPassportQrcodeRepository;
import com.qy.member.app.application.service.*;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.SystemPassportQrcodeDO;
import com.qy.rbac.api.client.ClientClient;
import com.qy.rbac.api.dto.ClientDTO;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.MemberIdentity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 系统二维码命令服务实现
 *
 * @author wwd
 */
@Service
public class SystemPassportQrcodeServiceImpl implements SystemPassportQrcodeService {
    private SystemPassportQrcodeRepository systemPassportQrcodeRepository;
    private ClientClient clientClient;
    private MemberSystemDataRepository memberSystemDataRepository;
    private MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository;
    private WeixinService weixinService;
    @Value("${qy.qrcode.call-back-url}")
    private String callBackUrl;

    public SystemPassportQrcodeServiceImpl(SystemPassportQrcodeRepository systemPassportQrcodeRepository,
                                           ClientClient clientClient,
                                           MemberSystemDataRepository memberSystemDataRepository,
                                           MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository,
                                           WeixinService weixinService) {
        this.systemPassportQrcodeRepository = systemPassportQrcodeRepository;
        this.clientClient = clientClient;
        this.memberSystemDataRepository = memberSystemDataRepository;
        this.memberSystemWeixinAppDataRepository = memberSystemWeixinAppDataRepository;
        this.weixinService = weixinService;
    }

    @Override
    public String getLoginPassport(String uuid, String linkType, String clientId) {
        SystemPassportQrcodeDO qrcodeDO = new SystemPassportQrcodeDO();
        qrcodeDO.setUuid(uuid);
        qrcodeDO.setLinkType(linkType);
        systemPassportQrcodeRepository.save(qrcodeDO);
        ClientDTO dto = clientClient.getClient(clientId);
        if (dto == null){
            throw new ValidationException("非法的客户端");
        }
        MemberSystemDO systemDO = memberSystemDataRepository.findById(dto.getSystemId());
        if (systemDO == null) {
            throw new ValidationException("非法的会员系统");
        }
        String url = "uuid=" + uuid;
        return url;
    }

    @Override
    public String getWeixinPassport(String uuid, String linkType, String clientId, String path) {
        SystemPassportQrcodeDO qrcodeDO = new SystemPassportQrcodeDO();
        qrcodeDO.setUuid(uuid);
        qrcodeDO.setLinkType(linkType);
        systemPassportQrcodeRepository.save(qrcodeDO);
        ClientDTO dto = clientClient.getClient(clientId);
        if (dto == null){
            throw new ValidationException("非法的客户端");
        }
        MemberSystemDO systemDO = memberSystemDataRepository.findById(dto.getSystemId());
        if (systemDO == null) {
            throw new ValidationException("非法的会员系统");
        }
        WeixinAppAuthorizationUrlQuery query = new WeixinAppAuthorizationUrlQuery();
        String url = callBackUrl + path + "?uuid=" + uuid;
        query.setClientId(dto.getClientId());
        query.setUrl(url);
        return weixinService.getAuthorizationUrl(query);
    }

    @Override
    public void action(String uuid, Integer status, HttpServletRequest request, MemberIdentity user) {
        SystemPassportQrcodeDO qrcodeDO = systemPassportQrcodeRepository.findByUuid(uuid);
        if (qrcodeDO == null) {
            throw new ValidationException("非法的二维码");
        }
        if (qrcodeDO.getExpirationTime().isBefore(LocalDateTime.now())){
            qrcodeDO.setStatus(3);
            systemPassportQrcodeRepository.update(qrcodeDO);
            throw new ValidationException("二维码已过期");
        }
        qrcodeDO.setStatus(status);
        qrcodeDO.setUserId(user.getId());
        qrcodeDO.setUseTime(LocalDateTime.now());
        qrcodeDO.setAccessToken(request.getHeader("Authorization").replace("Bearer ", ""));
        systemPassportQrcodeRepository.update(qrcodeDO);
    }

    @Override
    public SystemPassportQrcodeDTO check(String uuid, String type) {
        SystemPassportQrcodeDO qrcodeDO = systemPassportQrcodeRepository.findByUuid(uuid);
        if (qrcodeDO == null){
            throw new ValidationException("非法的二维码");
        }
        SystemPassportQrcodeDTO dto = new SystemPassportQrcodeDTO();
        if (qrcodeDO.getStatus() == 0){
            dto.setMessage("等待操作！");
        }
        if (qrcodeDO.getStatus() == 1){
            dto.setMessage("已确认！");
            dto.setAccessToken(qrcodeDO.getAccessToken());
        }
        if (qrcodeDO.getStatus() == 2){
            dto.setMessage("已拒绝！");
        }
        if (qrcodeDO.getStatus() == 3 || qrcodeDO.getExpirationTime().isBefore(LocalDateTime.now())){
            qrcodeDO.setStatus(3);
            systemPassportQrcodeRepository.update(qrcodeDO);
            dto.setMessage("已过期！");
        }
        dto.setStatus(qrcodeDO.getStatus());
        return dto;
    }
}
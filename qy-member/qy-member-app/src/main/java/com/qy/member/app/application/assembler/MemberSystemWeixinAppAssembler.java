package com.qy.member.app.application.assembler;

import com.qy.attachment.api.client.AttachmentClient;
import com.qy.attachment.api.dto.AttachmentBasicDTO;
import com.qy.member.app.application.dto.MemberSystemWeixinAppDTO;
import com.qy.member.app.application.repository.MemberSystemWeixinAppDataRepository;
import com.qy.member.app.application.security.MemberSystemWeixinAppPermission;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemWeixinAppDO;
import com.qy.rbac.api.client.ClientClient;
import com.qy.rbac.api.dto.ClientDTO;
import com.qy.rest.constant.DateTimeFormatConstant;
import com.qy.security.permission.action.Action;
import com.qy.security.session.Identity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员系统微信应用汇编器
 *
 * @author legendjw
 */
@Mapper
public abstract class MemberSystemWeixinAppAssembler {
    @Autowired
    private MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository;
    @Autowired
    private MemberSystemWeixinAppPermission memberSystemWeixinAppPermission;
    @Autowired
    private AttachmentClient attachmentClient;
    @Autowired
    private ClientClient clientClient;

    @Mapping(source = "memberSystemWeixinAppDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "qrCode", target = "qrCode", qualifiedByName = "mapAttachment")
    @Mapping(source = "memberSystemWeixinAppDO", target = "clientName", qualifiedByName = "mapClientName")
    @Mapping(source = "memberSystemWeixinAppDO.createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "memberSystemWeixinAppDO.updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    public abstract MemberSystemWeixinAppDTO toDTO(MemberSystemWeixinAppDO memberSystemWeixinAppDO, @Context Identity identity);

    @Named("mapAttachment")
    public AttachmentBasicDTO mapAttachment(Long attachmentId) {
        return attachmentId != null && attachmentId != 0L ? attachmentClient.getBasicAttachment(attachmentId) : null;
    }

    @Named("mapActions")
    protected List<Action> mapActions(MemberSystemWeixinAppDO memberSystemWeixinAppDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        if (identity == null) { return actions; }
        if (memberSystemWeixinAppPermission.hasPermission(identity, MemberSystemWeixinAppPermission.UPDATE, memberSystemWeixinAppDO.getId())) {
            actions.add(MemberSystemWeixinAppPermission.UPDATE.toAction());
        }
        if (memberSystemWeixinAppPermission.hasPermission(identity, MemberSystemWeixinAppPermission.DELETE, memberSystemWeixinAppDO.getId())) {
            actions.add(MemberSystemWeixinAppPermission.DELETE.toAction());
        }
        return actions;
    }

    @Named("mapClientName")
    protected String mapClientName(MemberSystemWeixinAppDO memberSystemWeixinAppDO) {
        ClientDTO clientDTO = clientClient.getClient(memberSystemWeixinAppDO.getClientId());
        return clientDTO != null ? clientDTO.getName() : "";
    }
}
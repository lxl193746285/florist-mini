package com.qy.organization.app.application.command;

import com.qy.organization.app.application.dto.PhoneAndNameDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 发送组织邀请链接命令
 *
 * @author legendjw
 */
@Data
public class SendOrganizationInviteLinkCommand {
    /**
     * 组织id
     */
    @NotNull(message = "请选择发送邀请链接的组织")
    private Long organizationId;

    /**
     * 邀请的人员
     */
    @NotNull(message = "请选择邀请的人员")
    private List<PhoneAndNameDTO> users = new ArrayList<>();
}
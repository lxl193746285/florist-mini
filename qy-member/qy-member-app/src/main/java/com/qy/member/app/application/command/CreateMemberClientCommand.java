package com.qy.member.app.application.command;

import com.qy.customer.api.command.BusinessLicenseForm;
import com.qy.customer.api.command.ContactForm;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 创建会员命令
 *
 * @author legendjw
 */
@Data
public class CreateMemberClientCommand {

    @ApiModelProperty(value = "会员id")
    private Long memberId;

    @ApiModelProperty(value = "客户端id")
    private List<String> clientIds;
}
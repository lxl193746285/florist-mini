package com.qy.message.app.application.command;

import com.qy.message.app.application.dto.PlatformConfig;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 编辑消息平台命令
 *
 * @author legendjw
 */
@Data
public class UpdatePlatformCommand implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    @NotBlank(message = "请输入名称")
    private String name;

    /**
     * 配置
     */
    private PlatformConfig config;

    /**
     * 备注
     */
    private String remark;
}
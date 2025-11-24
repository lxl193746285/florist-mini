package com.qy.message.app.application.dto;

import com.qy.security.permission.action.Action;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息平台DTO
 *
 * @author legendjw
 */
@Data
public class PlatformDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 配置
     */
    private com.qy.message.app.application.dto.PlatformConfig config;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 创建人名称
     */
    private String creatorName;

    /**
     * 创建时间
     */
    private String createTimeName;

    /**
     * 更新人id
     */
    private Long updatorId;

    /**
     * 更新人名称
     */
    private String updatorName;

    /**
     * 更新时间
     */
    private String updateTimeName;


    /**
     * 可操作动作
     */
    List<Action> actions = new ArrayList<>();
}

package com.qy.crm.customer.app.application.dto;

import com.qy.attachment.api.dto.AttachmentBasicDTO;
import com.qy.security.permission.action.Action;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 营业执照
 *
 * @author legendjw
 * @since 2021-08-05
 */
@Data
public class BusinessLicenseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 类型
     */
    private String type;

    /**
     * 法人id
     */
    private Long legalPersonId;

    /**
     * 法人姓名
     */
    private String legalPersonName;

    /**
     * 电话
     */
    private String tel;

    /**
     * 注册资本
     */
    private String registeredCapital;

    /**
     * 成立日期
     */
    private LocalDate registerDate;

    /**
     * 业务范围
     */
    private String businessScope;

    /**
     * 住所
     */
    private String domicile;

    /**
     * 营业执照照片
     */
    private AttachmentBasicDTO image;

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
     * 操作
     */
    private List<Action> actions = new ArrayList<>();
}
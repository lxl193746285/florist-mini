package com.qy.crm.customer.app.application.command;

import com.qy.crm.customer.app.application.dto.RelatedModuleDataDTO;
import com.qy.security.session.EmployeeIdentity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * 创建营业执照命令
 *
 * @author legendjw
 */
@Data
public class CreateBusinessLicenseCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前用户
     */
    @JsonIgnore
    private EmployeeIdentity identity;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 关联信息
     */
    private List<RelatedModuleDataDTO> relatedModuleData;

    /**
     * 名称
     */
    @NotBlank(message = "请输入营业执照名称")
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
     * 营业执照照片附件id
     */
    private Long imageId;

    /**
     * 备注
     */
    private String remark;
}
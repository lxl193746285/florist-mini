package com.qy.customer.api.command;

import com.qy.security.session.EmployeeIdentity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 更新营业执照命令
 *
 * @author legendjw
 */
@Data
public class UpdateBusinessLicenseCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前用户
     */
    @JsonIgnore
    private EmployeeIdentity identity;

    /**
     * id
     */
    private Long id;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 关联模块id
     */
    private String relatedModuleId;

    /**
     * 关联数据id
     */
    private Long relatedDataId;

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
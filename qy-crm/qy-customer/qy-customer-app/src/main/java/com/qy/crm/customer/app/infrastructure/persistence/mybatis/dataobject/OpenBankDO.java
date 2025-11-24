package com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject;

import com.qy.security.interfaces.GetOrganizationCreator;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 开户行
 * </p>
 *
 * @author legendjw
 * @since 2021-08-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_crm_open_bank")
public class OpenBankDO implements Serializable, GetOrganizationCreator {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 核准号
     */
    private String approvalNumber;

    /**
     * 名称
     */
    private String name;

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
     * 开户银行
     */
    private String depositBank;

    /**
     * 银行账号
     */
    private String bankAccount;

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
    private LocalDateTime createTime;

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
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    private Byte isDeleted;

    /**
     * 删除人id
     */
    private Long deletorId;

    /**
     * 删除人名称
     */
    private String deletorName;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;


}

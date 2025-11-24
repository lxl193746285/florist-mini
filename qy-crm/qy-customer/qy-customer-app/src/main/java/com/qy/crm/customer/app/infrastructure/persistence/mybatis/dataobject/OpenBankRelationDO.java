package com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 开户行关系
 * </p>
 *
 * @author legendjw
 * @since 2021-08-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_crm_open_bank_relation")
public class OpenBankRelationDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 关联模块id
     */
    private String moduleId;

    /**
     * 关联数据id
     */
    private Long dataId;

    /**
     * 开户行id
     */
    private Long openBankId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}

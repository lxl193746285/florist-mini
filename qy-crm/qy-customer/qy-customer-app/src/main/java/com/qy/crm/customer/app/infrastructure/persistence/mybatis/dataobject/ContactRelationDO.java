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
 * 联系人关系
 * </p>
 *
 * @author legendjw
 * @since 2021-08-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_crm_contact_relation")
public class ContactRelationDO implements Serializable {

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
     * 联系人id
     */
    private Long contactId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}

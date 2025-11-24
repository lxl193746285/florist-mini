package com.qy.organization.app.infrastructure.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 组织
 * </p>
 *
 * @author legendjw
 * @since 2021-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_org_datasource")
public class OrgDatasourceDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 数据库配置
     */
    private String datasourceName;

    /**
     * 组织id
     */
    private Long orgId;

    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人id
     */
    private Long updatorId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * 删除人id
     */
    private Long deletorId;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;
}

package com.qy.member.app.infrastructure.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qy.security.interfaces.GetOrganization;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 会员系统会员
 * </p>
 *
 * @author legendjw
 * @since 2021-08-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_mbr_member_client")
public class MemberClientDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}

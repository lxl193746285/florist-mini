package com.qy.extend.app.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CommentExtendFormDTO  implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    private Long id;
    /**
     * 类型
     */
    private String type;
    /**
     * 关联id
     */
    private Long linkId;

    /**
     * 公司id
     */
//    @NotNull(message = "公司id 不能为空")
    private Long companyId;

    /**
     * 会员id
     */
//    @NotNull(message = "会员id 不能为空")
    private Long memberId;

    /**
     * 系统别  1-办公 系统，2会员系统
     */
//    @NotNull(message = "系统别  1-办公 系统，2会员系统 不能为空")
    private Integer systemType;

    /**
     * 1点赞，2关注
     */
//    @NotNull(message = "1点赞，2关注 不能为空")
    private Integer category;

    /**
     * 创建人
     */
    private String creatorName;

    /**
     * 创建人Id
     */
    private Long creatorId;


    /**
     * 更新人
     */
    private String updatorName;

    /**
     * 更新人Id
     */
    private Long updatorId;


    /**
     * 删除人
     */
    private String deletorName;

    /**
     * 删除人Id
     */
    private Long deletorId;


    /**
     * 删除标记(0:未删除；1:已删除)
     */
    private Integer isDeleted;

}

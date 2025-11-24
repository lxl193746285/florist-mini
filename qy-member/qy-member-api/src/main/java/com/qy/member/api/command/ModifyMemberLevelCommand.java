package com.qy.member.api.command;

import lombok.Data;

/**
 * 修改会员等级命令
 *
 * @author legendjw
 */
@Data
public class ModifyMemberLevelCommand {
    /**
     * 会员id
     */
    private Long id;

    /**
     * 等级id
     */
    private Long levelId;

    /**
     * 等级名称
     */
    private String levelName;
}
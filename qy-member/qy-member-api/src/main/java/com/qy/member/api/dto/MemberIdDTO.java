package com.qy.member.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会员id
 *
 * @author legendjw
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberIdDTO {
    /**
     * 会员id
     */
    private Long id;

    /**
     * 账号id
     */
    private Long accountId;
}

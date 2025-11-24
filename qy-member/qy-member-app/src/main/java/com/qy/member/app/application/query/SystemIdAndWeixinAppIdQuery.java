package com.qy.member.app.application.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会员系统id以及微信应用id查询
 *
 * @author legendjw
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemIdAndWeixinAppIdQuery {
    /**
     * 会员系统id
     */
    private String systemId;

    /**
     * 微信应用id
     */
    private String appId;
}

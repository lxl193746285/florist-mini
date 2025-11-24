package com.qy.member.app.application.dto;

import lombok.Builder;
import lombok.Data;
import me.chanjar.weixin.common.service.WxService;

import java.time.LocalDateTime;

/**
 * 区分版本的微信服务
 *
 * @author legendjw
 */
@Data
@Builder
public class WxServiceWithVersionDTO {
    private LocalDateTime version;
    private WxService wxService;
}

package com.qy.organization.app.domain.entity;

import com.qy.authorization.api.dto.AccessTokenDTO;
import lombok.Data;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 返回参数
 */
@Data
public class ResDto {
	/**
	 * 返回状态
	 */
	private int status;
	/**
	 * 消息
	 */
	private String message;

	private String accessToken;

	private AccessTokenDTO accessTokenDTO;

	private WxMpUser wxMpUser = new WxMpUser();

	private UserDetailDTO user = new UserDetailDTO();

}

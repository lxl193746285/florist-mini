package com.qy.member.app.domain.entity;

import com.qy.member.app.domain.valueobject.WxAuthUser;
import com.qy.rest.exception.ValidationException;
import lombok.Getter;

/**
 * 会员账号聚合
 *
 * @author legendjw
 */
@Getter
public class AccountWeixin {
    /**
     * 微信appid
     */
    private String appId;

    /**
     * 账号
     */
    private MemberAccount memberAccount;

    /**
     * 微信用户
     */
    private WxAuthUser wxUser;

    public AccountWeixin(String appId, MemberAccount memberAccount, WxAuthUser wxUser) {
        this.appId = appId;
        this.memberAccount = memberAccount;
        this.wxUser = wxUser;
    }

    /**
     * 是否绑定微信
     *
     * @return
     */
    public boolean isBindWeixin() {
        return this.wxUser == null ? false : true;
    }

    /**
     * 绑定微信
     *
     * @param wxUser
     */
    public void bindWeixin(WxAuthUser wxUser) {
        if (this.wxUser != null) {
            throw new ValidationException("此账号已经绑定微信，请先解绑");
        }
        this.wxUser = wxUser;
    }

    /**
     * 解绑微信
     *
     * @param wxUser
     */
    public void unbindWeixin(WxAuthUser wxUser) {
        if (this.wxUser == null) {
            throw new ValidationException("此账号未绑定微信，无法解绑");
        }
        if (wxUser.getUnionId() == null) {
            if (!wxUser.getOpenId().equals(this.wxUser.getOpenId())) {
                throw new ValidationException("请使用之前绑定的微信账号授权进行解绑");
            }
        }
        else if(!wxUser.getUnionId().equals(this.wxUser.getUnionId()) && !wxUser.getOpenId().equals(this.wxUser.getOpenId())) {
            throw new ValidationException("请使用之前绑定的微信账号授权进行解绑");
        }
        this.wxUser = null;
    }
}

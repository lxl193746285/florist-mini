package com.qy.member.app.application.service;

import com.qy.member.app.application.command.BindWeixinBySessionCommand;
import com.qy.member.app.application.command.BindWeixinCommand;
import com.qy.member.app.application.dto.BindWeixinInfoDTO;
import com.qy.security.session.MemberIdentity;

/**
 * 账号微信服务
 *
 * @author legendjw
 */
public interface AccountWeixinService {
    /**
     * 查询指定账号是否绑定过微信
     *
     * @param appId
     * @param accountId
     * @param organizationId
     * @return
     */
    boolean isBindWeixin(String appId, Long accountId, Long organizationId);

    /**
     * 绑定微信
     *
     * @param command
     * @param identity
     */
    void bindWeixin(BindWeixinCommand command, MemberIdentity identity);

    /**
     * 解绑微信
     *
     * @param command
     * @param identity
     */
    void unbindWeixin(BindWeixinCommand command, MemberIdentity identity);

    /**
     * 通过微信会话绑定微信
     *
     * @param command
     */
    void bindWeixin(BindWeixinBySessionCommand command);

    /**
     * 获取指定微信应用下账号的绑定微信信息
     *
     * @param appId
     * @param accountId
     * @return
     */
    BindWeixinInfoDTO getAccountBindWeixinInfo(String appId, Long accountId);

    /**
     * 获取指定支付方式下账号的绑定微信信息
     *
     * @param subPayWay
     * @param memberId
     * @return
     */
    BindWeixinInfoDTO getAccountBindWeixinInfoByPayWay(String subPayWay, Long memberId);


}
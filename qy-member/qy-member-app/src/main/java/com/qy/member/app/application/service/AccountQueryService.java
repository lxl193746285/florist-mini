package com.qy.member.app.application.service;

import com.qy.member.app.application.dto.AccountBasicDTO;
import com.qy.member.app.application.dto.AccountDTO;
import com.qy.member.app.application.dto.IsPhoneRegisteredDTO;
import com.qy.member.app.application.dto.SendMessageAccountInfoDTO;
import com.qy.member.app.application.query.AccountQuery;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 账号查询服务
 *
 * @author legendjw
 */
public interface AccountQueryService {
    /**
     * 根据id获取基本账号信息
     *
     * @param id
     * @return
     */
    AccountBasicDTO getBasicAccount(Long id);

    /**
     * 获取账号列表
     *
     * @return
     */
    Page<AccountDTO> getAccounts(AccountQuery query, Identity identity);

    /**
     * 获取指定会员的主账号
     *
     * @param memberId
     * @return
     */
    AccountBasicDTO getPrimaryAccount(Long memberId);

    /**
     * 获取指定会员的所有子账号
     *
     * @param memberId
     * @return
     */
    List<AccountBasicDTO> getChildAccounts(Long memberId);

    /**
     * 获取发送消息会员账号信息
     *
     * @param memberId
     * @return
     */
    SendMessageAccountInfoDTO getSendMessageAccountInfo(Long memberId);

    /**
     * 指定手机号是否已经注册账号
     *
     * @param phone
     * @return
     */
    IsPhoneRegisteredDTO isPhoneRegistered(String phone);

    /**
     * 通过手机号获取会员账号信息
     * @param phone
     */
    AccountBasicDTO getAccountByPhone(String phone);
}
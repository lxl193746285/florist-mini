package com.qy.member.app.application.service.impl;

import com.qy.member.app.application.assembler.AccountAssembler;
import com.qy.member.app.application.dto.AccountBasicDTO;
import com.qy.member.app.application.dto.AccountDTO;
import com.qy.member.app.application.dto.IsPhoneRegisteredDTO;
import com.qy.member.app.application.dto.SendMessageAccountInfoDTO;
import com.qy.member.app.application.query.AccountQuery;
import com.qy.member.app.application.repository.*;
import com.qy.member.app.application.repository.*;
import com.qy.member.app.application.service.AccountQueryService;
import com.qy.member.app.domain.enums.WeixinAppType;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.*;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.*;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 账号查询服务实现
 *
 * @author legendjw
 */
@Service
public class AccountQueryServiceImpl implements AccountQueryService {
    private AccountAssembler accountAssembler;
    private AccountDataRepository accountDataRepository;
    private MemberDataRepository memberDataRepository;
    private MemberWeixinDataRepository memberWeixinDataRepository;
    private MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository;
    private MemberSystemDataRepository memberSystemDataRepository;

    public AccountQueryServiceImpl(AccountAssembler accountAssembler, AccountDataRepository accountDataRepository, MemberDataRepository memberDataRepository, MemberWeixinDataRepository memberWeixinDataRepository, MemberSystemWeixinAppDataRepository memberSystemWeixinAppDataRepository, MemberSystemDataRepository memberSystemDataRepository) {
        this.accountAssembler = accountAssembler;
        this.accountDataRepository = accountDataRepository;
        this.memberDataRepository = memberDataRepository;
        this.memberWeixinDataRepository = memberWeixinDataRepository;
        this.memberSystemWeixinAppDataRepository = memberSystemWeixinAppDataRepository;
        this.memberSystemDataRepository = memberSystemDataRepository;
    }

    @Override
    public AccountBasicDTO getBasicAccount(Long id) {
        MemberAccountDO accountDO = accountDataRepository.findById(id);
        return accountAssembler.toBasicDTO(accountDO);
    }

    @Override
    public Page<AccountDTO> getAccounts(AccountQuery query, Identity identity) {
        Page<MemberAccountDO> memberDOPage = accountDataRepository.findByQuery(query);
        return memberDOPage.map(memberDO -> accountAssembler.toDTO(memberDO, identity));
    }

    @Override
    public AccountBasicDTO getPrimaryAccount(Long memberId) {
        MemberAccountDO accountDO = accountDataRepository.findMemberPrimaryAccount(memberId);
        return accountAssembler.toBasicDTO(accountDO);
    }

    @Override
    public List<AccountBasicDTO> getChildAccounts(Long memberId) {
        List<MemberAccountDO> accountDOS = accountDataRepository.findMemberChildAccounts(memberId);
        return accountDOS.stream().map(a -> accountAssembler.toBasicDTO(a)).collect(Collectors.toList());
    }

    @Override
    public SendMessageAccountInfoDTO getSendMessageAccountInfo(Long memberId) {
        MemberDO memberDO = memberDataRepository.findById(memberId);
        if (memberDO == null) {
            throw new NotFoundException("未找到指定的会员");
        }
        MemberAccountDO accountDO = accountDataRepository.findById(memberDO.getAccountId());
        MemberWeixinDO memberWeixinDO = memberWeixinDataRepository.findByMemberSystemAndAppTypeAndAccountId(memberDO.getSystemId(), WeixinAppType.MP.getId(), memberDO.getAccountId());
        MemberSystemWeixinAppDO memberSystemWeixinAppDO = memberSystemWeixinAppDataRepository.findBySystemIdAndAppType(memberDO.getSystemId(), WeixinAppType.MP.getId());
        //此处兼容项目使用单个公众号切换门店的场景
        if (memberWeixinDO == null) {
            MemberSystemDO defaultMemberSystem = memberSystemDataRepository.findDefaultMemberSystem();
            if (defaultMemberSystem != null) {
                memberWeixinDO = memberWeixinDataRepository.findByMemberSystemAndAppTypeAndAccountId(defaultMemberSystem.getId(), WeixinAppType.MP.getId(), memberDO.getAccountId());
                memberSystemWeixinAppDO = memberSystemWeixinAppDataRepository.findBySystemIdAndAppType(defaultMemberSystem.getId(), WeixinAppType.MP.getId());
            }
        }

        SendMessageAccountInfoDTO sendMessageAccountInfoDTO = new SendMessageAccountInfoDTO();
        sendMessageAccountInfoDTO.setAccountId(accountDO.getId());
        sendMessageAccountInfoDTO.setSystemId(memberDO.getSystemId());
        sendMessageAccountInfoDTO.setEmail("");
        sendMessageAccountInfoDTO.setWeixinAppId(memberSystemWeixinAppDO != null ? memberSystemWeixinAppDO.getAppId() : "");
        sendMessageAccountInfoDTO.setOpenId(memberWeixinDO != null ? memberWeixinDO.getOpenId() : "");
        return sendMessageAccountInfoDTO;
    }

    @Override
    public IsPhoneRegisteredDTO isPhoneRegistered(String phone) {
        boolean isRegistered = accountDataRepository.findByPhone(phone) != null;
        IsPhoneRegisteredDTO isPhoneRegisteredDTO = new IsPhoneRegisteredDTO(isRegistered);
        return isPhoneRegisteredDTO;
    }

    @Override
    public AccountBasicDTO getAccountByPhone(String phone) {
        MemberAccountDO accountDO = accountDataRepository.findByPhone(phone);
        if (accountDO == null || accountDO.getIsDeleted() == LogicDeleteConstant.DELETED) {
            return null;
        }
        return accountAssembler.toBasicDTO(accountDO);
    }
}

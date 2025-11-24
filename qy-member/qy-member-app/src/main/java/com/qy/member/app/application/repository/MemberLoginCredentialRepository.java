package com.qy.member.app.application.repository;

import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberLoginCredentialDO;

/**
 * 账号数据资源库
 *
 * @author legendjw
 */
public interface MemberLoginCredentialRepository {

    MemberLoginCredentialDO findByAccountAndCredential(Long accountId, String credentialValue);

    MemberLoginCredentialDO findByCredential(String credentialValue);

    void saveData(MemberLoginCredentialDO memberLoginCredentialDO);

    void removeByAccountId(Long accountId);
}
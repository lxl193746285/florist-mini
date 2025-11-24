package com.qy.member.app.application.repository;

import com.qy.member.app.application.query.AccountQuery;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberAccountDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.SystemPassportQrcodeDO;
import com.qy.rest.pagination.Page;

import java.util.List;

/**
 * 系统二维码资源库
 *
 * @author wwd
 */
public interface SystemPassportQrcodeRepository {
    /**
     * 根据id获取账号
     *
     * @return
     */
    SystemPassportQrcodeDO save(SystemPassportQrcodeDO systemPassportQrcodeDO);

    SystemPassportQrcodeDO update(SystemPassportQrcodeDO systemPassportQrcodeDO);

    SystemPassportQrcodeDO findByUuid(String uuid);

}
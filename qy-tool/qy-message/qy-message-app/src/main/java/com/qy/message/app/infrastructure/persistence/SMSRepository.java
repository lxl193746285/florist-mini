package com.qy.message.app.infrastructure.persistence;

import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.SMSLogDO;
import com.qy.rest.pagination.Page;
import com.qy.security.session.EmployeeIdentity;

import java.util.List;

/**
 * 短信资源库
 *
 * @author lxl
 */
public interface SMSRepository {

    /**
     * 保存一个短信记录
     *
     * @param smsLogDO
     * @return
     */
    Long save(SMSLogDO smsLogDO);

}
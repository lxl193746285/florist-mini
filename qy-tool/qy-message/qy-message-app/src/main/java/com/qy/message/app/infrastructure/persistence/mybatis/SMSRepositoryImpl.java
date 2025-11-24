package com.qy.message.app.infrastructure.persistence.mybatis;


import com.qy.message.app.infrastructure.persistence.SMSRepository;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.SMSLogDO;
import com.qy.message.app.infrastructure.persistence.mybatis.mapper.SMSLogMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageImpl;
import com.qy.rest.pagination.PageRequest;
import com.qy.security.session.EmployeeIdentity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 短信日志资源库实现
 *
 * @author lxl
 */
@Repository
public class SMSRepositoryImpl implements SMSRepository {
    private SMSLogMapper smsLogMapper;

    public SMSRepositoryImpl(SMSLogMapper smsLogMapper) {
        this.smsLogMapper = smsLogMapper;
    }

    @Override
    public Long save(SMSLogDO smsLogDO) {
        if (smsLogDO.getId() == null) {
            smsLogMapper.insert(smsLogDO);
        }
        else {
            smsLogMapper.updateById(smsLogDO);
        }
        return smsLogDO.getId();
    }

}

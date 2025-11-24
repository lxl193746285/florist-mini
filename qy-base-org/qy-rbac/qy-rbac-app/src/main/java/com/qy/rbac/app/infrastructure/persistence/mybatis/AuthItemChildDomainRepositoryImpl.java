package com.qy.rbac.app.infrastructure.persistence.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qy.rbac.app.infrastructure.persistence.AuthItemChildDomainRepository;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.AuthItemChildDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.mapper.AuthItemChildMapper;
import org.springframework.stereotype.Service;

/**
 * 权限项领域资源库
 *
 * @author legendjw
 */
@Service
public class AuthItemChildDomainRepositoryImpl extends ServiceImpl<AuthItemChildMapper, AuthItemChildDO> implements AuthItemChildDomainRepository {

}

package com.qy.crm.customer.app.infrastructure.persistence.mybatis.mapper;

import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.CustomerDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 客户 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-08-05
 */
@Mapper
public interface CustomerMapper extends BaseMapper<CustomerDO> {

}

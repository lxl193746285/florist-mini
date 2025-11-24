package com.qy.crm.customer.app.infrastructure.persistence.mybatis.mapper;

import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.ContactRelationDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 联系人关系 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-08-05
 */
@Mapper
public interface ContactRelationMapper extends BaseMapper<ContactRelationDO> {

}

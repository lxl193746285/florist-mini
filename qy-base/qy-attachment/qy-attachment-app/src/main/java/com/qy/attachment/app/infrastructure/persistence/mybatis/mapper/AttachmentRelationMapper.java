package com.qy.attachment.app.infrastructure.persistence.mybatis.mapper;

import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentRelationDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 附件关系 Mapper 接口
 * </p>
 *
 * @author legendjw
 * @since 2021-08-11
 */
@Mapper
public interface AttachmentRelationMapper extends BaseMapper<AttachmentRelationDO> {

}

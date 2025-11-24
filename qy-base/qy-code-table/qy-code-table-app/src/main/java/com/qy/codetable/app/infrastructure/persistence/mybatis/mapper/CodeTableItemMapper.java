package com.qy.codetable.app.infrastructure.persistence.mybatis.mapper;

import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableItemDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 代码项 Mapper 接口
 *
 * @author legendjw
 * @since 2021-07-28
 */
@Mapper
public interface CodeTableItemMapper extends BaseMapper<CodeTableItemDO> {
    int selectMaxValue(@Param("type") String type, @Param("relatedId") Long relatedId, @Param("code") String code);
}

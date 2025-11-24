package com.qy.codetable.app.infrastructure.persistence.mybatis.mapper;

import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableDefaultItemDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 代码默认项 Mapper 接口
 *
 * @author legendjw
 * @since 2021-07-28
 */
@Mapper
public interface CodeTableDefaultItemMapper extends BaseMapper<CodeTableDefaultItemDO> {
    int selectMaxValue(@Param("type") String type, @Param("code") String code);
}

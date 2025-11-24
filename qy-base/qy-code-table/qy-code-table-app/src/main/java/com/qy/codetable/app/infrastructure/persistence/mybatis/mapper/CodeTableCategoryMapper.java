package com.qy.codetable.app.infrastructure.persistence.mybatis.mapper;

import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableCategoryDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 代码分类 Mapper 接口
 *
 * @author legendjw
 * @since 2021-07-28
 */
@Mapper
public interface CodeTableCategoryMapper extends BaseMapper<CodeTableCategoryDO> {

}

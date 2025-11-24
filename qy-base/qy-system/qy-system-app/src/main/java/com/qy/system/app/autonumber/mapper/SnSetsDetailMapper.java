package com.qy.system.app.autonumber.mapper;

import com.qy.system.app.autonumber.entity.SnSetsDetailEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 编号规则设置子表 Mapper 接口
 * </p>
 *
 * @author ln
 * @since 2022-04-30
 */
@Repository
public interface SnSetsDetailMapper extends BaseMapper<SnSetsDetailEntity> {

    void deleteBySnSetsId(SnSetsDetailEntity snSetsDetailEntity);

    String getAutoNumber(@Param("noId") String noId, @Param("companyId") Long companyId, @Param("userId") Long userId);
}

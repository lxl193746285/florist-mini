package com.qy.wf.nodeRepulse.mapper;

import com.qy.wf.nodeRepulse.entity.DefNodeRepulseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 工作流_设计_打回节点 Mapper 接口
 * </p>
 *
 * @author syf
 * @since 2023-08-16
 */
@Mapper
public interface DefNodeRepulseMapper extends BaseMapper<DefNodeRepulseEntity> {

    void deleteByWfId(Long wfId);
}

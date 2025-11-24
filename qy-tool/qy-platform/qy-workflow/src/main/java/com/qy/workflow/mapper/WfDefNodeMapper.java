package com.qy.workflow.mapper;

import com.qy.workflow.dto.WfNodeDTO;
import com.qy.workflow.entity.WfDefNodeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 工作流_设计_节点 Mapper 接口
 * </p>
 *
 * @author iFeng
 * @since 2022-11-15
 */
@Mapper
public interface WfDefNodeMapper extends BaseMapper<WfDefNodeEntity> {
    List<WfNodeDTO> selectById(@Param("wfid") Long id, @Param("nodeid") Long nodeId);
}

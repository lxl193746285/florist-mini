package com.qy.wf.runNode.mapper;

import com.qy.wf.runNode.dto.RunNodeDTO;
import com.qy.wf.runNode.entity.RunNodeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 工作流_执行_节点 Mapper 接口
 * </p>
 *
 * @author wch
 * @since 2022-11-17
 */
@Mapper
public interface RunNodeMapper extends BaseMapper<RunNodeEntity> {

    Integer getNextNodeStatus(@Param("curNodeId") Long curNodeId, @Param("curNodeUserId")Long curNodeUserId, @Param("id")Long id);

    RunNodeEntity getRunNodeByWfRunId(Long wfRunId);

    List<RunNodeDTO> getRunNodeListByWfRunId(Long wfRunId);
}

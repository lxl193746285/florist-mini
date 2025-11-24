package com.qy.wf.nodetable.mapper;

import com.qy.wf.nodetable.entity.NodeTableEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 工作流_设计_节点表单 Mapper 接口
 * </p>
 *
 * @author hh
 * @since 2022-11-19
 */
@Mapper
public interface NodeTableMapper extends BaseMapper<NodeTableEntity> {

    void deleteByNodeCode(@Param("list") List<String> nodeCodeList);

    void deleteByWfId(Long wfId);
}

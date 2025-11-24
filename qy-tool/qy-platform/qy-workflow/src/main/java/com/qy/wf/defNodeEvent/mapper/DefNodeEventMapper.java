package com.qy.wf.defNodeEvent.mapper;

import com.qy.wf.defNodeEvent.entity.DefNodeEventEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 工作流_设计_节点事件 Mapper 接口
 * </p>
 *
 * @author syf
 * @since 2022-11-21
 */
@Mapper
public interface DefNodeEventMapper extends BaseMapper<DefNodeEventEntity> {

    void deleteByNodeCode(@Param("nodeCodeList") List<String> nodeCodeList);

    void deleteByWfId(Long wfId);

    List<DefNodeEventEntity> getDefNodeEventCanUseList(Long wfId,Long nodeId);
}

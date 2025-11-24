package com.qy.wf.defNodeRelation.mapper;

import com.qy.wf.defNodeRelation.entity.DefNodeRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 工作流_设计_节点关系 Mapper 接口
 * </p>
 *
 * @author syf
 * @since 2022-11-14
 */
@Mapper
public interface DefNodeRelationMapper extends BaseMapper<DefNodeRelationEntity> {

    void deleteByNodeCode(@Param("nodeCodeList") List<String> nodeCodeList);
}

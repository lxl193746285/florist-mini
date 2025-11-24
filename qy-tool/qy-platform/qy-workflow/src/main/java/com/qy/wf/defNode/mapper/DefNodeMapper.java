package com.qy.wf.defNode.mapper;

import com.qy.wf.defNode.entity.DefNodeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 工作流_设计_节点 Mapper 接口
 * </p>
 *
 * @author syf
 * @since 2022-11-14
 */
@Mapper
public interface DefNodeMapper extends BaseMapper<DefNodeEntity> {

    void deleteByNodeCode(@Param("nodeCodeList") List<String> nodeCodeList);

    List<DefNodeEntity> getIdByNodeCode(@Param("list") List<String> codeList);
}

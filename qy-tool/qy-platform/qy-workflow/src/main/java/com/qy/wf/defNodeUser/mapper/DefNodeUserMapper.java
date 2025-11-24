package com.qy.wf.defNodeUser.mapper;

import com.qy.wf.defNodeUser.entity.DefNodeUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 工作流_设计_节点人员 Mapper 接口
 * </p>
 *
 * @author syf
 * @since 2022-11-14
 */
@Mapper
public interface DefNodeUserMapper extends BaseMapper<DefNodeUserEntity> {

    void deleteByNodeCode(@Param("nodeCodeList") List<String> nodeCodeList);

    void deleteByWfId(Long wfId);
}

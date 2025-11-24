package com.qy.wf.defDefine.mapper;

import com.qy.wf.defDefine.dto.DefStartDTO;
import com.qy.wf.defDefine.entity.DefDefineEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 工作流_设计_定义
 * </p>
 *
 * @author wch
 * @since 2022-11-12
 */
@Mapper
public interface DefDefineMapper extends BaseMapper<DefDefineEntity> {

    List<DefStartDTO> getCanStartWFDef();
}

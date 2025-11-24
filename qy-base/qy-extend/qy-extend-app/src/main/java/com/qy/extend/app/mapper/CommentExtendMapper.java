package com.qy.extend.app.mapper;

import com.qy.extend.app.dto.CommentExtendDTO;
import com.qy.extend.app.entity.CommentExtendEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 评论扩展 Mapper 接口
 * </p>
 *
 * @author huangh
 * @since 2022-04-05
 */
@Mapper
public interface CommentExtendMapper  extends BaseMapper<CommentExtendEntity> {

    IPage<CommentExtendDTO> selectByLinkDatas(@Param("params") PageDTO<Object> objectPageDTO, @Param("ew") LambdaQueryWrapper<CommentExtendEntity> queryWrapper);
}


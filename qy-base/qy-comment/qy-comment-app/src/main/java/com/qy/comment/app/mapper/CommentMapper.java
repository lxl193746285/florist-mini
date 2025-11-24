package com.qy.comment.app.mapper;

import com.qy.comment.app.dto.CommentDTO;
import com.qy.comment.app.entity.CommentEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 Mapper 接口
 * </p>
 *
 * @author huangh
 * @since 2022-04-02
 */
@Mapper
public interface CommentMapper extends BaseMapper<CommentEntity> {

    List<CommentDTO> selectByLinkData(@Param("linkType") String linkType, @Param("linkId") Long linkId, @Param("primaryCommentId") Long primaryCommentId);

    List<CommentDTO> selectByParams(@Param("params") Map<String, Object> params);

    IPage<CommentDTO> selectByLinkDatas(@Param("page")PageDTO<Object> objectPageDTO,@Param("ew") LambdaQueryWrapper<CommentEntity> queryWrapper);

    int getPraiseNum(@Param("linkId")Long id, @Param("category")int category);

    int getCommentCount(Long id);

    int getTotalCommentCount(@Param("linkId")Long linkId, @Param("linkType")String linkType);
}

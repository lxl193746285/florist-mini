package com.qy.extend.app.service;

import com.qy.extend.app.dto.CommentExtendDTO;
import com.qy.extend.app.dto.CommentExtendFormDTO;
import com.qy.extend.app.dto.CommentExtendQueryDTO;
import com.qy.extend.app.entity.CommentExtendEntity;
import com.qy.rest.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 点赞 服务类
 *
 * @author huangh
 * @since 2022-04-05
 */
public interface CommentExtendService extends IService<CommentExtendEntity> {

    /**
     * 获取评论扩展分页列表
     *
     * @param iPage 分页
     * @param arkSystemCommentExtendQueryDTO 查询条件
     * @return 评论扩展列表
     */
//    IPage<CommentExtendEntity> getArkSystemCommentExtends(IPage iPage, CommentExtendQueryDTO arkSystemCommentExtendQueryDTO);

    /**
     * 获取评论扩展列表
     *
     * @param arkSystemCommentExtendQueryDTO 查询条件
     * @return 评论扩展列表
     */
    List<CommentExtendEntity> getArkSystemCommentExtends(CommentExtendQueryDTO arkSystemCommentExtendQueryDTO);

    /**
     * 获取单个评论扩展
     *
     * @param id 主键id
     * @return 单个评论扩展
     */
    CommentExtendEntity getArkSystemCommentExtend(Long id);

    /**
     * 创建单个评论扩展
     *
     * @param arkSystemCommentExtendFormDTO 表单对象
     * @return 新创建的评论扩展
     */
    CommentExtendEntity createArkSystemCommentExtend(CommentExtendFormDTO arkSystemCommentExtendFormDTO);

    /**
     * 更新单个评论扩展
     *
     * @param arkSystemCommentExtendFormDTO 表单对象
     * @return 修改后的评论扩展
     */
    CommentExtendEntity updateArkSystemCommentExtend( CommentExtendFormDTO arkSystemCommentExtendFormDTO);

    /**
     * 删除单个评论扩展
     *
     * @return 删除的评论扩展
     */
    CommentExtendEntity deleteArkSystemCommentExtend(CommentExtendFormDTO formDTO);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param arkSystemCommentExtendEntity 数据库映射对象
     * @return
     */
    CommentExtendDTO mapperToDTO(CommentExtendEntity arkSystemCommentExtendEntity);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param arkSystemCommentExtendEntityList 数据库映射对象列表
     * @return
     */
    List<CommentExtendDTO> mapperToDTO(List<CommentExtendEntity> arkSystemCommentExtendEntityList);

    Page<CommentExtendDTO> getCommentExtends(CommentExtendQueryDTO queryDTO);

    CommentExtendEntity getCommentExtend(Long linkId, String type,Long memberId);

    CommentExtendEntity deleteBy(CommentExtendFormDTO formDTO);
}

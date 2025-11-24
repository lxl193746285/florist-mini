package com.qy.comment.app.service;

import com.qy.comment.app.dto.CommentDTO;
import com.qy.comment.app.dto.CommentQueryDTO;
import com.qy.comment.app.entity.CommentEntity;
import com.qy.rest.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 评论 服务类
 *
 * @author huangh
 * @since 2022-04-02
 */
public interface CommentService extends IService<CommentEntity> {

    Page<CommentDTO> getComments(CommentQueryDTO queryDTO);

    List<CommentDTO> getCommentsByLinkData(String linkType, Long linkId);

    void createComment(CommentDTO commentDTO);

    void deleteComment(Long id);

    CommentDTO getCommentById(Long id);

    void updateComment(CommentDTO commentDTO);

    CommentEntity getOneCommentById(Long id);

    Page<CommentDTO> getCommentsForPC(CommentQueryDTO queryDTO);

    int getTotalCommentCount(CommentDTO commentDTO);


//
//    /**
//     * 获取评论列表
//     *
//     * @param commentQueryDTO 查询条件
//     * @param currentUser 当前登录用户
//     * @return 评论列表
//     */
//    List<CommentEntity> getComments(CommentQueryDTO commentQueryDTO, EmployeeIdentity currentUser);
//
//    /**
//     * 获取单个评论
//     *
//     * @param id 主键id
//     * @param currentUser 当前登录用户
//     * @return 单个评论
//     */
//    CommentEntity getComment(Long id, EmployeeIdentity currentUser);
//
//    /**
//     * 创建单个评论
//     *
//     * @param commentFormDTO 表单对象
//     * @param currentUser 当前登录用户
//     * @return 新创建的评论
//     */
//    CommentEntity createComment(CommentFormDTO commentFormDTO, EmployeeIdentity currentUser);
//
//    /**
//     * 更新单个评论
//     *
//     * @param commentFormDTO 表单对象
//     * @param currentUser 当前登录用户
//     * @return 修改后的评论
//     */
//    CommentEntity updateComment(Long id, CommentFormDTO commentFormDTO, EmployeeIdentity currentUser);
//
//    /**
//     * 删除单个评论
//     *
//     * @param id 主键id
//     * @param currentUser 当前登录用户
//     * @return 删除的评论
//     */
//    CommentEntity deleteComment(Long id, EmployeeIdentity currentUser);
//
//    /**
//     * 数据库映射对象转换为DTO对象
//     *
//     * @param commentEntity 数据库映射对象
//     * @param currentUser 当前登录用户
//     * @return
//     */
//    CommentDTO mapperToDTO(CommentEntity commentEntity, EmployeeIdentity currentUser);
//
//    /**
//     * 批量数据库映射对象转换为DTO对象
//     *
//     * @param commentEntityList 数据库映射对象列表
//     * @param currentUser 当前登录用户
//     * @return
//     */
//    List<CommentDTO> mapperToDTO(List<CommentEntity> commentEntityList, EmployeeIdentity currentUser);
}

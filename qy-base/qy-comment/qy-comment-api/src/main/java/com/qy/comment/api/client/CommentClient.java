package com.qy.comment.api.client;

import com.qy.comment.api.dto.CommentDTO;
import com.qy.comment.api.dto.CommentQueryDTO;
import com.qy.comment.api.entity.CommentEntity;
import com.qy.rest.pagination.SimplePageImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

/**
 * 评论接口
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base", contextId = "qy-system-comments")
public interface CommentClient {
    /**
     * 获取评论列表
     *
     * @param queryDTO
     * @return
     */
    @GetMapping("/v4/api/comments")
    SimplePageImpl<CommentDTO> getComments(@SpringQueryMap CommentQueryDTO queryDTO);
    /**
     * 获取评论列表-pc
     *
     * @param queryDTO
     * @return
     */
    @GetMapping("/v4/api/comments/for-pc")
    SimplePageImpl<CommentDTO> getCommentsForPC(@SpringQueryMap CommentQueryDTO queryDTO);

    /**
     * 创建评论
     *
     * @param commentDTO
     * @return
     */
    @PostMapping("/v4/api/comments")
    CommentDTO createComment(@RequestBody CommentDTO commentDTO);

    /**
     * 根据ID查询评论
     *
     * @param id
     * @return
     */
    @GetMapping("/v4/api/comments/getCommentById/{id}")
    CommentDTO getCommentById( @PathVariable(value = "id") Long id);

    /**
     * 根据ID查询单个评论
     *
     * @param id
     * @return
     */
    @GetMapping("/v4/api/comments/{id}")
    CommentEntity getOneCommentById(@PathVariable(value = "id") Long id);


    /**
     * 更新评论
     *
     * @param
     * @param commentDTO
     * @return
     */
    @PatchMapping("/v4/api/comments")
    void updateComment(@RequestBody CommentDTO commentDTO);

    /**
     * 删除评论
     *
     * @param id
     * @return
     */
    @DeleteMapping("/v4/api/comments/delete/{id}")
    CommentDTO deleteComment(@PathVariable(value = "id") Long id);

    /**
     * 评论数量
     *
     * @param commentDTO
     * @return
     */
    @PostMapping("/v4/api/comments/getTotalCommentCount")
    public int getTotalCommentCount( @RequestBody CommentDTO commentDTO );
}

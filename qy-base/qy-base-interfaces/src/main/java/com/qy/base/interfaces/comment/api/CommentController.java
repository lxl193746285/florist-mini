package com.qy.base.interfaces.comment.api;


import com.qy.comment.app.dto.CommentDTO;
import com.qy.comment.app.dto.CommentQueryDTO;
import com.qy.comment.app.entity.CommentEntity;
import com.qy.comment.app.service.CommentService;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.SimplePageImpl;
import com.qy.rest.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 通用的评论控制器
 */
@RestController
@RequestMapping("/v4/api/comments")
public class CommentController {


    @Autowired
    private CommentService commentService;

    /**
     * 获取评论列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    public ResponseEntity<SimplePageImpl<CommentDTO>> getComments(
            @ModelAttribute CommentQueryDTO queryDTO,
            HttpServletResponse response
    ) {
        Page<CommentDTO> page = commentService.getComments(queryDTO);
        return ResponseUtils.ok().body(new SimplePageImpl(page));
    }

    /**
     * 获取评论列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping("/for-pc")
    public ResponseEntity<SimplePageImpl<CommentDTO>> getCommentsForPC(
            @ModelAttribute CommentQueryDTO queryDTO,
            HttpServletResponse response
    ) {
        Page<CommentDTO> page = commentService.getCommentsForPC(queryDTO);
        return ResponseUtils.ok().body(new SimplePageImpl(page));
    }
    /**
     * 查询所有评论
     *
     * @param linkType
     * @param linkId
     * @param response
     * @return
     */
    @GetMapping("/all-comments")
    public List<CommentDTO> getAllComments(
            @RequestParam(name = "link_type") String linkType,
            @RequestParam(name = "link_id") Long linkId,
            HttpServletResponse response
    ) {
        return commentService.getCommentsByLinkData(linkType, linkId);
    }

    /**
     * 新增评论
     *
     * @param commentDTO
     * @param response
     * @return
     */
    @Transactional
    @PostMapping
    public ResponseEntity<CommentDTO> createComment(
            @RequestBody CommentDTO commentDTO,
            HttpServletResponse response
    ) {
        commentService.createComment(commentDTO);
        return ResponseUtils.ok("评论成功").build();
    }

    /**
     * 评论数量
     *
     * @param commentDTO
     * @param response
     * @return
     */
    @PostMapping("/getTotalCommentCount")
    public int getTotalCommentCount(
            @RequestBody CommentDTO commentDTO,
            HttpServletResponse response
    ) {

        return commentService.getTotalCommentCount(commentDTO);
    }

    /**
     * 根据ID查询评论
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/getCommentById/{id}")
    public CommentDTO getCommentById(
            @PathVariable(value = "id") Long id,
            HttpServletResponse response
    ) {
        return commentService.getCommentById(id);
    }

    /**
     * 根据ID查询评论
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    public CommentEntity getOneCommentById(
            @PathVariable(value = "id") Long id,
            HttpServletResponse response
    ) {
        return commentService.getOneCommentById(id);
    }

    /**
     * 更新评论
     *
     * @param
     * @param commentDTO
     * @param response
     * @return
     */
    @PatchMapping
    public ResponseEntity updateComment(
            @RequestBody CommentDTO commentDTO,
            HttpServletResponse response
    ) {

        commentService.updateComment(commentDTO);
        return ResponseUtils.ok("更新成功").build();
    }


    /**
     * 删除评论
     *
     * @param id
     * @param response
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CommentDTO> deleteComment(
            @PathVariable(value = "id") Long id,
            HttpServletResponse response
    ) {

        commentService.deleteComment(id);
        return ResponseUtils.ok("删除成功").build();
    }


}

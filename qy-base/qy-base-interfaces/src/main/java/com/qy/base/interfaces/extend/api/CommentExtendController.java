package com.qy.base.interfaces.extend.api;

import com.qy.extend.app.dto.CommentExtendDTO;
import com.qy.extend.app.dto.CommentExtendFormDTO;
import com.qy.extend.app.dto.CommentExtendQueryDTO;
import com.qy.extend.app.entity.CommentExtendEntity;
import com.qy.extend.app.service.CommentExtendService;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.SimplePageImpl;
import com.qy.rest.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 评论扩展
 *
 * @author huangh
 * @since 2022-04-05
 */
@RestController
@RequestMapping("/v4/api/extends")
public class CommentExtendController {

    @Autowired
    private CommentExtendService commentExtendService;

    /**
     * 获取评论扩展列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<SimplePageImpl<CommentExtendDTO>> getArkSystemCommentExtends(
            @ModelAttribute CommentExtendQueryDTO queryDTO,
            HttpServletResponse response
    ) {
        Page<CommentExtendDTO> page = commentExtendService.getCommentExtends(queryDTO);
        return ResponseUtils.ok().body(new SimplePageImpl(page));
//        return commentExtendService.mapperToDTO(pm.getRecords(), currentUser);
    }

    /**
     * 获取单个评论扩展
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    public CommentExtendDTO getArkSystemCommentExtend(
            @PathVariable(value = "id") Long id,
            HttpServletResponse response
    ) {

        CommentExtendEntity arkSystemCommentExtendEntity = commentExtendService.getArkSystemCommentExtend(id);

        return commentExtendService.mapperToDTO(arkSystemCommentExtendEntity);
    }

    /**
     * 获取单个评论扩展
     *
     * @param formDTO
     * @param response
     * @return
     */
    @GetMapping("/getCommentExtend")
    public CommentExtendDTO getCommentExtend(@RequestBody CommentExtendFormDTO formDTO,
            HttpServletResponse response
    ) {

        CommentExtendEntity arkSystemCommentExtendEntity = commentExtendService.getCommentExtend(formDTO.getLinkId(),formDTO.getType(),formDTO.getMemberId());

        return commentExtendService.mapperToDTO(arkSystemCommentExtendEntity);
    }

    /**
     * 创建单个评论扩展
     *
     * @param formDTO
     * @param response
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity createArkSystemCommentExtend(
            @Valid @RequestBody CommentExtendFormDTO formDTO,
            BindingResult result,
            HttpServletResponse response
    ) {

        commentExtendService.createArkSystemCommentExtend(formDTO);
        return ResponseUtils.ok("评论扩展新增成功").build();
    }

    /**
     * 修改单个评论扩展
     *
     * @param response
     * @return
     */
    @PatchMapping("/update")
    public ResponseEntity updateArkSystemCommentExtend(
            @Valid @RequestBody CommentExtendFormDTO formDTO,
            BindingResult result,
            HttpServletResponse response
    ) {

        commentExtendService.updateArkSystemCommentExtend(formDTO);
        return ResponseUtils.ok("评论扩展修改成功").build();
    }

    /**
     * 删除单个评论扩展
     *
     * @param response
     */
    @DeleteMapping("/delete")
    public ResponseEntity deleteArkSystemCommentExtend(
            @RequestBody CommentExtendFormDTO formDTO,
            HttpServletResponse response
    ) {

        commentExtendService.deleteArkSystemCommentExtend(formDTO);
        return ResponseUtils.ok("评论扩展删除成功").build();
    }
    /**
     * 根据link_id，type，member_id 删除单个评论扩展
     *
     * @param response
     */
    @PostMapping("/deleteBy")
    public ResponseEntity deleteCommentExtend(
            @RequestBody CommentExtendFormDTO formDTO,
            HttpServletResponse response
    ) {

        commentExtendService.deleteBy(formDTO);
        return ResponseUtils.ok("评论扩展删除成功").build();
    }
}
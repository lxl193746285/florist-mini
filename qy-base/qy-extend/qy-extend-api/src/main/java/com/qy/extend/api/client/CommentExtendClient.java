package com.qy.extend.api.client;

import com.qy.extend.api.dto.CommentExtendDTO;
import com.qy.extend.api.dto.CommentExtendFormDTO;
import com.qy.extend.api.dto.CommentExtendQueryDTO;
import com.qy.extend.api.entity.CommentExtendEntity;
import com.qy.rest.pagination.SimplePageImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

/**
 * 评论接口
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base", contextId = "qy-system-comment-extend")
public interface CommentExtendClient {
    /**
     * 获取评论扩展列表
     *
     * @param queryDTO
     * @return
     */
    @GetMapping("/v4/api/extends/list")
    SimplePageImpl<CommentExtendDTO> getArkSystemCommentExtends(@SpringQueryMap CommentExtendQueryDTO queryDTO);

    /**
     * 获取单个评论扩展
     * @param id
     * @return
     */
    @GetMapping("/v4/api/extends/{id}")
    CommentExtendDTO getArkSystemCommentExtend(@PathVariable(value = "id") Long id);

    /**
     * 创建单个评论扩展
     *
     * @param formDTO
     * @return
     */
    @PostMapping("/v4/api/extends/add")
    CommentExtendEntity createArkSystemCommentExtend(@RequestBody CommentExtendFormDTO formDTO);

    /**
     * 修改单个评论扩展
     *
     * @return
     */
    @PatchMapping("/v4/api/extends/update")
    CommentExtendEntity updateArkSystemCommentExtend(@RequestBody CommentExtendFormDTO formDTO);

    /**
     * 删除单个评论扩展

     */
    @DeleteMapping("/v4/api/extends/delete")
    CommentExtendEntity deleteArkSystemCommentExtend(@RequestBody CommentExtendFormDTO formDTO);

    /**
     * 获取单个评论扩展
     *
     * @param formDTO
     * @return
     */
    @GetMapping("/v4/api/extends/getCommentExtend")
    CommentExtendDTO getCommentExtend(@RequestBody CommentExtendFormDTO formDTO);




    /**
     * 根据link_id，type，member_id 删除单个评论扩展
     *
     */
    @PostMapping("/v4/api/extends/deleteBy")
    CommentExtendEntity deleteCommentExtend( @RequestBody CommentExtendFormDTO formDTO);






}

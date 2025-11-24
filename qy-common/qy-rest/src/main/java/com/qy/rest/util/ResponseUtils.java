package com.qy.rest.util;

import com.qy.rest.constant.RestHeaderConstant;
import com.qy.rest.pagination.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

/**
 * 通用响应实体工具类
 *
 * @author legendjw
 */
public class ResponseUtils {
    /**
     * 创建一个指定http状态的响应
     *
     * @param status
     * @return
     */
    public static ResponseEntity.BodyBuilder status(HttpStatus status) {
        return ResponseEntity.status(status);
    }

    /**
     * 创建一个指定http状态的响应并设置响应的消息信息
     *
     * @param status
     * @return
     */
    public static ResponseEntity.BodyBuilder status(HttpStatus status, String message) {
        String encodeMessage = encodeURIComponent(message);
        return status(status).header(RestHeaderConstant.CUSTOM_MESSAGE, encodeMessage);
    }

    /**
     * 创建一个200响应
     *
     * @return
     */
    public static ResponseEntity.BodyBuilder ok() {
        return status(HttpStatus.OK);
    }

    /**
     * 创建一个200响应并设置响应的消息信息
     *
     * @param message
     * @return
     */
    public static ResponseEntity.BodyBuilder ok(String message) {
        return status(HttpStatus.OK, message);
    }

    /**
     * 创建一个200响应并设置响应的分页信息
     *
     * @param page
     * @return
     */
    public static ResponseEntity.BodyBuilder ok(Page page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(RestHeaderConstant.PAGINATION_CURRENT_PAGE, String.valueOf(page.getPage()));
        headers.add(RestHeaderConstant.PAGINATION_PER_PAGE, String.valueOf(page.getPageSize()));
        headers.add(RestHeaderConstant.PAGINATION_PAGE_COUNT, String.valueOf(page.getTotalPages()));
        headers.add(RestHeaderConstant.PAGINATION_TOTAL_COUNT, String.valueOf(page.getTotalRecords()));
        return status(HttpStatus.OK).headers(headers);
    }

    /**
     * 创建一个201响应
     *
     * @return
     */
    public static ResponseEntity.BodyBuilder created() {
        return status(HttpStatus.CREATED);
    }

    /**
     * 创建一个201响应并设置消息信息
     *
     * @return
     */
    public static ResponseEntity.BodyBuilder created(String message) {
        return status(HttpStatus.CREATED, message);
    }

    /**
     * 创建一个201响应并设置跳转地址
     *
     * @param location
     * @return
     */
    public static ResponseEntity.BodyBuilder created(URI location) {
        return status(HttpStatus.CREATED).location(location);
    }

    /**
     * 创建一个204响应
     *
     * @return
     */
    public static ResponseEntity.HeadersBuilder<?> noContent() {
        return status(HttpStatus.NO_CONTENT);
    }

    /**
     * 创建一个204响应并设置响应的消息
     *
     * @return
     */
    public static ResponseEntity.HeadersBuilder<?> noContent(String message) {
        return status(HttpStatus.NO_CONTENT, message);
    }

    /**
     * 创建一个400响应
     *
     * @return
     */
    public static ResponseEntity.BodyBuilder badRequest() {
        return status(HttpStatus.BAD_REQUEST);
    }

    /**
     * 创建一个400响应并设置消息信息
     *
     * @param message
     * @return
     */
    public static ResponseEntity.BodyBuilder badRequest(String message) {
        return status(HttpStatus.BAD_REQUEST, message);
    }

    /**
     * 创建一个404响应
     *
     * @return
     */
    public static ResponseEntity.HeadersBuilder<?> notFound() {
        return status(HttpStatus.NOT_FOUND);
    }

    /**
     * 创建一个404响应并设置消息信息
     *
     * @return
     */
    public static ResponseEntity.HeadersBuilder<?> notFound(String message) {
        return status(HttpStatus.NOT_FOUND, message);
    }

    /**
     * 创建一个422响应
     *
     * @return
     */
    public static ResponseEntity.BodyBuilder unprocessableEntity() {
        return status(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * 创建一个422响应并设置消息信息
     *
     * @return
     */
    public static ResponseEntity.BodyBuilder unprocessableEntity(String message) {
        return status(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }

    /**
     * 创建一个500请求
     *
     * @return
     */
    public static ResponseEntity.BodyBuilder internalServerError() {
        return status(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 创建一个500请求并设置消息信息
     *
     * @return
     */
    public static ResponseEntity.BodyBuilder internalServerError(String message) {
        return status(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    /**
     * 中文utf8编码
     *
     * @param value
     * @return
     */
    public static String encodeURIComponent(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}

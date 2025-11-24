package com.qy.rest.advice;

import com.qy.rest.exception.BusinessException;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.UnauthorizedException;
import com.qy.rest.exception.ValidationException;
import com.qy.rest.util.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 控制器全局异常处理
 *
 * @author legendjw
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = { UnauthorizedException.class })
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
        return ResponseUtils
                .status(HttpStatus.UNAUTHORIZED, ex.getMessage())
                .body(new ErrorBody(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), ex.getMessage()));
    }

    @ExceptionHandler(value = { ValidationException.class })
    public ResponseEntity<Object> handleValidationException(ValidationException ex) {
        return ResponseUtils
                .unprocessableEntity(ex.getMessage())
                .body(new ErrorBody(HttpStatus.UNPROCESSABLE_ENTITY.value(), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(), ex.getMessage()));
    }

    @ExceptionHandler(value = { NotFoundException.class })
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        return ResponseUtils
                .notFound(ex.getMessage()).build();
    }

    @ExceptionHandler(value = { BusinessException.class })
    public ResponseEntity<Object> handleBusinessException(BusinessException ex) {
        return ResponseUtils
                .badRequest(ex.getMessage())
                .body((new ErrorBody(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage())));
    }

    @ExceptionHandler(value = { AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseUtils
                .status(HttpStatus.FORBIDDEN, "没有权限访问")
                .body(new ErrorBody(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), "没有权限访问"));
    }

    @ExceptionHandler(value = { RuntimeException.class })
    public ResponseEntity<Object> handledRuntimeException(RuntimeException ex) {
        logger.error(ex.getMessage(), ex);

        return ResponseUtils
                .status(HttpStatus.BAD_REQUEST, ex.getMessage())
                .body((new ErrorBody(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage())));
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handledException(Exception ex) {
        logger.error(ex.getMessage(), ex);

        return ResponseUtils
                .internalServerError("服务器发生异常")
                .body((new ErrorBody(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage())));
    }
}
package com.qy.feign.config;

import com.qy.rest.exception.BusinessException;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 自定义错误处理
 *
 * @author legendjw
 */
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());
        //从header头部尝试获取错误信息
        String message = "服务器发生异常";
        if (response.headers().containsKey("x-message")) {
            try {
                message = URLDecoder.decode(response.headers().get("x-message").toString(), "UTF-8");
                message = message.replace("[", "");
                message = message.replace("]", "");
            } catch (UnsupportedEncodingException e) {
            }
        }

        if (responseStatus.equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
            throw new ValidationException(message);
        }
        else if (responseStatus.equals(HttpStatus.NOT_FOUND)) {
            throw new NotFoundException(message);
        }
        else if (responseStatus.is4xxClientError()) {
            throw new BusinessException(message);
        }

        return new Default().decode(methodKey, response);
    }
}

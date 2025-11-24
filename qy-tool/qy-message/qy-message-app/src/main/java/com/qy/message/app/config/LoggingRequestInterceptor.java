package com.qy.message.app.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@Slf4j
class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequestDetails(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponseDetails(response);
        return response;
    }

    private void logRequestDetails(HttpRequest request, byte[] body) {
        log.info("==> Request: {} {}", request.getMethod(), request.getURI());
        log.info("==> Request Headers: {}", request.getHeaders());
        log.info("==> Request Body: {}", new String(body));
    }

    private void logResponseDetails(ClientHttpResponse response) throws IOException {
        log.info("<== Response Status Code: {}", response.getStatusCode());
        log.info("<== Response Headers: {}", response.getHeaders());
    }
}
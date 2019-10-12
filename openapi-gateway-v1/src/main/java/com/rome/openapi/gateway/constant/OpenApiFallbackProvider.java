/*
 * Filename OpenApiFallbackProvider.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.gateway.constant;

import com.google.gson.Gson;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import com.rome.arch.core.clientobject.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 为路线提供Hystrix回退
 * 当某个下游服务断开时提供回退
 *
 * @author kongweixiang
 * @since 1.0.0_2018/8/1
 */
@Component
public class OpenApiFallbackProvider implements FallbackProvider {
    private Logger log = LoggerFactory.getLogger(OpenApiFallbackProvider.class);

    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        log.error("服务{}已断开", route, cause);
        if (cause instanceof HystrixTimeoutException) {
            return response(HttpStatus.GATEWAY_TIMEOUT, route);
        } else {
            return response(HttpStatus.INTERNAL_SERVER_ERROR, route);
        }
    }

    private ClientHttpResponse response(final HttpStatus status, String route) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return status;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return status.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return status.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() throws IOException {
                Gson gson = new Gson();
                return new ByteArrayInputStream(gson.toJson(Response.builderFail(CodeMessage.ROUTE_FAIL.getCode(), route + CodeMessage.ROUTE_FAIL.getMessage())).getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}

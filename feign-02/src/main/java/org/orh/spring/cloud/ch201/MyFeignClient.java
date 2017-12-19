package org.orh.spring.cloud.ch201;

import feign.Client;
import feign.Request;
import feign.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * 使用自定义的 Client 来替代 Feign HttpURLConnect 发起连接
 */
public class MyFeignClient implements Client {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Response execute(Request request, Request.Options options) throws IOException {
        logger.info("use MyFeignClient...");

        CloseableHttpClient httpClient = HttpClients.createDefault();
        final String method = request.method();
        Response response = null;

        // feign request -> http client request
        // method、uri、headers... 挨个获取设置
        HttpRequestBase httpRequestBase = new HttpRequestBase() {
            @Override
            public String getMethod() {
                return method;
            }
        };
        try {
            httpRequestBase.setURI(new URI(request.url()));
            request.headers().entrySet().forEach(entry -> {
                entry.getValue().stream().forEach(e -> httpRequestBase.addHeader(entry.getKey(), e));
            });
            HttpResponse httpResponse = httpClient.execute(httpRequestBase);
            byte[] responseBody = EntityUtils.toByteArray(httpResponse.getEntity());

            // http response -> feign response
            response = Response.builder()
                    .body(responseBody)
                    .status(httpResponse.getStatusLine().getStatusCode())
                    .headers(new HashMap<>())
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException("请求地址错误，", e);
        }
        return response;
    }
}

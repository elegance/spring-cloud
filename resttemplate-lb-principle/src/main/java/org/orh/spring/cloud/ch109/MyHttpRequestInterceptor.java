package org.orh.spring.cloud.ch109;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 实现对-client 端发出的 http request 进行拦截，模拟解析服务名到具体的HostPort
 */
@Component
public class MyHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logger.info("==========http request 进入自定义拦截器");
        logger.info("请求的原URI: {}", request.getURI());

        HttpRequest newRequest = new LoadBalancedRequest(request);
        logger.info("请求的新URI: {}", newRequest.getURI());

        return execution.execute(new LoadBalancedRequest(newRequest), body);
    }


    /**
     * 解析 URI 中的服务名解析出来
     */
    class LoadBalancedRequest implements HttpRequest {
        private HttpRequest sourceRequest;

        public LoadBalancedRequest(HttpRequest request) {
            this.sourceRequest = request;
        }

        @Override
        public HttpMethod getMethod() {
            return sourceRequest.getMethod();
        }

        @Override
        public URI getURI() {

            URI newUri = null;
            try {
                // 这里就固定访问：localhost 了， 实际的实现应该是 截取出服务名，通过 Eureka api 得到服务列表，来替换uri
                newUri = new URI("http://localhost:8080/hello");
            } catch (URISyntaxException e) {
                throw new RuntimeException("URI 地址错误，", e);
            }
            return newUri;
        }

        @Override
        public HttpHeaders getHeaders() {
            return sourceRequest.getHeaders();
        }
    }
}

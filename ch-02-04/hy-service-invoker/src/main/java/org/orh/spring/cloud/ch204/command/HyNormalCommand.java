package org.orh.spring.cloud.ch204.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HyNormalCommand extends HystrixCommand<String> {

    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet("http://localhost:8080/normalHello");

    public HyNormalCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
    }

    @Override
    protected String run() throws Exception {
        HttpResponse response = httpClient.execute(httpGet);
        return EntityUtils.toString(response.getEntity());
    }
}

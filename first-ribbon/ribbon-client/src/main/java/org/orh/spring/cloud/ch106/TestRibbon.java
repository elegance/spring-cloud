package org.orh.spring.cloud.ch106;

import com.netflix.client.ClientFactory;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.niws.client.http.RestClient;

import java.net.URI;

public class TestRibbon {

    public static void main(String[] args) throws Exception {
        ConfigurationManager.getConfigInstance().setProperty("my-client.ribbon.listOfServers", "127.0.0.1:8080, 127.0.0.1:8084");
        System.out.println(ConfigurationManager.getConfigInstance().getProperties("my-client-ribbon.listOfServers"));

        RestClient restClient = (RestClient) ClientFactory.getNamedClient("my-client");
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("/user")).build();

        for (int i = 0; i < 20; i++) {
            HttpResponse response = restClient.executeWithLoadBalancer(request);
//            System.out.println(response.getEntity(String.class));
            System.out.println("Status code for " + response.getRequestedURI() + "  :" + response.getStatus());
        }
    }
}

package org.orh.spring.cloud.ch108;

import org.springframework.cloud.netflix.ribbon.RibbonClient;

@RibbonClient(name = "service-provider", configuration = MyConfig.class)
public class MyClient {
}

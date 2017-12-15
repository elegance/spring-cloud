package org.orh.spring.cloud.ch105;

import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

/**
 * 自定义健康检查器
 */
@Component
public class MyHealthCheckHandler implements HealthCheckHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MyHealthIndicator healthIndicator;

    @Override
    public InstanceInfo.InstanceStatus getStatus(InstanceInfo.InstanceStatus instanceStatus) {
        logger.info("execute myHealthCheckHandler...");
        if (healthIndicator.health().getStatus().equals(Status.DOWN)) {
            return InstanceInfo.InstanceStatus.DOWN;
        }
        return InstanceInfo.InstanceStatus.UP;
    }
}

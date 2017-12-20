package org.orh.spring.cloud.ch205;

import com.netflix.config.ConfigurationManager;

public class TimeoutRunMain {
    public static void main(String[] args) {
        TimeoutCommand command = new TimeoutCommand(10);
        System.out.println(command.execute());

        TimeoutCommand command2 = new TimeoutCommand(2100);
        System.out.println(command2.execute());
    }
}

package org.orh.spring.cloud.ch204;

import org.orh.spring.cloud.ch204.command.HyTimeoutCommand;

public class HyTimeoutMain {
    public static void main(String[] args) throws Exception {
        HyTimeoutCommand command = new HyTimeoutCommand();
        System.out.println(command.execute());
    }
}

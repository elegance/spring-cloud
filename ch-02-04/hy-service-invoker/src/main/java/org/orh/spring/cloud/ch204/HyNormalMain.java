package org.orh.spring.cloud.ch204;

import org.orh.spring.cloud.ch204.command.HyNormalCommand;

public class HyNormalMain {
    public static void main(String[] args) throws Exception {
        HyNormalCommand command = new HyNormalCommand();
        System.out.println(command.execute());
    }
}

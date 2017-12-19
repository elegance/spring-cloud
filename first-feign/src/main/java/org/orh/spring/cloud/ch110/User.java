package org.orh.spring.cloud.ch110;

public class User {
    private String userName;
    private int age;
    private String serverNodeInfo;

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                ", serverNodeInfo='" + serverNodeInfo + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getServerNodeInfo() {
        return serverNodeInfo;
    }

    public void setServerNodeInfo(String serverNodeInfo) {
        this.serverNodeInfo = serverNodeInfo;
    }
}

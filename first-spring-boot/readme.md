## Spirng boot
几点新的细节
#### 1. 读取配置文件信息
```java
@Autowired

private ApplicationContext ctx;
ctx.getPropert
```
#### 2. 自定配置文件位置
```java
new SpringApplicationBuilder(App.class)
                .properties("spring.config.location=classpath:/abc.properties")
                .run(args);
```

#### 3. 运行时指定 `profile`
```java
new SpringApplicationBuilder(App.class)
    .properties("spring.profiles.active=prod")
    .run(args);
```
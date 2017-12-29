## 配置中心(三)配置加密

用于应对敏感数据，如数据库密码、用户名等的配置。

* 依赖JCE (Java Cryptography Extensions)
* 支持对称、非对称加密

源码：**基于[ch-04-02](../ch-04-02/)**

#### 服务提供加密、解密端点

在配置好加密配置后，Config Server 会提供一下端点：

1. `/encrypt`, 携带明文请求体post请求到这个端点，会返回请求体加密的内容
2. `/decrypt`, 携带密文请求体post请求到这个端点，会返回请求体解密的内容

使用`curl`的方式，如 `curl http://localhost:8888/encrypt -d hello`来测试，或者使用postman等工具来测试。

现在我们还没有配置，接下来我们看下怎么来配置

#### 对称加密方式

Config Server application.yml 配置如下：

```yaml
encrypt:
  key: aggred-cipher-abc
```

测试加密服务状态：

* 用浏览器直接访问：`http://localhost:8888/encrypt/status`
  * 如果返回：`status: OK`，那就恭喜一切正常
  * 如果**`错误404：No key was installed for encryption service`**，这是版本上的一个坑，**降级Spring Cloud Dalston.SR1 可以解决**，我的项目是**采用了将`spring-cloud-config-server`明确指定改为`1.3.1.RELEASE`**来解决的。

测试测试加密：

* `curl http://localhost:8888/encrypt -d hello` 返回密文：`81aea90f5a1ea5f709bad580f9ad86438724301a97528c36ef9707c8820faeea`，多次访问可以发现同明文返回的密文是不一样的

解密：

* `**curl http://localhost:8888/decrypt -d 81aea90f5a1ea5f709bad580f9ad86438724301a97528c36ef9707c8820faeea**` 返回明文`hello`

####  测试配置文件的解密

针对`.properties`增加密文前增加`{cipher}`即可，如`test.name=${cipher}xxxxxxx`。

针对`.yml`文件，需要用单引号包裹整个值，如`password: '${cipher}xxxx'`。

我们下面的命令来新增配置文件，提交到config server，然后用浏览器访问测试下：

1. 往git 仓库`config-repo`新增两个配置文件：

   ```bash
   $ echo test.name={cipher}81aea90f5a1ea5f709bad580f9ad86438724301a97528c36ef9707c8820faeea > application-dev.properties
   $ echo -e "test:\n  name: '{cipher}$81aea90f5a1ea5f709bad580f9ad86438724301a97528c36ef9707c8820faeea'" > application-prod.yml
   $ git add .
   $ git commit -m "add encrypt text file"
   ```

2. 访问浏览器测试

   * http://localhost:8888/application-dev.properties , 会得到 **`application.properties` + `application-dev.properties`**并且会将密文解密范围
   * http://localhost:8888/application-dev.yml，会得到 **`application.properties` + `application-dev.yml`**并且会将密文解密范围

#### 使用非对称加密

1. 使用Java 自动工具`keytool`生成密钥对:

   ```bash
   > keytool -genkeypair -alias mytestkey -keyalg RSA  -dname "CN=Web Server,OU=Unit,O=Organization,L=City,S=State,C=cn" -keypass changeme -keystore D:\orh-server.jks -storepass changeme
   ```

2. 服务器`application.yml` 配置：

   ```yaml
   encrypt:
     key-store:
       location: classpath:/orh-server.jks
       password: changeme
       alias: mytestkey
       secret: changeme
   ```

这里的测试和对称加密的测试一样，就省略了。


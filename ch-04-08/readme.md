# Spring Data MongDB

#### 安装

MongoDB安装文件：根据自己的操作系统到[MongoDB的官网](https://www.mongodb.com/download-center#community)下载对应版本。

可视化操作工具：	[Robo3T](https://robomongo.org/download)

我的机器上为了一些开发环境的方便，安装了Docker，所以这里使用的是Docker安装MongoDB的方式。

中文版的安装与学习文档可以参考[这里](http://www.runoob.com/mongodb/mongodb-linux-install.html)。

```bash
$ docker search mongodb
$ docker pull mongo
$ docker run --name mongo-server -d mongo
$ docker ps
```

进入mogo容器命令行创建数据库：

```bash
$ docker exec -it mongo-server bash
$ mongo
> show dbs
admin   0.000GB
config  0.000GB
local   0.000GB
> use my_db # 切换/创建数据库
> db.Person.insert({name: "xiaoxiao", age: NumberInt(33), company: "AAA Company"})
> db.Person.insert({name: "Jim", age: NumberInt(18), company: "BBB Company"})
> show dbs
```

#### 构建Spring Boot项目

依赖：

* `org.springframework.boot/spring-boot-starter-web`


* `org.springframework.boot/spring-boot-starter-data-mongodb`

#### 数据层Repository

更全面系统的文档请直接查看[官网文档](https://docs.spring.io/spring-data/mongodb/docs/1.10.9.RELEASE/reference/html/)。

* `public interface PersonRepository extends MongoRepository<Person, String>` 继承`MongoRepository` 即有了一系列的基础方法
* 自定义查询实现，类似于JPA，可以自定义接口、自定义实现类，在让repository继承
* 根据属性名称作为方法名查询属性
* 查询方法：findByXX，支持的关键字请参[考官方文档](https://docs.spring.io/spring-data/mongodb/docs/1.10.9.RELEASE/reference/html/#mongodb.repositories.queries)
* @Query注解

示例：

```java
public interface PersonRepository extends MongoRepository<Person, String>, PersonRepositoryCustom{

    // 属性名作为方法名
   List<Person> name(String name);

   // 约定规则的查询方法
   List<Person> findByAgeGreaterThan(int age);

   @Query(value = "{ 'name': ?0, 'age': ?1}", fields = "{'name': 1, 'age': 1}")
   List<Person> findByTheNameAndAge(String name, int age);
}
```



#### 附录1：MongoDB基本概念

| SQL术语/概念    | MongoDB术语/概念 | 解释/说明                   |
| ----------- | ------------ | ----------------------- |
| database    | database     | 数据库                     |
| table       | collection   | 数据库表/集合                 |
| row         | document     | 数据记录行/文档                |
| column      | field        | 数据字段/域                  |
| index       | index        | 索引                      |
| table joins |              | 表连接,MongoDB不支持          |
| primary key | primary key  | 主键,MongoDB自动将_id字段设置为主键 |

与关系型数据库的术语对应：

| RDBMS | MongoDB                     |
| ----- | --------------------------- |
| 数据库   | 数据库                         |
| 表格    | 集合                          |
| 行     | 文档                          |
| 列     | 字段                          |
| 表联合   | 嵌入文档                        |
| 主键    | 主键 (MongoDB 提供了 key 为 _id ) |

#### 附录2：MongoDB基本命令

```bash
> help : 输入help可以看到基本操作命令
> show dbs:显示数据库列表 
> show collections：显示当前数据库中的集合（类似关系数据库中的表） 
> show users：显示用户
> 
> use <db name>：切换当前数据库，这和MS-SQL里面的意思一样 
> db.help()：显示数据库操作命令，里面有很多的命令 
> db.foo.help()：显示集合操作命令，同样有很多的命令，foo指的是当前数据库下，一个叫foo的集合，并非真正意义上的命令 
> db.foo.find()：对于当前数据库中的foo集合进行数据查找（由于没有条件，会列出所有数据） 
> db.foo.find( { a : 1 } )：对于当前数据库中的foo集合进行查找，条件是数据中有一个属性叫a，且a的值为1
```


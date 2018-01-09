# JPA数据查询

可以参考: 

[Spring4all-如何对 JPA 或者 MyBatis 进行技术选型](http://www.spring4all.com/article/391)

[Spring-data官方文档](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)

#### 1. 属性查询

直接在`Repository`接口中书写以“属性"为名称的方法。


#### 2. [方法名查询](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation)

`Repository`中编写符合约定规则的方法，详细规则请点击链接参考。



#### 3. JPQL 查询

使用`@Query(select ...)"`来书写`JPQL`查询语句

#### 4. 附例：

```java
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    // 属性查询
    List<User> username(String username);

    // 方法名规则查询：https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    User findByUsername(String username);

    // JPQL 查询
    @Query("select u from User u where u.username = ?1")
    List<User> findJPQLQuery(String name);

    // 原生SQL
    @Query(value = "select * from tb_user where username =:name", nativeQuery = true)
    List<User> findNativeQuery(@Param("name") String name);
}
```

注：`H2`数据库默认可以没有用户名密码、密码，但是如果通过Spring DataSource 来自动创建嵌入数据库时会有默认的用户名，具体见`DataSourceProperties`的`determineUsername`方法，可以看到默认用户名是**sa**，默认密码是空，你从`H2 Console`登录时可能用得到。
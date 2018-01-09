package org.orh.spring.data.repository;

import org.orh.spring.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

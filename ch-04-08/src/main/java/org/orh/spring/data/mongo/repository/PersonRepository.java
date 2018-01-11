package org.orh.spring.data.mongo.repository;

import org.orh.spring.data.mongo.entity.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PersonRepository extends MongoRepository<Person, String>, PersonRepositoryCustom{

    // 属性名作为方法名
   List<Person> name(String name);

   // 约定规则的查询方法
   List<Person> findByAgeGreaterThan(int age);

   @Query(value = "{ 'name': ?0, 'age': ?1}", fields = "{'name': 1, 'age': 1}")
   List<Person> findByTheNameAndAge(String name, int age);
}

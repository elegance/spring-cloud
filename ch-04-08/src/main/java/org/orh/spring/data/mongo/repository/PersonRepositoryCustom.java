package org.orh.spring.data.mongo.repository;

import org.orh.spring.data.mongo.entity.Person;

import java.util.List;

public interface PersonRepositoryCustom {
    List<Person> myQuery();
}

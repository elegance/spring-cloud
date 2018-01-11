package org.orh.spring.data.mongo.service;

import org.orh.spring.data.mongo.entity.Person;
import org.orh.spring.data.mongo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public List<Person> myQuery() {
        return personRepository.myQuery();
    }

    public List<Person> findPersonAttrName(String name) {
        return personRepository.name(name);
    }

    public List<Person> findByAgeGreaterThan(int age) {
        return personRepository.findByAgeGreaterThan(age);
    }

    public List<Person> findByTheNameAndAge(String name, int age) {
        return personRepository.findByTheNameAndAge(name, age);
    }
}

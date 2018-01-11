package org.orh.spring.data.mongo.controller;

import org.orh.spring.data.mongo.entity.Person;
import org.orh.spring.data.mongo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/findAll")
    public List<Person> findAll() {
        return personService.findAll();
    }

    @GetMapping("/myQuery")
    public List<Person> myQuery() {
        return personService.myQuery();
    }

    @GetMapping("/name")
    public List<Person> name(@RequestParam("p") String name) {
        return personService.findPersonAttrName(name);
    }

    @GetMapping("/ageGt")
    public List<Person> name(@RequestParam("p") int age) {
        return personService.findByAgeGreaterThan(age);
    }

    @GetMapping("/query")
    public List<Person> query(String name, int age) {
        return personService.findByTheNameAndAge(name, age);
    }
}

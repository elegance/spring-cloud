package org.orh.spring.data.mongo.repository;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.orh.spring.data.mongo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

public class PersonRepositoryImpl implements PersonRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Person> myQuery() {
        return mongoTemplate.execute(Person.class, (collection) -> {
            List<Person> result = new ArrayList<>();
            DBCursor cursor = collection.find();
            while (cursor.hasNext()) {
                DBObject data = cursor.next();
                Person p = new Person();
                ObjectId objectId = (ObjectId) data.get("_id");
                p.setId(objectId.toHexString());
                p.setName((String) data.get("name"));
                p.setAge((Integer) data.get("age"));
                p.setCompany((String) data.get("company"));
                result.add(p);
            }
            return result;
        });
    }
}

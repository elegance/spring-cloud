package org.orh.spring.data.repository;

import org.orh.spring.data.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    public List<User> myQuery() {
        return em.createNativeQuery("select * from tb_user").getResultList();
    }
}

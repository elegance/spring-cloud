package org.orh.spring.data.repository;

import org.orh.spring.data.entity.User;

import java.util.List;

public interface UserRepositoryCustom {

   List<User> myQuery();
}

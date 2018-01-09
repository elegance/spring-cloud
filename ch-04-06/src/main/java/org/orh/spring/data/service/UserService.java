package org.orh.spring.data.service;

import org.orh.spring.data.entity.User;
import org.orh.spring.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> queryAllUser() {
        return userRepository.findAll();
    }

    public List<User> customQa() {
        return userRepository.myQuery();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public User findByUserId(long id) {
        return userRepository.findOne(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findByUsername2(String username) {
        return userRepository.username(username);
    }

    public List<User> findJPQLName(String name) {
        return userRepository.findJPQLQuery(name);
    }

    public List<User> findNativeQuery(String name) {
        return userRepository.findNativeQuery(name);
    }
}

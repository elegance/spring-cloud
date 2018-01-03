package org.orh.spring.data.controller;

import org.orh.spring.data.entity.User;
import org.orh.spring.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> user() {
        return userService.queryAllUser();
    }

    @GetMapping("/qaUsers")
    public List<User> qaUser() {
        return userService.customQa();
    }

    @GetMapping("/user/{id:\\d+}")
    public User user(@PathVariable("id") long id) {
        return userService.findByUserId(id);
    }

    @PostMapping("/user")
    public User create(User user) {
        return userService.save(user);
    }

    @PutMapping("/user/{id}")
    public User update(User user, @PathVariable("id") long id) {
        user.setId(id);
        return  userService.update(user);
    }
}

package io.github.mickey.dynamic.datasource.v1.controller;

import io.github.mickey.dynamic.datasource.v1.domain.User;
import io.github.mickey.dynamic.datasource.v1.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mickey
 * @date 3/6/21 14:33
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public String addUser() {
        User user = fakeUser();
        userService.addUser(user);
        return "ok";
    }

    private User fakeUser() {
        final User user = new User();
        user.setNickname("jack");
        user.setMobile("18816899195");
        user.setStatus(1);
        user.setAvatar("http://baidu.com/iamges/112347.png");
        user.setPassword(System.nanoTime() + "");
        return user;
    }

}

package io.github.mickey.shardingsphere.v1.service;


import io.github.mickey.shardingsphere.v1.domain.User;

import java.util.List;

/**
 * @author mickey
 * @date 3/6/21 14:31
 */
public interface UserService {


    void addUser(User user);

    List<User> getUsers();
}

package io.github.mickey.dynamic.datasource.v2.service;


import io.github.mickey.dynamic.datasource.v2.domain.User;

import java.util.List;

/**
 * @author mickey
 * @date 3/6/21 14:31
 */
public interface UserService {


    void addUser(User user);

    List<User> getUsers();
}

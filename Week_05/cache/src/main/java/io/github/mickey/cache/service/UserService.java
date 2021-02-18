package io.github.mickey.cache.service;

import io.github.mickey.cache.annotation.Cache;
import io.github.mickey.cache.domain.User;
import io.github.mickey.cache.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mickey
 * @date 2/18/21 00:32
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Cache(3)
    public User getUser(int id) {
        return userRepository.getUser(id);
    }

    @Cache(3)
    public List<User> getUsers() {
        return userRepository.getUsers();
    }


}

package io.github.mickey.shardingsphere.v1.service;

import io.github.mickey.shardingsphere.v1.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mickey
 * @date 3/6/21 14:31
 */
@Service
@Slf4j
public class UserServiceImpl implements
        UserService{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addUser(User user) {
        jdbcTemplate.update("insert into t_user (nickname, mobile, status,avatar, password,created_time, updated_time) values (?,?,?,?,?,now(),now())",
                user.getNickname(),
                user.getMobile(),
                user.getStatus(),
                user.getAvatar(),
                user.getPassword());
        final List<User> users = getUsers();
        log.info("get users:");
        users.forEach(e ->{
            log.info("user:{}", user);
        });

    }

    @Override
    public List<User> getUsers() {
        return jdbcTemplate.query("select * from t_user", new BeanPropertyRowMapper<>(User.class));
    }
}

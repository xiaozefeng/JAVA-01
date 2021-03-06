package io.github.mickey.dynamic.datasource.v1.service;

import io.github.mickey.dynamic.datasource.v1.annotation.DS;
import io.github.mickey.dynamic.datasource.v1.context.JdbcTemplateContext;
import io.github.mickey.dynamic.datasource.v1.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mickey
 * @date 3/6/21 14:31
 */
@Service
public class UserServiceImpl implements
        UserService{


    @Override
    @DS(value = DS.Operation.MODIFY_DATA)
    public void addUser(User user) {
        JdbcTemplate jdbcTemplate = JdbcTemplateContext.get();
        jdbcTemplate.update("insert into t_user (nickname, mobile, status,avatar, password,created_time, updated_time) values (?,?,?,?,?,now(),now())",
                user.getNickname(),
                user.getMobile(),
                user.getStatus(),
                user.getAvatar(),
                user.getPassword());
    }

    @Override
    @DS
    public List<User> getUsers() {
        JdbcTemplate jdbcTemplate = JdbcTemplateContext.get();
        return jdbcTemplate.query("select * from t_user", new BeanPropertyRowMapper<>(User.class));
    }
}

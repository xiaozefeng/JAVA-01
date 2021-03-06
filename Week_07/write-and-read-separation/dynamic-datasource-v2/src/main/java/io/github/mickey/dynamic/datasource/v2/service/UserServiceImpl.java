package io.github.mickey.dynamic.datasource.v2.service;

import io.github.mickey.dynamic.datasource.v2.annotation.DS;
import io.github.mickey.dynamic.datasource.v2.context.DataSourceRoutingContext;
import io.github.mickey.dynamic.datasource.v2.domain.User;
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
public class UserServiceImpl implements
        UserService{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @DS(value = DataSourceRoutingContext.SLAVE)
    public void addUser(User user) {
        jdbcTemplate.update("insert into t_user (nickname, mobile, status,avatar, password,created_time, updated_time) values (?,?,?,?,?,now(),now())",
                user.getNickname(),
                user.getMobile(),
                user.getStatus(),
                user.getAvatar(),
                user.getPassword());
    }

    @Override
    @DS(value = DataSourceRoutingContext.SLAVE)
    public List<User> getUsers() {
        return jdbcTemplate.query("select * from t_user", new BeanPropertyRowMapper<>(User.class));
    }
}

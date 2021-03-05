package io.github.mickey.jdbc.dataprepared;
import io.github.mickey.jdbc.domain.User;

import java.util.ArrayList;
import java.util.List;

public class MassUserGenerator {

    public static List<User> generate( int count) {
        List<User> users = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setNickname("name" + i);
            user.setMobile("18816899195");
            user.setStatus(1);
            user.setAvatar("http://www.baidu.com/image"+i);
            user.setPassword(System.nanoTime()+"");
            users.add(user);
        }
        return users;
    }
}

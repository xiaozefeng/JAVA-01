package io.github.mickey.cache;

import io.github.mickey.cache.config.AppConfig;
import io.github.mickey.cache.domain.User;
import io.github.mickey.cache.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * @author mickey
 * @date 2/18/21 00:23
 */
public class Application {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        final UserService userService = ac.getBean(UserService.class);
        runByTimes(10, () -> {
            final User u = userService.getUser(4);
            System.out.println(u);
        });


        runByTimes(10, () ->{
            final List<User> users = userService.getUsers();
            System.out.println(users);
        });


    }

    private static void runByTimes(int times, Runnable r) {
        for (int i = 0; i < times; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r.run();
        }
    }
}

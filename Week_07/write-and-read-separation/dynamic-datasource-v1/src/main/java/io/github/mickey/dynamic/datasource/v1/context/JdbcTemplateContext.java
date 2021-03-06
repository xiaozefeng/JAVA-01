package io.github.mickey.dynamic.datasource.v1.context;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author mickey
 * @date 3/6/21 14:50
 */
public class JdbcTemplateContext {

    private static ThreadLocal<JdbcTemplate> holder = new ThreadLocal<>();

    public static void set(JdbcTemplate t) {
        holder.set(t);
    }

    public static JdbcTemplate get() {
        return holder.get();
    }

    public static void remove() {
        holder.remove();
    }
}

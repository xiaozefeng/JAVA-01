package io.github.mickey.week5.aop;

/**
 * @author mickey
 * @date 2/8/21 21:51
 */
public interface Interceptor {

    boolean pre();

    void after();
}

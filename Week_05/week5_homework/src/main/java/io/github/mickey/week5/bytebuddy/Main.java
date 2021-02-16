package io.github.mickey.week5.bytebuddy;

/**
 * @author mickey
 * @date 2/15/21 23:34
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Service service = BeanFactory.getBean(Service.class);
        service.bar(1000);
        service.foo(2000);
    }


}

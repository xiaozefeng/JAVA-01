package io.github.mickey.week5.assemble.examples.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author mickey
 * @date 2/11/21 13:05
 */
public class Runner {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:application-assemble-context.xml");
        Company c= ac.getBean(Company.class);
        System.out.println("company name: " + c.getName());
        System.out.println("company boos name: " + c.getBoss().getName());
        System.out.println("company employees: ");
        for (Employee employee : c.getEmployees()) {
            System.out.println("\t"+employee);
        }
    }
}

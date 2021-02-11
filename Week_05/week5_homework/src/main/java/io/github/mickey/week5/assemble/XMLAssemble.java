package io.github.mickey.week5.assemble;

import io.github.mickey.week5.assemble.bean.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author mickey
 * @date 2/11/21 01:25
 */
public class XMLAssemble {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:application-context.xml");
        Student student = ac.getBean(Student.class);
        System.out.println(student);
    }
}

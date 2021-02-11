package io.github.mickey.week5.assemble;

import io.github.mickey.week5.assemble.bean.Student;
import io.github.mickey.week5.assemble.config.AnnotationConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author mickey
 * @date 2/11/21 01:26
 */
public class AnnotationAssemble {

    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AnnotationConfig.class);
        Student s = ac.getBean(Student.class);
        System.out.println(s);
    }
}

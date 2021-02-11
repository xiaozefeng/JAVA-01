package io.github.mickey.week5.assemble.config;

import io.github.mickey.week5.assemble.bean.Address;
import io.github.mickey.week5.assemble.bean.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author mickey
 * @date 2/11/21 10:21
 */
@ComponentScan
@Configuration
public class AnnotationConfig {

    @Bean
    public Student student() {
        Student s = new Student();
        s.setAge(20);
        s.setName("rose");
        s.setAddress(address());
        return s;
    }

    @Bean
    public Address address() {
        Address address = new Address();
        address.setProvince("湖南省");
        address.setCity("娄底市");
        address.setRegion("涟源市茅塘镇");
        return address;
    }
}

package io.github.mickey.week5.assemble.bean;

import lombok.Data;
import lombok.ToString;

/**
 * @author mickey
 * @date 2/11/21 01:27
 */
@Data
@ToString
public class Student {

    private String name;
    private int age;

    private Address address;
}

package io.github.mickey.week5.assemble.examples.bean;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author mickey
 * @date 2/11/21 13:00
 */
@Data
@ToString
public class Company {
    private String name;

    private List<Employee> employees;

    private Boss boss;
}

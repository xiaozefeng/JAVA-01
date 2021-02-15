package com.mickey.x2.starter;

import java.util.List;

/**
 * @author mickey
 * @date 2/15/21 17:04
 */
public class Company {

    private Boss boss;

    private List<Employee> employees;

    public Boss getBoss() {
        return boss;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}

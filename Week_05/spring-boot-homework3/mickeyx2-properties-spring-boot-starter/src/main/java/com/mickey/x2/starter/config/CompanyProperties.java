package com.mickey.x2.starter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author mickey
 * @date 2/15/21 17:07
 */
@ConfigurationProperties(prefix = "mickey.company")
public class CompanyProperties {

    private String employees;

    private String boos;

    public String getEmployees() {
        return employees;
    }

    public void setEmployees(String employees) {
        this.employees = employees;
    }

    public String getBoos() {
        return boos;
    }

    public void setBoos(String boos) {
        this.boos = boos;
    }
}

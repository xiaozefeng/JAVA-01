package com.mickey.x2.starter.config;

import com.mickey.x2.starter.Boss;
import com.mickey.x2.starter.Company;
import com.mickey.x2.starter.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mickey
 * @date 2/15/21 17:09
 */
@Configuration
@EnableConfigurationProperties(CompanyProperties.class)
@ConditionalOnClass(Company.class)
@ConditionalOnProperty(prefix = "mickey.company", value = "enabled", matchIfMissing = true)
public class CompanyAutoConfiguration {

    @Autowired
    private CompanyProperties companyProperties;

    private static final String delimiter = ",";

    private static final String vertical = "\\|";


    @Bean
    @ConditionalOnMissingBean
    public Company company() {
        Company c = new Company();
        final String employees = companyProperties.getEmployees();
        List<Employee> es = new ArrayList<>();
        if (employees != null) {
            final String[] groups = employees.split(delimiter);
            if (groups.length > 1) {
                for (String group : groups) {
                    final String[] each = group.split(vertical);
                    es.add(new Employee(each[0], new Double(each[1])));
                }
            }
        }
        c.setEmployees(es);

        final String boos = companyProperties.getBoos();
        if (!StringUtils.isEmpty(boos)) {
            final String[] group = boos.split(delimiter);
            if (group.length > 1) {
                final Boss boss = new Boss();
                boss.setName(group[0]);
                boss.setAsset(new Double(group[1]));
                c.setBoss(boss);
            }
        }
        return c;
    }
}

package io.github.mickey.controller;

import com.mickey.x2.starter.Company;
import com.mickey.x2.starter.service.MickeyService;
import com.mickey.x3.starter.service.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author mickey
 * @date 2/15/21 16:50
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @Resource
    private MickeyService mickeyService;

    @GetMapping("/x1")
    public String index() {
        mickeyService.doSomeThing();
        return "hello";
    }


    @Resource
    private Company company;

    @GetMapping("x2/company")
    public Company getCompany() {
        return company;
    }

    @Resource
    private MessageService messageService;

    @GetMapping("/x3")
    public String getMessage() {
        return messageService.produceMessage();
    }



}

package io.github.mickey.week5.assemble.auto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mickey
 * @date 2/11/21 12:39
 */
@Service
public class AService {


    @Autowired
    private BService bService;

    public void hi(){
        System.out.println("A service say hi");
        bService.hi();
    }
}

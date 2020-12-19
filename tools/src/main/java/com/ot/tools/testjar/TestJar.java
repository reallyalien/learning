package com.ot.tools.testjar;

import com.ot.mybatis.jartest.v1.Hello;
import org.apache.http.annotation.Contract;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestJar {


    @GetMapping("/hello")
    public void hello(){
        Hello hello = new Hello();
        hello.hello();
    }
}

package pers.dafacloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestContrller {

    @GetMapping("/testCookie")
    public String  function01() {
        return "helloworld";
    }

    @PostMapping("/testCookie")
    public String  function02() {
        return "helloworld";
    }
}

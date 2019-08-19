package pers.dafacloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestContrller {

    @GetMapping("/test")
    public String  function01() {

        return "helloworld";

    }

    @PostMapping("/test")
    public String  function02() {

        return "helloworld";

    }


}

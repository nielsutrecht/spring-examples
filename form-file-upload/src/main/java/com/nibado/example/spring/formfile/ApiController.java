package com.nibado.example.spring.formfile;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping
    private Message getMessage() {
        return new Message("Hello, World!");
    }

    private record Message(String message){}
}

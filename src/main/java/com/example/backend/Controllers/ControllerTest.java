package com.example.backend.Controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerTest {

    @GetMapping("/test")
    public String test() {
        return "Hello, World!";
    }
}

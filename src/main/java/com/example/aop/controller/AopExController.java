package com.example.aop.controller;


import com.example.aop.annotation.Decode;
import com.example.aop.annotation.Timer;
import com.example.aop.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class AopExController {
    @GetMapping("/get/{id}")
    public String get(@PathVariable Long id, @RequestParam String name) {
        return id + " " + name;
    }

    @PostMapping("/post")
    public User post(@RequestBody User user) {
        return user;
    }

    @Timer
    @PostMapping("/work")
    public void work() {
        try {
            Thread.sleep(3000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Decode
    @PutMapping("/put")
    public User put(@RequestBody User user) {
        return user;
    }
}

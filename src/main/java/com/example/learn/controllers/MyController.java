package com.example.learn.controllers;

import com.example.learn.customAnnotation.TimeMonitor;
import com.example.learn.dto.CheckLearningDto;
import com.example.learn.dto.User;
import com.example.learn.services.MyService;
import com.example.learn.validator.NoSqlInjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MyController {

    @Autowired
    private MyService myService;

    @TimeMonitor
    @PostMapping("/learn")
    public ResponseEntity<Object> checkLearning(@RequestBody CheckLearningDto checkLearningDto) {
        return new ResponseEntity<>(myService.checkLearning(), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> validateUser(@RequestBody User user) {
        return new ResponseEntity<>(Map.of("status", "success", "validity", "valid user"), HttpStatus.OK);
    }

}

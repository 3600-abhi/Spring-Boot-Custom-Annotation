package com.example.learn.services;

import com.example.learn.customAnnotation.TimeMonitor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MyService {

    public Object checkLearning() {
        return Map.of("status", "In Progress");
    }

}

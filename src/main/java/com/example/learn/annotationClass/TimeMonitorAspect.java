package com.example.learn.annotationClass;

import com.example.learn.dto.CheckLearningDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeMonitorAspect {
    private final Logger logger = LoggerFactory.getLogger(TimeMonitorAspect.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Around("@annotation(com.example.learn.customAnnotation.TimeMonitor)")
    public Object logTime(ProceedingJoinPoint joinPoint) {
        Object response = null;

        long startTime = System.currentTimeMillis();

        String reqPayloadStr = null;
        CheckLearningDto checkLearningDto = null;


        try {
            reqPayloadStr = objectMapper.writeValueAsString(joinPoint.getArgs()[0]);
            checkLearningDto = objectMapper.readValue(reqPayloadStr, CheckLearningDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }



        logger.info("______________________Request Body : {} ______________________", checkLearningDto);

        try {
            response = joinPoint.proceed();
        } catch (Throwable e) {
            logger.error("something went wrong");
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.info("Total time taken to complete request : {} ms", executionTime);
        }

        return response;
    }
}

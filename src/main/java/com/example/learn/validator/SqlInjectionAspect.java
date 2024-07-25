package com.example.learn.validator;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
public class SqlInjectionAspect {

    @Before("execution(* com.example.learn.controllers..*(..))")
    public void validateSqlInjection(JoinPoint joinPoint) throws Throwable {

        System.out.println("********** Inside SQL Injection *****************");

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Object[] args = joinPoint.getArgs();

        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(RequestBody.class) && args[i].getClass().isAnnotationPresent(NoSqlInjection.class)) {
                validateFields(args[i]);
            }
        }
    }

    private void validateFields(Object obj) throws IllegalAccessException {
        System.out.println("************** inside validateFields ************");

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(obj);
            if (value != null) {
                if (value instanceof String) {
                    if (value.toString().equalsIgnoreCase("select")) {

                        throw new IllegalArgumentException("SQL Injection detected in field: " + field.getName());
                    }
                } else {
                    validateFields(value);
                }
            }
        }
    }

}

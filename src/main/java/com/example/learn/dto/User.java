package com.example.learn.dto;

import com.example.learn.validator.NoSqlInjection;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@NoSqlInjection
public class User {
    private String username;
    private String password;
    private String address;
    private Education education;
}

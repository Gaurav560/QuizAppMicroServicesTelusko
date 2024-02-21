package com.telusko;

import io.unlogged.Unlogged;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuizappApplication {
@Unlogged
    public static void main(String[] args) {
        SpringApplication.run(QuizappApplication.class, args);
    }

}

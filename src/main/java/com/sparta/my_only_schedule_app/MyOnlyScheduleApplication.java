package com.sparta.my_only_schedule_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.validation.annotation.Validated;

@EnableJpaAuditing
@SpringBootApplication
public class MyOnlyScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyOnlyScheduleApplication.class, args);
    }

}

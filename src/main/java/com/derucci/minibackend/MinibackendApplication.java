package com.derucci.minibackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.derucci.minibackend.mapper")
@SpringBootApplication
public class MinibackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinibackendApplication.class, args);
    }

}

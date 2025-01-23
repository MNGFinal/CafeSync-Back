package com.ohgiraffers.cafesyncfinalproject.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.ohgiraffers.cafesyncfinalproject")
@EnableJpaRepositories(basePackages = "com.ohgiraffers.cafesyncfinalproject")
@EntityScan(basePackages = "com.ohgiraffers.cafesyncfinalproject")
public class Config {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                // private 필드에 접근하기 위한 설정
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                // DTO 필드와 Entity 필드 매칭 가능 여부 설정
                .setFieldMatchingEnabled(true);

        return modelMapper;
    }
}
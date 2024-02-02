package com.todolist.config;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Markers {
    @Bean(name = "SECRET")
    Marker getSecretMarker() {
        return MarkerFactory.getMarker("SECRET");
    }
}

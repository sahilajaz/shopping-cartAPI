package com.cart.web.cart_web.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigFile {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

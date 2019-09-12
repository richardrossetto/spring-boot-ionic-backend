package com.richard.cursomc.config;

import com.richard.cursomc.services.DBService;
import com.richard.cursomc.services.EmailService;
import com.richard.cursomc.services.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired private DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        dbService.instantiateTestDatabase();
        if(!"create".equals(strategy)){
            return false;
        }
        return true;
    }

    @Bean
    public EmailService emailService(){
        return new SmtpEmailService();
    }
}

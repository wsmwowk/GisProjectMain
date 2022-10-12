package com.GisProjectAli.GisProjectMain.controller;

import com.GisProjectAli.GisProjectMain.model.CurrentUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class SessionController {

    @Bean
    @SessionScope
    public CurrentUser sessionValidation(){
        return new CurrentUser();
    }
}

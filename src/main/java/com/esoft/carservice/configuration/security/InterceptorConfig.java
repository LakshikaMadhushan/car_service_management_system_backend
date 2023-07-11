package com.esoft.carservice.configuration.security;


import com.esoft.carservice.util.AccessTokenValidator;
import com.esoft.carservice.util.InterceptorForValidUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private final AccessTokenValidator accessTokenValidator;
    @Autowired
    public InterceptorConfig(@Lazy AccessTokenValidator accessTokenValidator) {
        this.accessTokenValidator = accessTokenValidator;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new InterceptorForValidUser(accessTokenValidator));
    }
}

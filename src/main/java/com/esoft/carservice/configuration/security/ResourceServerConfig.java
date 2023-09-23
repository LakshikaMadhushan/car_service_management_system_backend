package com.esoft.carservice.configuration.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

import static com.esoft.carservice.constant.Constant.RESOURCE_ID;


@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //Item
                .antMatchers(HttpMethod.POST, "/v1/items").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/items").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/items/{itemId}").permitAll()
                .antMatchers(HttpMethod.DELETE, "/v1/items/{itemId}").permitAll()
                .antMatchers(HttpMethod.PUT, "/v1/items").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/items/filter").permitAll()
                //Item category
                .antMatchers(HttpMethod.POST, "/v1/category").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/category").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/category/{itemCategoryId}").permitAll()
                .antMatchers(HttpMethod.DELETE, "/v1/category/{itemCategoryId}").permitAll()
                .antMatchers(HttpMethod.PUT, "/v1/category").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/category/filter").permitAll()
                //Item category
                .antMatchers(HttpMethod.POST, "/v1/technician").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/technician").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/technician/{technicianId}").permitAll()
                .antMatchers(HttpMethod.DELETE, "/v1/technician/{technicianId}").permitAll()
                .antMatchers(HttpMethod.PUT, "/v1/technician").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/technician/filter").permitAll()

                .antMatchers("/**").authenticated()
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
        http.csrf().disable();
    }

}

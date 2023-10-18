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
        String apiVersion = "/v1";
        http
                .authorizeRequests()
                .antMatchers(apiVersion + "/request/token").permitAll()
                .antMatchers(apiVersion + "/oauth/token").permitAll()
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
                //Technician category
                .antMatchers(HttpMethod.POST, "/v1/technician").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/technician").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/technician/{technicianId}").permitAll()
                .antMatchers(HttpMethod.DELETE, "/v1/technician/{technicianId}").permitAll()
                .antMatchers(HttpMethod.PUT, "/v1/technician").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/technician/filter").permitAll()
                //Admin category
                .antMatchers(HttpMethod.POST, "/v1/admin").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/admin").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/admin/{adminId}").permitAll()
                .antMatchers(HttpMethod.PUT, "/v1/admin").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/admin/filter").permitAll()
                //Manage mechanic Service
                .antMatchers(HttpMethod.POST, "/v1/mechanic-service").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/mechanic-service").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/mechanic-service/{mechanicServiceId}").permitAll()
                .antMatchers(HttpMethod.PUT, "/v1/mechanic-service").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/mechanic-service/filter").permitAll()
                .antMatchers(HttpMethod.DELETE, "/v1/mechanic-service/{mechanicServiceId}").permitAll()
                //Manage mechanic service category
                .antMatchers(HttpMethod.POST, "/v1/mechanic-service-category").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/mechanic-service-category").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/mechanic-service-category/{mechanicServiceCategoryId}").permitAll()
                .antMatchers(HttpMethod.PUT, "/v1/mechanic-service-category").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/mechanic-service-category/filter").permitAll()
                .antMatchers(HttpMethod.DELETE, "/v1/mechanic-service-category/{mechanicServiceCategoryId}").permitAll()
                //Manage vehicle service
                .antMatchers(HttpMethod.GET, "/v1/service/{serviceId}").permitAll()
                .antMatchers(HttpMethod.PUT, "/v1/service").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/service").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/service/filter").permitAll()
                //Manage vehicle
                .antMatchers(HttpMethod.GET, "/v1/vehicle/{vehicleId}").permitAll()
                .antMatchers(HttpMethod.PUT, "/v1/vehicle").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/vehicle").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/vehicle/filter").permitAll()
                //Manage report
                .antMatchers(HttpMethod.POST, "/v1/report/filter").permitAll()
                //Manage vehicle service details
                .antMatchers(HttpMethod.GET, "/v1/service-details/{serviceDetailId}").permitAll()
                .antMatchers(HttpMethod.PUT, "/v1/service-details").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/service-details").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/service-details/filter").permitAll()

//                .antMatchers("/**").authenticated()
                .antMatchers("/**").permitAll()
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
        http.csrf().disable();
    }

}

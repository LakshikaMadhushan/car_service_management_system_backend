package com.esoft.carservice.util;


import com.esoft.carservice.configuration.exception.CustomException;
import com.esoft.carservice.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.esoft.carservice.constant.ResponseCodes.INVALID_INPUT;


@Log4j2
public class InterceptorForValidUser implements HandlerInterceptor {
    @Autowired
    private final AccessTokenValidator accessTokenValidator;

    public InterceptorForValidUser(AccessTokenValidator accessTokenValidator) {
        this.accessTokenValidator = accessTokenValidator;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Execute method preHandle ");
        try {
            if (accessTokenValidator.isAuthenticated()) {
                User user = accessTokenValidator.retrieveUserInformationFromAuthentication();

//                if (user.getUserStatus() != UserStatus.ACTIVE ) {
                throw new CustomException(INVALID_INPUT, "You are not active user.");
//                }
            }
            return true;
        } catch (Exception e) {
            log.error("Method preHandle : " + e.getMessage(), e);
            throw e;
        }
    }

}

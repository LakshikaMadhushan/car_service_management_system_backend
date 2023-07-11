package com.esoft.carservice.configuration.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOAuthException extends OAuth2Exception {

    public CustomOAuthException(String msg) {
        super(msg);
    }
}

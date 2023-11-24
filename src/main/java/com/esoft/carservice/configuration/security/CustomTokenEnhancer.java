package com.esoft.carservice.configuration.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

import static com.esoft.carservice.constant.ResponseCodes.OPERATION_SUCCESS;


@Slf4j
public class CustomTokenEnhancer extends JwtAccessTokenConverter {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        log.info("Enhancing access token");

        final Map<String, Object> additionalInfo = new HashMap<>();


        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();


        log.info("Enhancing access token USER {}", user.getUserId());
        additionalInfo.put("user_id", user.getUserId());
        additionalInfo.remove("password");
        additionalInfo.put("status", OPERATION_SUCCESS);

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return super.enhance(accessToken, authentication);
    }
}

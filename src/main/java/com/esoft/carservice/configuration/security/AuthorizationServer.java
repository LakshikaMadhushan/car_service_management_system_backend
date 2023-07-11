package com.esoft.carservice.configuration.security;



import com.esoft.carservice.configuration.exception.CustomOAuthException;
import com.esoft.carservice.util.AccessTokenValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;


import static com.esoft.carservice.constant.OAuth2TokenConstant.ACCESS_TOKEN_VALIDITY_SECONDS;
import static com.esoft.carservice.constant.OAuth2TokenConstant.CLIENT_ID;
import static com.esoft.carservice.constant.OAuth2TokenConstant.CLIENT_SECRET;
import static com.esoft.carservice.constant.OAuth2TokenConstant.GRANT_TYPE_PASSWORD;
import static com.esoft.carservice.constant.OAuth2TokenConstant.REFRESH_TOKEN;
import static com.esoft.carservice.constant.OAuth2TokenConstant.REFRESH_TOKEN_VALIDITY_SECONDS;
import static com.esoft.carservice.constant.OAuth2TokenConstant.SCOPE_READ;
import static com.esoft.carservice.constant.OAuth2TokenConstant.SCOPE_WRITE;
import static com.esoft.carservice.constant.OAuth2TokenConstant.TOKEN_SIGN_IN_KEY;

@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Value("${oauth-path}")
    String oauthPath;

    @Autowired
    AccessTokenValidator tokenValidator;

    @Autowired
    CustomOAuthFilter filter;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security
                .addTokenEndpointAuthenticationFilter(filter);

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    .withClient(CLIENT_ID)
//                .secret(passwordEncoder.encode(""))
                .secret(passwordEncoder.encode(CLIENT_SECRET))
//                    .secret(CLIENT_SECRET)
                    .scopes(SCOPE_READ,SCOPE_WRITE)
                    .authorizedGrantTypes(GRANT_TYPE_PASSWORD,REFRESH_TOKEN)
                    .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
                    .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));

        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(getTokenStore())
                .tokenEnhancer(enhancerChain)
                .accessTokenConverter(accessTokenConverter())
                .pathMapping("/oauth/token",oauthPath)
                .exceptionTranslator(e -> {
                    log.info("Auth failed: {}", e.getMessage());
                     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CustomOAuthException(e.getMessage()));
                });


    }

    @Bean
    public JwtTokenStore getTokenStore(){

        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey(TOKEN_SIGN_IN_KEY);
        return tokenConverter;

    }

    @Bean
    public TokenEnhancer tokenEnhancer(){
        return new CustomTokenEnhancer();
    }

}

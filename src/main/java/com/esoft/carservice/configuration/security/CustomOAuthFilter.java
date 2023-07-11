package com.esoft.carservice.configuration.security;



import com.esoft.carservice.dto.common.CommonResponse;
import com.esoft.carservice.util.AccessTokenValidator;
import com.esoft.carservice.util.EmailVerificationTokenGenerator;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import static com.esoft.carservice.constant.ResponseCodes.OPERATION_FAILED;

@Component
@Slf4j
public class CustomOAuthFilter extends OncePerRequestFilter {

    @Autowired
    AccessTokenValidator tokenValidator;

    @Autowired
    EmailVerificationTokenGenerator emailVerificationTokenGenerator;

    @Value("${oauth-path}")
    String oauthPath;




    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        try {

            String reqUri = req.getRequestURI();
//            if(!reqUri.equalsIgnoreCase("/api"+oauthPath)){chain.doFilter(req,res); return;}


            String token = req.getHeader("ms_token");
            String emailToken = req.getHeader("email_token");

            log.info("email token found, {}", emailToken);

            if (token == null && emailToken == null) {
                chain.doFilter(req, res);
                return;
            }

            String grant_type = "password";
            String username = null;
            String password = null;
            String scope = "read";

            if (token != null) {
                if (!tokenValidator.validate(token) || !tokenValidator.checkIntended(token))
                    throw new Exception("token is invalid or not intended for this api");
                log.info("token is valid");

                Jwt<Header, Claims> untrusted = tokenValidator.getClaims(token);
                username = untrusted.getBody().get("email", String.class);
                password = untrusted.getBody().get("email", String.class);
                log.info("username: " + username);
                log.info("password: " + password);

                if (username == null || password == null) {
                    log.info("could not find email field in token. using unique name for user name");
                    username = untrusted.getBody().get("unique_name", String.class);
                    password = username;
                    log.info("unqiue name is: {}", username);
                }

            } else {
                log.info("email_token is present , {}", emailToken);
                Jws<Claims> claims = emailVerificationTokenGenerator.verify(emailToken);

                if (claims == null) {
                    log.info("Email token not valid");
                    throw new Exception("Email token verification failed.");
                }
                log.info("taking guest user as the authenticated user");
                username = "guest@mail.com";
                password = "guest@mail.com";
            }
            //check email token and use for email
            if(emailToken==null && req.getParameter("username")!=null && req.getParameter("password")!=null){
                System.out.println("authType==AuthType.EMAIL");
                username=req.getParameter("username");
                password=req.getParameter("password");
                System.out.println(username+" "+password);
            }


            Map<String, String[]> additionalParams = new HashMap<String, String[]>();
            additionalParams.put("username", new String[]{username});
            additionalParams.put("password", new String[]{password});
            additionalParams.put("grant_type", new String[]{grant_type});
            additionalParams.put("scope", new String[]{scope});
            CustomWrappedRequest wrappedRequest = new CustomWrappedRequest(req, additionalParams);


            chain.doFilter(wrappedRequest, res);

        } catch (Exception e) {
            log.error("FAILED: " + e.toString());
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            res.getWriter().print(new Gson().toJson(new CommonResponse(OPERATION_FAILED, e.getMessage())));
            res.getWriter().flush();
            return;
        }


    }
}

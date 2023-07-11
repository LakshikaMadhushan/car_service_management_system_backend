package com.esoft.carservice.util;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Header;
import com.esoft.carservice.configuration.exception.CustomException;
import com.esoft.carservice.configuration.exception.ServiceException;
import com.esoft.carservice.entity.User;
import com.esoft.carservice.repository.UserRepository;
import com.esoft.carservice.service.UserService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

import static com.esoft.carservice.constant.ResponseCodes.*;

@Slf4j
@Component
public class AccessTokenValidator {

    @Value("${app-id}")
    String appId;

    @Value("${tenant-id}")
    String tenantId;

    private final UserRepository userRepository;
    private final UserService userService;
    private final EmailVerificationTokenGenerator emailVerificationTokenGenerator;

    public AccessTokenValidator(UserRepository userRepository, UserService userService, EmailVerificationTokenGenerator emailVerificationTokenGenerator) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.emailVerificationTokenGenerator = emailVerificationTokenGenerator;
    }


    public boolean validate(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);


            JwkProvider provider = null;
            Jwk jwk =null;
            Algorithm algorithm=null;


            provider = new UrlJwkProvider(new URL("https://login.microsoftonline.com/"+tenantId+"/discovery/v2.0/keys"));
            jwk = provider.get(jwt.getKeyId());
            algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
            algorithm.verify(jwt);// if the token signature is invalid, the method will throw SignatureVerificationException
        } catch(Exception e){

            log.info("failed: "+e.getMessage());
            return false;

        }

        return true;
    }

    public boolean checkIntended(String token){
        try{
            int i = token.lastIndexOf('.');
            String withoutSignature = token.substring(0, i+1);
            Jwt<io.jsonwebtoken.Header, Claims> untrusted = Jwts.parser().parseClaimsJwt(withoutSignature);

            String aud = untrusted.getBody().get("aud",String.class);
            Date expDate = untrusted.getBody().get("exp",Date.class);

            if(!aud.equalsIgnoreCase(appId))return false;
            if(expDate.before(new Date()))return false;

        }catch(Exception e){
            log.info(e.getMessage());
            return false;
        }
        return true;
    }

    public Jwt<io.jsonwebtoken.Header,Claims> getClaims(String token){
        try{

            int i = token.lastIndexOf('.');
            String withoutSignature = token.substring(0, i+1);
            Jwt<io.jsonwebtoken.Header, Claims> untrusted = Jwts.parser().parseClaimsJwt(withoutSignature);
            return untrusted;
        }catch(Exception e){
            log.info(e.getMessage());
        }
        return null;
    }

//    public long validInquiryIdFromAuthentication(String token) {
//        log.info("Execute method validInquiryIdFromAuthentication");
//        try {
//           Jws<Claims> claimsJws = emailVerificationTokenGenerator.verify(token);
//
//        if (claimsJws == null) {
//            log.error("Token not valid");
//            throw new CustomException(OPERATION_FAILED, "Token not valid");
//        }
//
//        String inquiryId = claimsJws.getBody().getSubject();
//
//        Optional<Inquiry> optionalInquiry = inquiryRepository.findById(Long.parseLong(inquiryId));
//
//        if (!optionalInquiry.isPresent()) {
//            log.error("Inquiry not found!");
//            throw new CustomException(RESOURCE_NOT_FOUND, "Inquiry not found!");
//        }
//
//        Inquiry inquiry = optionalInquiry.get();
//
////        InquiryGeneralInformationResponseDTO inquiryGeneralInformationResponseDTO = modelMapper.map(inquiry, InquiryGeneralInformationResponseDTO.class);
////
////        inquiryGeneralInformationResponseDTO.setCounsellorId(inquiry.getCounselor().getUserId());
////        inquiryGeneralInformationResponseDTO.setAppliedCourseId(inquiry.getCourse().getCourseId());
////        inquiryGeneralInformationResponseDTO.setIntakeId(inquiry.getIntake().getIntakeId());
//
//        return inquiry.getInquiryId();
//
//        } catch (Exception e) {
//            log.error("Method validInquiryIdFromAuthentication : " + e.getMessage());
//            throw new ServiceException(OPERATION_FAILED, e.getMessage());
//        }
//    }

    public User retrieveUserInformationFromAuthentication() {
        log.info("Execute method retrieveUserInformationFromAuthentication");
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                User user = userService.getUserByEmail(authentication.getName());

                if (user == null) {
                    throw new CustomException(USER_NOT_FOUND, "this user is not registered yet");
                }
                return user;
            }
            throw new CustomException(RESOURCE_NOT_FOUND, "Can't find user details from token");
        } catch (Exception e) {
            log.error("Method retrieveB2BUserInformationFromAuthentication : " + e.getMessage());
            throw new CustomException(OPERATION_FAILED, e.getMessage());
        }
    }

    public boolean isAuthenticated() {
        log.info("Execute method isAuthenticated");
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            log.info("isAuthenticated " + authentication.isAuthenticated());
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                log.info("isAuthenticated : instance of AnonymousAuthenticationToken");
                log.info("isAuthenticated : email : " + authentication.getName());
                 User user = userService.getUserByEmail(authentication.getName());
                log.info("isAuthenticated : user : " + user);
                return user != null;
            }
            return false;
        } catch (Exception e) {
            log.error("Method isAuthenticated : " + e.getMessage());
            throw new CustomException(OPERATION_FAILED, e.getMessage());
        }
    }

}

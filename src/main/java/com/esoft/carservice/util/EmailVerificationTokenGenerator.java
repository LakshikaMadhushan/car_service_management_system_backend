package com.esoft.carservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class EmailVerificationTokenGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(com.esoft.carservice.util.EmailVerificationTokenGenerator.class);

    private final CustomGenerator customGenerator;

    private static final String SECRET = "AIsanifM4hK0sMK2ahi8KRp1zhiAX18b654051rLK51it0m4y19932612a92vA3Ws8Q14381Kth5asMn0pm5";
    public static final String EMAIL_CLAIM = "email";

    public EmailVerificationTokenGenerator(CustomGenerator customGenerator) {
        this.customGenerator = customGenerator;
    }


    private Jws<Claims> getClaimsJws(String auth, SecretKey secretKey) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(auth);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public String generate(long userId, String email) {

        Date issued = new Date();
        SecretKey secretKey = Keys.hmacShaKeyFor((SECRET).getBytes());

        return Jwts.builder()
                .setIssuer("ceyentra")
                .setSubject(String.valueOf(userId))
                .setIssuedAt(issued)
                .setExpiration(customGenerator.changeDateFromMinutes(new Date(), 60 * 24 * 3))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .claim(EMAIL_CLAIM, email)
                .compact();
    }

    public String generateAccessTokenForDropRequest(long studentDropReqId, String email) {

        Date issued = new Date();
        SecretKey secretKey = Keys.hmacShaKeyFor((SECRET).getBytes());

        return Jwts.builder()
                .setIssuer("ceyentra")
                .setSubject(String.valueOf(studentDropReqId))
                .setIssuedAt(issued)
                .setExpiration(customGenerator.changeDateFromMinutes(new Date(), 1440*30))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .claim(EMAIL_CLAIM, email)
                .compact();
    }

    public Jws<Claims> verify(String auth) {
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor((SECRET).getBytes());
            return getClaimsJws(auth, secretKey);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

}

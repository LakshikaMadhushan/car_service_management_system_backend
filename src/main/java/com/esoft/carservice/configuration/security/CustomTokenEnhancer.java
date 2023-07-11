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

        CustomSecurityUser user = (CustomSecurityUser) authentication.getPrincipal();
        additionalInfo.put("name", user.getName());
//        additionalInfo.put("department", user.getDepartment());
//        additionalInfo.put("userRoles", Arrays.stream(user.getUserRoles().split(",")).map(UserRole::valueOf));
//        additionalInfo.put("lastName", user.getLastName());
//        additionalInfo.put("role",user.getRole());
//        additionalInfo.put("gender",user.getGender());
//        additionalInfo.put("mobileNumber",user.getMobileNumber());
//        additionalInfo.put("nic",user.getNic());
//        additionalInfo.put("enabled",user.isUserEnabled());
//        additionalInfo.put("studentId",user.getStudentId());
//        additionalInfo.put("profileImageUrl",user.getProfileImageUrl());
//        additionalInfo.put("email",user.getEmail());
//        additionalInfo.put("dateOfBirth",user.getDateOfBirth());
//        additionalInfo.put("empNo",user.getEmpNo());
//        additionalInfo.put("userId",user.getUserId());
//        additionalInfo.put("firstName",user.getFirstName());
//        additionalInfo.put("cbNumber",user.getCbNumber());
//        additionalInfo.put("createdAt",user.getCreatedAt());
        additionalInfo.remove("password");
        additionalInfo.put("status",OPERATION_SUCCESS);

        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(additionalInfo);

        return super.enhance(accessToken, authentication);
    }
}

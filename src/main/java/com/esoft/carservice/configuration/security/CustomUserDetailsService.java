package com.esoft.carservice.configuration.security;
import com.esoft.carservice.entity.User;
import com.esoft.carservice.repository.UserRepository;
import com.esoft.carservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;



@Service
@Slf4j
@Transactional(propagation = Propagation.SUPPORTS)
public class CustomUserDetailsService implements UserDetailsService {

    UserRepository userRepository;
    private final UserService userService;
    ModelMapper modelMapper;
//    @Value("${authentication.type}")
//    private AuthType authType;

    public CustomUserDetailsService(UserRepository userRepository, UserService userService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("load user name "+username);
        return getUserFromEmail(username);
    }


    public CustomSecurityUser getUserFromEmail(String email) throws UsernameNotFoundException {
        log.info("fetching user");
        Optional<User> userOptional = userRepository.findLatestByEmail(email);
        User user = null;
        if (!userOptional.isPresent()) {
            log.info("User not found");
            throw new UsernameNotFoundException("User not found");
        }

        log.info("getting user");
        user = userOptional.get();
//        log.info("user is: {}", user.getFirstName());
//        if (user.getUserStatus() != UserStatus.ACTIVE || !user.isActiveUser())
//            throw new CustomException(OPERATION_FAILED, "User is not active");

//        if (userService.getUserRoles(user.getUserRoles()).contains(UserRole.STUDENT)) {
//            log.info("User is a student");
//            Student student = user.getStudent();
//            if (student == null) log.info("User role is STUDENT but student not found.");
//            if (student != null) {
//                if (!student.isSms()) throw new CustomException(OPERATION_FAILED, "Student sms is not enabled");
//            }
//        }

//        log.info("user received: ");
//        Student student = user.getStudent();
        CustomSecurityUser customSecurityUser=null;

            System.out.println("user received:");
//            String password=user.getPassword();
//            log.info("password"+password);
//            customSecurityUser = new CustomSecurityUser(email, password,
//                    getAuthorities(userService.getUserRoleString(user.getUserId()).toArray(new String[0])));


//        customSecurityUser.setRole(user.getRole());
        customSecurityUser.setName(user.getName());
//        customSecurityUser.setUserId(user.getUserId());
//        customSecurityUser.setEmail(email);
//        customSecurityUser.setFirstName(user.getFirstName());
//        customSecurityUser.setGender(user.getGender());
//        customSecurityUser.setDateOfBirth(user.getDateOfBirth());
//        customSecurityUser.setProfileImageUrl(user.getProfileImageUrl());
//        customSecurityUser.setMobileNumber(user.getMobileNumber());
//        customSecurityUser.setNic(user.getNic());
//        customSecurityUser.setEmpNo(user.getEmpNo());
//        customSecurityUser.setCreatedAt(user.getCreatedAt());
//        customSecurityUser.setCreatedAt(user.getCreatedAt());
//        if (user.getDepartment() != null) {
//            customSecurityUser.setDepartment(modelMapper.map(user.getDepartment(), DepartmentBasicDTO.class));
//        }
//        customSecurityUser.setUserEnabled(true);
//        customSecurityUser.setEmail(email);
//        if (student != null) {
//            customSecurityUser.setStudentId(student.getStudentId());
//            customSecurityUser.setCbNumber(student.getCbNumber());
//        }
        return customSecurityUser;
    }

    /**
     * @param role the user role of a searched user
     * @return the user role as authority
     */
    private List<GrantedAuthority> getAuthority(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }

    private List<GrantedAuthority> getAuthorities(String[] roles) {

        List<GrantedAuthority> authorities = Arrays.stream(roles).map(role -> {

            return new SimpleGrantedAuthority("ROLE_" + role);
        }).collect(Collectors.toList());
        return authorities;
    }

}

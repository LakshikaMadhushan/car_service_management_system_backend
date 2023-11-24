package com.esoft.carservice.service.Impl;


import com.esoft.carservice.configuration.security.CustomUserDetails;
import com.esoft.carservice.entity.User;
import com.esoft.carservice.repository.UserRepository;
import com.esoft.carservice.service.UserAuthService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service(value = "userAuthService")
public class UserAuthServiceImpl implements UserDetailsService, UserAuthService {

    @Autowired
    private UserRepository userRepo;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        try {
//            Optional<User> user = userRepo.findLatestByEmail(username);
//
//            if (!user.isPresent()) throw new UsernameNotFoundException("Invalid username or password.");
//            log.info(user.get().getEmail());
//            return new org.springframework.security.core.userdetails.User(user.get().getName(), user.get().getPassword(), getAuthority(user.get()));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<User> user = userRepo.findLatestByEmail(username);
            if (!user.isPresent()) throw new UsernameNotFoundException("Invalid username or password.");
            ArrayList<String> roles = new ArrayList<>();
            roles.add(user.get().getUserRole().toString());
            CustomUserDetails customUserDetails = new CustomUserDetails(user.get().getEmail(), user.get().getPassword(), getAuthorities((roles).toArray(new String[0])));

            log.info(user.get().getEmail());
            customUserDetails.setUserId(user.get().getUserId());
            customUserDetails.setPassword(user.get().getPassword());
            customUserDetails.setUsername(user.get().getEmail());
            return customUserDetails;
//            return new org.springframework.security.core.userdetails.User(user.get().getName(), user.get().getPassword(), getAuthority(user.get()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private List<GrantedAuthority> getAuthorities(String[] roles) {

        List<GrantedAuthority> authorities = Arrays.stream(roles).map(role -> {

            return new SimpleGrantedAuthority(role);
        }).collect(Collectors.toList());
        return authorities;
    }

    private List getAuthority(User user) {
        return Arrays.asList(new SimpleGrantedAuthority(user.getUserRole().name()));
    }
}

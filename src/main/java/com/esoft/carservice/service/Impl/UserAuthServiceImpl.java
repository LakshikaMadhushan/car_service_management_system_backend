package com.esoft.carservice.service.Impl;


import com.esoft.carservice.entity.User;
import com.esoft.carservice.repository.UserRepository;
import com.esoft.carservice.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service(value = "userAuthService")
public class UserAuthServiceImpl implements UserDetailsService, UserAuthService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<User> user = userRepo.findLatestByEmail(username);
            if (!user.isPresent()) throw new UsernameNotFoundException("Invalid username or password.");
            return new org.springframework.security.core.userdetails.User(user.get().getName(), user.get().getPassword(), getAuthority(user.get()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    private List getAuthority(User user) {
        return Arrays.asList(new SimpleGrantedAuthority(user.getUserRole().name()));
    }
}

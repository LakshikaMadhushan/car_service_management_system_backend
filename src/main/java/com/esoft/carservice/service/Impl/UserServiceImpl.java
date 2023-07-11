package com.esoft.carservice.service.Impl;

import com.esoft.carservice.entity.User;
import com.esoft.carservice.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {
    @Override
    public User getUserByEmail(String email) {
        return null;
    }
}

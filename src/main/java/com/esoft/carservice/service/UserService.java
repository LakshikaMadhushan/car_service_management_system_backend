package com.esoft.carservice.service;

import com.esoft.carservice.entity.User;

public interface UserService {
    User getUserByEmail(String email);
}

package com.esoft.carservice.repository;

import com.esoft.carservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value = "SELECT * FROM user u WHERE u.email=?1",nativeQuery = true)
    Optional<User> findLatestByEmail(String email);
}

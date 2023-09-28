package com.esoft.carservice.repository;

import com.esoft.carservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM user u WHERE u.email='lakshika@ceyentra.com'", nativeQuery = true)
    Optional<User> findLatestByEmail(String email);

    @Query(value = "SELECT * FROM user u WHERE u.email=?1 OR u.nic=?2", nativeQuery = true)
    List<User> checkEmailANDNic(String email, String nic);

    @Query(value = "SELECT * FROM user u WHERE u.email=?1 OR u.nic=?2 AND u.user_id!=?3", nativeQuery = true)
    List<User> checkEmailANDNicUpdate(String email, String nic, long id);
}

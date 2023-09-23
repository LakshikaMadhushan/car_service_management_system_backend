package com.esoft.carservice.repository;

import com.esoft.carservice.entity.Admin;
import com.esoft.carservice.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Query(value = "SELECT * FROM admin a WHERE (?1 IS NULL OR a.mobile_number =?1) AND\n" +
            "(?2 = 0 OR a.admin_id=?2) AND\n" +
            "(?3 IS NULL OR a.email LIKE %?3%) AND\n" +
            "(?4 IS NULL OR (SELECT user_id FROM user u where u.user_id=?4 AND u.admin_admin_id=a.admin_id )) AND\n" +
            "(?5 IS NULL OR a.nic LIKE %?5%) AND\n" +
            "(?6 IS NULL OR a.status =?6)\n" +
            "ORDER BY a.admin_id DESC", nativeQuery = true)
    List<Admin> getAllAdminFilter(String contact, long adminId, String email, long userId, String nic, UserStatus status);
}

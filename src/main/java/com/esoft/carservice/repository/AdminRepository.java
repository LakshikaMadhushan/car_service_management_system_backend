package com.esoft.carservice.repository;

import com.esoft.carservice.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Query(value = "SELECT * FROM admin a WHERE (?1 IS NULL OR a.mobile_number =?1) AND\n" +
            "(?2 = 0 OR a.admin_id=?2) AND\n" +
            "(?3 IS NULL OR (SELECT u.user_id FROM user u WHERE u.email LIKE %?3% AND a.user_user_id=u.user_id )) AND\n" +
            "(?4 = 0 OR (SELECT u.user_id FROM user u where u.user_id=?4 AND a.user_user_id=u.user_id  )) AND\n" +
            "(?5 IS NULL OR a.nic LIKE %?5%) AND\n" +
            "(?6 IS NULL OR (SELECT u.user_id FROM user u where u.status =?6 AND u.admin_admin_id=a.admin_id))\n" +
            "ORDER BY a.admin_id DESC", nativeQuery = true)
    List<Admin> getAllAdminFilter(String contact, long adminId, String email, long userId, String nic, String status);
}

package com.esoft.carservice.repository;

import com.esoft.carservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT * FROM customer c WHERE (?1 IS NULL OR c.mobile_number =?1) AND\n" +
            "            (?2 = 0 OR c.customer_id=?2) AND\n" +
            "            (?3 IS NULL OR (SELECT u.user_id FROM user u WHERE u.email LIKE %?3% AND u.customer_customer_id=c.customer_id )) AND\n" +
            "            (?4 = 0 OR (SELECT u.user_id FROM user u where u.user_id=?4 AND u.customer_customer_id=c.customer_id )) AND\n" +
            "            (?5 IS NULL OR c.nic LIKE %?5%) AND\n" +
            "            (?6 IS NULL OR (SELECT u.user_id FROM user u where u.status =?6 AND u.customer_customer_id=c.customer_id))\n" +
            "            ORDER BY c.customer_id DESC", nativeQuery = true)
    List<Customer> getAllCustomerFilter(String contact, long adminId, String email, long userId, String nic, String status);
}

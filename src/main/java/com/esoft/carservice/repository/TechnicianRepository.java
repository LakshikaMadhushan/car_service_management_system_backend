package com.esoft.carservice.repository;

import com.esoft.carservice.entity.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TechnicianRepository extends JpaRepository<Technician, Long> {
    @Query(value = "SELECT * FROM technician t WHERE (?1 IS NULL OR t.name LIKE %?1%) AND (?2 = 0 OR t.technician_id=?2)\n" +
            "AND (?3 IS NULL OR t.email LIKE %?3%) AND (?4 IS NULL OR t.nic LIKE %?4%) AND (?5 IS NULL OR t.status =?5) ORDER BY t.technician_id DESC", nativeQuery = true)
    List<Technician> getAllTechnicianFilter(String name, long id, String email, String nic, String status);
}

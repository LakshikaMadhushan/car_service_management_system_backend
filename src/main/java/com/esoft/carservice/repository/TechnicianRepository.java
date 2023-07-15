package com.esoft.carservice.repository;

import com.esoft.carservice.entity.Technician;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnicianRepository extends JpaRepository<Technician, Long> {
}

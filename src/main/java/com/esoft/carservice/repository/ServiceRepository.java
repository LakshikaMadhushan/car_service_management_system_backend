package com.esoft.carservice.repository;

import com.esoft.carservice.entity.Service;
import com.esoft.carservice.enums.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    @Query(value = "SELECT * FROM service s WHERE (?1 = 0 OR s.service_id=?1) AND\n" +
            "(?2 = 0 OR s.vehicle_vehicle_id=?2) AND\n" +
            "(?3 IS NULL OR s.type=?3) AND\n" +
            "(?4 IS NULL OR s.service_date BETWEEN ?4 AND ?5) ORDER BY s.service_id DESC ", nativeQuery = true)
    List<Service> getAllServiceFilter(long serviceId, long vehicleId, ServiceType type, Date startDate, Date endDate);
}

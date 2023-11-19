package com.esoft.carservice.repository;

import com.esoft.carservice.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    @Query(value = "SELECT * FROM service s WHERE (?1 = 0 OR s.service_id=?1) AND\n" +
            "(?2 = 0 OR s.vehicle_vehicle_id=?2) AND\n" +
            "(?3 = 0 OR s.technician_technician_id=?3) AND\n" +
            "(?4 IS NULL OR s.type=?4) AND\n" +
            "(?5 IS NULL OR s.service_date BETWEEN ?5 AND ?6) ORDER BY s.service_id DESC ", nativeQuery = true)
    List<Service> getAllServiceFilter(long serviceId, long vehicleId, long technicianId, String type, Date startDate, Date endDate);

    @Query(value = "SELECT * FROM service s WHERE (?1 = 0 OR s.technician_technician_id=?1) AND\n" +
            "        (?2 = 0 OR s.vehicle_vehicle_id=?2) AND\n" +
            "        (?3 IS NULL OR s.type=?3) AND\n" +
            "        (?4 IS NULL OR s.service_date BETWEEN ?4 AND ?5) AND\n" +
            "        (?6 = 0 OR s.vehicle_vehicle_id IN (SELECT v.vehicle_id FROM vehicle v where v.customer_customer_id=?6))\n" +
            "        ORDER BY s.service_id DESC ", nativeQuery = true)
    List<Service> getAdminReportFilter(long technicianId, long vehicleId, String type, Date startDate, Date endDate, long customerId);

    @Query(value = "SELECT * FROM service s WHERE s.vehicle_vehicle_id IN (SELECT v.vehicle_id FROM vehicle v where v.customer_customer_id=?1)\n" +
            "        ORDER BY s.service_id DESC ", nativeQuery = true)
    List<Service> serviceCount(long customerId);

    @Query(value = "SELECT * FROM service s WHERE s.vehicle_vehicle_id IN (SELECT v.vehicle_id FROM vehicle v where v.customer_customer_id=?1)\n" +
            "        ORDER BY s.service_date DESC LIMIT 1 ", nativeQuery = true)
    Optional<Service> serviceDate(long customerId);
}

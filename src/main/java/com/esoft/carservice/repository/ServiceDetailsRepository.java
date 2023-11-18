package com.esoft.carservice.repository;

import com.esoft.carservice.entity.ServiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceDetailsRepository extends JpaRepository<ServiceDetails, Long> {
    @Query(value = "SELECT * FROM service_details ds WHERE (?1 = 0 OR ds.service_details_id=?1) AND\n" +
            "            (?2 = 0 OR ds.service_service_id=?2) AND\n" +
            "            (?3 = 0 OR ds.item_item_id=?3) AND\n" +
            "            (?4 = 0 OR ds.mechanic_service_mechanic_service_id=?4) AND\n" +
            "            (?5 IS NULL OR ds.type=?5)  ORDER BY ds.service_details_id DESC", nativeQuery = true)
    List<ServiceDetails> getAllServiceDetailFilter(long serviceDetailsId, long serviceId, long itemId, long mechanicServiceId, String type);

//    @Query(value = "SELECT * FROM service s WHERE (?1 = 0 OR s.technician_technician_id=?1) AND\n" +
//            "        (?2 = 0 OR s.vehicle_vehicle_id=?2) AND\n" +
//            "        (?3 IS NULL OR s.type=?3) AND\n" +
//            "        (?4 IS NULL OR s.service_date BETWEEN ?4 AND ?5) AND\n" +
//            "        (?5 IS NULL OR s.vehicle_vehicle_id IN (SELECT v.vehicle_id FROM vehicle v where v.customer_customer_id=?5))\n" +
//            "        ORDER BY s.service_id DESC ", nativeQuery = true)
//    List<Service> getAdminReportFilter(long technicianId, long vehicleId, ServiceType type, Date startDate, Date endDate, long customerId);
}

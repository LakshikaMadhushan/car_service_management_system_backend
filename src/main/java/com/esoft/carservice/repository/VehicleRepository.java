package com.esoft.carservice.repository;

import com.esoft.carservice.entity.Vehicle;
import com.esoft.carservice.enums.VehicleStatus;
import com.esoft.carservice.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @Query(value = "SELECT * FROM vehicle v WHERE v.number_plate=?1", nativeQuery = true)
    List<Vehicle> findByVehicleNumber(String name);

    @Query(value = "SELECT * FROM vehicle v WHERE v.number_plate=?1 AND v.vehicle_id=?2", nativeQuery = true)
    List<Vehicle> findByVehicleNumberUpdate(String name, long id);

    @Query(value = "SELECT * FROM vehicle v WHERE (?1 IS NULL OR v.number_plate LIKE %?1%)\n" +
            "                          AND (?2 = 0 OR v.vehicle_id=?2)\n" +
            "                          AND (?3 IS NULL OR v.vehicle_type=?3)\n" +
            "                          AND (?4 IS NULL OR v.category=?4)\n" +
            "                          AND (?4 IS NULL OR v.status=?5)\n" +
            "                          AND (?4 = 0 OR v.customer_customer_id=?6) ORDER BY v.vehicle_id DESC", nativeQuery = true)
    List<Vehicle> getAllVehicleFilter(String name, long vehicleId, VehicleType vehicletype, String category, VehicleStatus status, long customerId);
}

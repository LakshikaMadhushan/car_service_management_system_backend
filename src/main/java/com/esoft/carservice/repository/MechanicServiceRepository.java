package com.esoft.carservice.repository;

import com.esoft.carservice.entity.MechanicService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MechanicServiceRepository extends JpaRepository<MechanicService, Long> {
    @Query(value = "SELECT * FROM mechanic_service m WHERE m.name=?1 AND m.mechanic_service_id!=?2", nativeQuery = true)
    List<MechanicService> findMechanicServiceNameUpdate(String name, long id);

    @Query(value = "SELECT * FROM mechanic_service m WHERE m.name=?1", nativeQuery = true)
    List<MechanicService> findMechanicServiceByName(String name);

    @Query(value = "SELECT * FROM mechanic_service m WHERE (?1 IS NULL OR m.name LIKE %?1%) AND (?2 = 0 OR m.mechanic_service_id=?2) AND (?3 IS NULL OR m.vehicle_type=?3) AND (?4 = 0 OR m.mechanic_service_category_mechanic_service_category_id=?4) ORDER BY m.mechanic_service_id DESC", nativeQuery = true)
    List<MechanicService> getAllMechanicServiceFilter(String name, long id, String vehicleType, long categoryId);
}

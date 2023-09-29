package com.esoft.carservice.repository;

import com.esoft.carservice.entity.MechanicServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MechanicServiceCategoryRepository extends JpaRepository<MechanicServiceCategory, Long> {
    @Query(value = "SELECT * FROM mechanic_service_category m WHERE m.name=?1", nativeQuery = true)
    List<MechanicServiceCategory> findMechanicServiceCategoryName(String name);

    @Query(value = "SELECT * FROM mechanic_service_category m WHERE m.name=?1 AND m.mechanic_service_category_id!=?2", nativeQuery = true)
    List<MechanicServiceCategory> findMechanicServiceCategoryNameUpdate(String name, long id);

    @Query(value = "SELECT * FROM mechanic_service_category m WHERE (?1 IS NULL OR m.name LIKE %?1%) AND (?2 = 0 OR m.mechanic_service_category_id=?2) ORDER BY m.mechanic_service_category_id DESC", nativeQuery = true)
    List<MechanicServiceCategory> getAllMechanicServiceCategoryFilter(String name, long id);
}

package com.esoft.carservice.repository;

import com.esoft.carservice.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {
    @Query(value = "SELECT * FROM item_category i WHERE i.name=?1 AND i.item_category_id!=?2", nativeQuery = true)
    List<ItemCategory> findItemByItemCategoryNameUpdate(String name, long id);

    @Query(value = "SELECT * FROM item_category i WHERE i.name=?1", nativeQuery = true)
    List<ItemCategory> findItemCategoryByItemName(String name);

    @Query(value = "SELECT * FROM item_category i WHERE (?1 IS NULL OR i.name LIKE %?1%) AND (?2 = 0 OR i.item_category_id=?2) ORDER BY i.item_category_id DESC", nativeQuery = true)
    List<ItemCategory> getAllItemCategoryFilter(String name, long id);
}

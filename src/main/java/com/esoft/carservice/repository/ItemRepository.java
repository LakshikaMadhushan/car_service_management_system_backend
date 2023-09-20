package com.esoft.carservice.repository;

import com.esoft.carservice.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query(value = "SELECT * FROM item i WHERE i.item_name=?1", nativeQuery = true)
    List<Item> findItemByItemName(String name);
}

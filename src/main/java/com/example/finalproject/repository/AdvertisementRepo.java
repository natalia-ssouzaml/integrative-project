package com.example.finalproject.repository;

import com.example.finalproject.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdvertisementRepo extends JpaRepository<Advertisement, Long> {
    @Query(value = "SELECT DISTINCT a.advertisement_code ,a.name ,a.price ,a.seller_code \n" +
            "FROM advertisement a \n" +
            "INNER JOIN batch b on a.advertisement_code = b.advertisement_code\n" +
            "INNER JOIN inbound_order i on b.order_code = i.order_code\n" +
            "INNER JOIN section s on i.section_code = s.section_code\n" +
            "WHERE s.category = ?1 ", nativeQuery = true)
    List<Advertisement> findAllByCategory(String category);
}

package com.example.finalproject.repository;

import com.example.finalproject.model.Advertisement;
import com.example.finalproject.model.Enum.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AdvertisementRepo extends JpaRepository<Advertisement, Long> {
    @Query(value = "SELECT DISTINCT a.advertisement_id ,a.name ,a.price ,a.seller_id \n" +
            "FROM advertisement a \n" +
            "INNER JOIN batch b on a.advertisement_id = b.advertisement_id\n" +
            "INNER JOIN inbound_order i on b.order_number = i.order_number\n" +
            "INNER JOIN section s on i.section_code = s.section_code\n" +
            "WHERE s.category = ?1 ",nativeQuery = true)

    List<Advertisement> findAllByCategory(String category);
}

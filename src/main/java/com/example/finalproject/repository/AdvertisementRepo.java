package com.example.finalproject.repository;

import com.example.finalproject.model.Advertisement;
import com.example.finalproject.model.Enum.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AdvertisementRepo extends JpaRepository<Advertisement, Long> {


    @Query(value = "select *\n" +
            "from advertisement a\n" +
            "    inner join batch b on a.advertisement_id = b.advertisement_id\n" +
            "    inner join inbound_order i on b.order_number = i.order_number\n" +
            "    inner join section s on i.section_code = s.section_code\n" +
            "where s.category = :category ",nativeQuery = true)
List<Advertisement> findAllByCategory(@Param("category") Category category);
//    List<Advertisement> findAllByCategory(String category);


//    @Query(value = "select a from Advertisement a \n" +
//            "    inner join Batch b \n" +
//            "    inner join InboundOrder io \n" +
//            "    inner join Section s \n" +
//            "    where s.category = :category")
//    List<Advertisement> findAllByCategory(@Param("category") Category category);
}

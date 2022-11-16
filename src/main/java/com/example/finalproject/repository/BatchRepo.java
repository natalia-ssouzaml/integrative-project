package com.example.finalproject.repository;

import com.example.finalproject.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatchRepo extends JpaRepository<Batch, Long> {
    List<Batch> findByAdvertisementAdvertisementId(Long advertisementId);
}

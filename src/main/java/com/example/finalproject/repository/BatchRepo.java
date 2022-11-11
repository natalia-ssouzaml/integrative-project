package com.example.finalproject.repository;

import com.example.finalproject.model.Advertisement;
import com.example.finalproject.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface BatchRepo extends JpaRepository<Batch, Long> {
    Optional<Batch> findFirstByAdvertisementAdvertisementId(Long advertisementId);

}

package com.example.finalproject.repository;

import com.example.finalproject.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepo extends JpaRepository<Batch, Long> {
}

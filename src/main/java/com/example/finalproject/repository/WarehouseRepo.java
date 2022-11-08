package com.example.finalproject.repository;

import com.example.finalproject.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepo extends JpaRepository<Warehouse, Long> {
}

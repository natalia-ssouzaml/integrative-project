package com.example.finalproject.repository;

import com.example.finalproject.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRepo extends JpaRepository<Buyer, Long> {
}

package com.example.finalproject.repository;

import com.example.finalproject.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuyerRepo extends JpaRepository<Buyer, Long> {
    Optional<Buyer> findBuyerByUsername(String username);
    Boolean existsByUsername(String username);
}

package com.example.finalproject.repository;

import com.example.finalproject.model.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseItemRepo extends JpaRepository<PurchaseItem, Long> {
}

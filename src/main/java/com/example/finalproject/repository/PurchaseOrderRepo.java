package com.example.finalproject.repository;

import com.example.finalproject.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder, Long> {

}

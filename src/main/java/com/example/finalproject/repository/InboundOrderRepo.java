package com.example.finalproject.repository;

import com.example.finalproject.model.InboundOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboundOrderRepo extends JpaRepository<InboundOrder, Long> {
}

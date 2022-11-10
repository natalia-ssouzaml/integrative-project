package com.example.finalproject.repository;

import com.example.finalproject.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepo extends JpaRepository<Advertisement,Long> {
}

package com.example.finalproject.service;

import com.example.finalproject.model.Advertisement;
import com.example.finalproject.model.Batch;

import java.util.List;

public interface IAdvertisementService {
    List<Advertisement> findAll();
    List<Advertisement>findAllByCategory(String category);

    Batch findAllByBatch(Long advertisementId);
}

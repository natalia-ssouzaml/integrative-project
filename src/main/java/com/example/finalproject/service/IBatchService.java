package com.example.finalproject.service;

import com.example.finalproject.model.Batch;

import java.util.List;

public interface IBatchService {
    List<Batch> findByAdvertisementId(Long advertisementId,String filter);
}

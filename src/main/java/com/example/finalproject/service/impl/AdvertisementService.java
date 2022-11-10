package com.example.finalproject.service.impl;

import com.example.finalproject.exception.NotFoundException;
import com.example.finalproject.model.Advertisement;
import com.example.finalproject.model.Enum.Category;
import com.example.finalproject.repository.AdvertisementRepo;
import com.example.finalproject.service.IAdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisementService implements IAdvertisementService {

    @Autowired
    AdvertisementRepo advertisementRepo;

    @Override
    public List<Advertisement> findAll() {
        List<Advertisement> advertisementList = advertisementRepo.findAll();
        if (advertisementList.isEmpty()) throw new NotFoundException("No advertisements found");

        return advertisementList;
    }

    @Override
    public List<Advertisement> findAllByCategory(String category) {
        return advertisementRepo.findAllByCategory(Category.CONGELADO);
    }

}
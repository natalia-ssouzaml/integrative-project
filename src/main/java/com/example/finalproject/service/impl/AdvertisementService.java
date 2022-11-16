package com.example.finalproject.service.impl;

import com.example.finalproject.exception.NotFoundException;
import com.example.finalproject.model.Advertisement;
import com.example.finalproject.model.Section;
import com.example.finalproject.repository.AdvertisementRepo;
import com.example.finalproject.repository.SectionRepo;
import com.example.finalproject.service.IAdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisementService implements IAdvertisementService {
    @Autowired
    AdvertisementRepo advertisementRepo;

    @Autowired
    SectionRepo sectionRepo;

    @Override
    public List<Advertisement> findAll() {
        List<Advertisement> advertisementList = advertisementRepo.findAll();
        if (advertisementList.isEmpty()) throw new NotFoundException("No advertisements found");

        return advertisementList;
    }

    @Override
    public List<Advertisement> findAllByCategory(String category) {
        categoryValidation(category);
        List<Advertisement> advertisementList = advertisementRepo.findAllByCategory(category);
        if (advertisementList.isEmpty()) throw new NotFoundException("No advertisement in this category");
        return advertisementList;
    }

    private void categoryValidation(String category) {
        sectionRepo.findByCategory(category).orElseThrow(() -> new NotFoundException("Category does not exist"));
    }

}
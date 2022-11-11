package com.example.finalproject.controller;

import com.example.finalproject.dto.AdvertisementDTO;
import com.example.finalproject.model.Advertisement;
import com.example.finalproject.model.Enum.Category;
import com.example.finalproject.repository.AdvertisementRepo;
import com.example.finalproject.service.IAdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/fresh-products")
public class AdvertisementController {

    @Autowired
    IAdvertisementService service;

    @GetMapping
    public ResponseEntity<List<AdvertisementDTO>> getAllAdvertisiment() {
        return ResponseEntity.ok(AdvertisementDTO.convertListToResponse(service.findAll()));
    }

    @GetMapping("/list/{category}")
    public ResponseEntity<List<AdvertisementDTO>> getAllAdvertisimentByCategory(@PathVariable String category) {
        return ResponseEntity.ok(AdvertisementDTO.convertListToResponse(service.findAllByCategory(category)));
    }
}


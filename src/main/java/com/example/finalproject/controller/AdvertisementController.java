package com.example.finalproject.controller;

import com.example.finalproject.dto.AdvertisementDTO;
import com.example.finalproject.service.IAdvertisementService;
import com.example.finalproject.service.IBatchService;
import org.springframework.beans.factory.annotation.Autowired;
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
    IAdvertisementService advertisementService;

    @Autowired
    IBatchService batchService;

    @GetMapping
    public ResponseEntity<List<AdvertisementDTO>> getAllAdvertisement() {
        return ResponseEntity.ok(AdvertisementDTO.convertListToResponse(advertisementService.findAll()));
    }

    @GetMapping("/list/{category}")
    public ResponseEntity<List<AdvertisementDTO>> getAllAdvertisementByCategory(@PathVariable String category) {
        return ResponseEntity.ok(AdvertisementDTO.convertListToResponse(advertisementService.findAllByCategory(category)));
    }
}


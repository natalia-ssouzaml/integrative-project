package com.example.finalproject.controller;

import com.example.finalproject.dto.AdvertisementDTO;
import com.example.finalproject.dto.ListBatchesAdvertisementDTO;
import com.example.finalproject.service.IAdvertisementService;
import com.example.finalproject.service.IBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/fresh-products")
public class AdvertisementController {

    @Autowired
    IAdvertisementService service;

    @Autowired
    IBatchService batchService;

    @GetMapping
    public ResponseEntity<List<AdvertisementDTO>> getAllAdvertisement() {
        return ResponseEntity.ok(AdvertisementDTO.convertListToResponse(service.findAll()));
    }

    @GetMapping("/list/{category}")
    public ResponseEntity<List<AdvertisementDTO>> getAllAdvertisementByCategory(@PathVariable String category) {
        return ResponseEntity.ok(AdvertisementDTO.convertListToResponse(service.findAllByCategory(category)));
    }


    @GetMapping("/list/advertisement")
    public ResponseEntity<ListBatchesAdvertisementDTO> getAllAdvertisementByBatch(Long advertisementId, @RequestParam(required = false) String filter) {
        return ResponseEntity.ok(new ListBatchesAdvertisementDTO(batchService.findByAdvertisementId(advertisementId, filter)));
    }


}


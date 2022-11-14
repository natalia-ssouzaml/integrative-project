package com.example.finalproject.controller;

import com.example.finalproject.dto.AdvertisementDTO;
import com.example.finalproject.dto.BatchLocationDTO;
import com.example.finalproject.dto.ListBatchesAdvertisementDTO;
import com.example.finalproject.exception.NotFoundException;
import com.example.finalproject.model.Advertisement;
import com.example.finalproject.model.Batch;
import com.example.finalproject.model.Enum.Category;
import com.example.finalproject.repository.AdvertisementRepo;
import com.example.finalproject.service.IAdvertisementService;
import com.example.finalproject.service.IBatchService;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/fresh-products")
public class AdvertisementController {

    @Autowired
    IAdvertisementService service;

    @Autowired
    IBatchService batchService;

    @GetMapping
    public ResponseEntity<List<AdvertisementDTO>> getAllAdvertisement() {
        // TODO: Padronizar response
        return ResponseEntity.ok(AdvertisementDTO.convertListToResponse(service.findAll()));
    }

    @GetMapping("/list/{category}")
    public ResponseEntity<List<AdvertisementDTO>> getAllAdvertisementByCategory(@PathVariable String category) {
        return ResponseEntity.ok(AdvertisementDTO.convertListToResponse(service.findAllByCategory(category)));
    }


    //TODO Se passar uma key de um parametro q nao existe, precisa tratar?
    @GetMapping("/list/advertisement")
    public ResponseEntity<ListBatchesAdvertisementDTO> getAllAdvertisementByBatch(Long advertisementId, @RequestParam(required = false) String filter) {
        return ResponseEntity.ok(new ListBatchesAdvertisementDTO(batchService.findByAdvertisementId(advertisementId, filter)));
    }


}


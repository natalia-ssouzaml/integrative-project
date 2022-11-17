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
    IAdvertisementService advertisementService;


    //TODO migrar o @GetMapping("/list/advertisement") para o batch service
    //TODO receber minusculo no parametro do @GetMapping("/list/advertisement")
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


    @GetMapping("/list/advertisement")
    public ResponseEntity<ListBatchesAdvertisementDTO> getAllAdvertisementByBatch(@RequestParam(value = "advertisement") Long advertisementCode, @RequestParam(required = false) String filter) {
        return ResponseEntity.ok(new ListBatchesAdvertisementDTO(batchService.findByAdvertisementCode(advertisementCode, filter)));
    }


}


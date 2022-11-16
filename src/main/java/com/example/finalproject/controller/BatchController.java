package com.example.finalproject.controller;

import com.example.finalproject.dto.BatchDTO;
import com.example.finalproject.dto.WarehouseQuantityAdvertisementDTO;
import com.example.finalproject.service.IBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/fresh-products")
public class BatchController {

    @Autowired
    private IBatchService batchService;

    @GetMapping("/due-date")
    public ResponseEntity<List<BatchDTO>> findAllBatchBySectorAndDueDate(@RequestParam int days,
                                                                         @RequestParam Long section) {
        return new ResponseEntity<>(BatchDTO.convertListToResponse(batchService.findAllBatchBySectorAndDueDate(days, section)), HttpStatus.OK);
    }

    @GetMapping("/due-date/list")
    public ResponseEntity<List<BatchDTO>> findAllBatchByCategoryAndDueDate(@RequestParam int days,
                                                                           @RequestParam String category,
                                                                           @RequestParam String order) {
        return new ResponseEntity<>(BatchDTO.convertListToResponse(batchService.findAllBatchByCategoryAndDueDate(days, category, order)), HttpStatus.OK);
    }

    @GetMapping("/list/warehouse")
    public ResponseEntity<WarehouseQuantityAdvertisementDTO>sumAdvertisementByWarehouse(@RequestParam(value = "advertisement") Long advertisementId ){
        return ResponseEntity.ok(new WarehouseQuantityAdvertisementDTO(advertisementId,batchService.findByAdvertisementId(advertisementId)));
    }

}

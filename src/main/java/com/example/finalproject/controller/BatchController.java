package com.example.finalproject.controller;

import com.example.finalproject.dto.BatchDTO;
import com.example.finalproject.dto.WarehouseQuantityAdvertisementDTO;
import com.example.finalproject.service.IBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/fresh-products")
public class BatchController {

    @Autowired
    private IBatchService batchService;

    //TODO Validar dias negativos
    //TODO validar asc/desc default
    @GetMapping("/due-date")
    public ResponseEntity<List<BatchDTO>> findAllBatchBySectorAndDueDate(@RequestParam int days,
                                                                         @RequestParam Long section) {
        return ResponseEntity.ok(BatchDTO.convertListToResponse(batchService.findAllBatchBySectorAndDueDate(days, section)));
    }

    @GetMapping("/due-date/list")
    public ResponseEntity<List<BatchDTO>> findAllBatchByCategoryAndDueDate(@RequestParam int days,
                                                                           @RequestParam String category,
                                                                           @RequestParam String order) {
        return ResponseEntity.ok(BatchDTO.convertListToResponse(batchService.findAllBatchByCategoryAndDueDate(days, category, order)));
    }

    @GetMapping("/list/warehouse")
    public ResponseEntity<WarehouseQuantityAdvertisementDTO> sumAdvertisementByWarehouse(@RequestParam(value = "advertisement") Long advertisementCode) {
        return ResponseEntity.ok(new WarehouseQuantityAdvertisementDTO(advertisementCode, batchService.findByAdvertisementCode(advertisementCode)));
    }

}

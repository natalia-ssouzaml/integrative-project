package com.example.finalproject.controller;

import com.example.finalproject.dto.BatchDTO;
import com.example.finalproject.service.IBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/due-date")
    public ResponseEntity<List<BatchDTO>> FindAllBatchBySectorAndDueDate(@RequestParam int days,
                                                                         @RequestParam Long section) {
        return new ResponseEntity<>(BatchDTO.convertListToResponse(batchService.FindAllBatchBySectorAndDueDate(days, section)), HttpStatus.OK);
    }

    @GetMapping("/due-date/list")
    public ResponseEntity<List<BatchDTO>> FindAllBatchByCategoryAndDueDate(@RequestParam int days,
                                                                           @RequestParam String category,
                                                                           @RequestParam String order) {
        return new ResponseEntity<>(BatchDTO.convertListToResponse(batchService.FindAllBatchByCategoryAndDueDate(days, category, order)), HttpStatus.OK);
    }

}

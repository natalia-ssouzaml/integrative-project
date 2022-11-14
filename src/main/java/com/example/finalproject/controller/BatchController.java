package com.example.finalproject.controller;

import com.example.finalproject.dto.BatchDTO;
import com.example.finalproject.service.IBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/fresh-products")
public class BatchController {

    @Autowired
    private IBatchService batchService;

    @GetMapping("due-date/days/{days}/section/{sectionCode}")
    public ResponseEntity<List<BatchDTO>> FindAllBatchBySectorAndDueDate(@PathVariable int days, @PathVariable Long sectionCode) {
        return new ResponseEntity<>(BatchDTO.convertListToResponse(batchService.FindAllBatchBySectorAndDueDate(days, sectionCode)), HttpStatus.OK);
    }

    @GetMapping("due-date/list/days/{days}/{category}")
    public ResponseEntity<List<BatchDTO>> FindAllBatchByCategoryAndDueDate(@PathVariable int days, @PathVariable String category) {
        return new ResponseEntity<>(BatchDTO.convertListToResponse(batchService.FindAllBatchByCategoryAndDueDate(days, category)), HttpStatus.OK);
    }

}

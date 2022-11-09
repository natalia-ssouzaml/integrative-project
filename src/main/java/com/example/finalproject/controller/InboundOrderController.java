package com.example.finalproject.controller;

import com.example.finalproject.dto.BatchDTO;
import com.example.finalproject.dto.InboundOrderCreateDTO;
import com.example.finalproject.dto.InboundOrderUpdateDTO;
import com.example.finalproject.model.Batch;
import com.example.finalproject.service.IInboundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/inboundOrder")
public class InboundOrderController {

    @Autowired
    private IInboundOrderService service;

    @PostMapping
    public ResponseEntity<List<BatchDTO>> createInboundOrder(@Valid @RequestBody InboundOrderCreateDTO inboundOrderCreateDTO) {
        List<BatchDTO> batchDTOList = BatchDTO.convertListToResponse(service.create(inboundOrderCreateDTO));
        return new ResponseEntity<>(batchDTOList, HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<List<BatchDTO>> updateInboundOrder(@RequestBody InboundOrderUpdateDTO inboundOrderUpdateDTO){
        List<BatchDTO> batchDTOList = BatchDTO.convertListToResponse(service.update(inboundOrderUpdateDTO));
        return new ResponseEntity<>(batchDTOList, HttpStatus.CREATED);
    }
}

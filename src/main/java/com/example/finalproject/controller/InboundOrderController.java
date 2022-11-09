package com.example.finalproject.controller;

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
    public ResponseEntity<List<Batch>> createInboundOrder(@Valid @RequestBody InboundOrderCreateDTO inboundOrderCreateDTO) {
        return new ResponseEntity<>(service.create(inboundOrderCreateDTO), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<List<Batch>> updateInboundOrder(@RequestBody InboundOrderUpdateDTO inboundOrderUpdateDTO){
        return new ResponseEntity<>(service.update(inboundOrderUpdateDTO), HttpStatus.CREATED);
    }
}

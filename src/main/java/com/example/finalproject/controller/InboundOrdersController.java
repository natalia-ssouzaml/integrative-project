package com.example.finalproject.controller;

import com.example.finalproject.dto.InboundOrderRequestDTO;
import com.example.finalproject.model.Batch;
import com.example.finalproject.model.InboundOrder;
import com.example.finalproject.service.IInboundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/inboundOrder")
public class InboundOrdersController {

    @Autowired
    private IInboundOrderService service;

    @PostMapping
    public ResponseEntity<List<Batch>> createInboundOrder(@RequestBody InboundOrderRequestDTO inboundOrderRequestDTO) {
        return new ResponseEntity<>(service.create(inboundOrderRequestDTO), HttpStatus.CREATED);
    }
}

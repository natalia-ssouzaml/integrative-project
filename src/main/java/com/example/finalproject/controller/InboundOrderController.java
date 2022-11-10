package com.example.finalproject.controller;

import com.example.finalproject.dto.BatchDTO;
import com.example.finalproject.dto.InboundOrderCreateDTO;
import com.example.finalproject.dto.InboundOrderUpdateDTO;
import com.example.finalproject.model.InboundOrder;
import com.example.finalproject.service.IInboundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/fresh-products")
public class InboundOrderController {

    @Autowired
    private IInboundOrderService service;

    @PostMapping("/inboundorder")
    public ResponseEntity<List<BatchDTO>> createInboundOrder(@Valid @RequestBody InboundOrderCreateDTO inboundOrderCreateDTO) {
        InboundOrder inboundOrder = InboundOrderCreateDTO.convertToInboundOrder(inboundOrderCreateDTO);
        Long sectionCode = inboundOrderCreateDTO.getSectionCode();
        Long warehouseCode = inboundOrderCreateDTO.getWarehouseCode();
        List<Long> advertisementIdsList = inboundOrderCreateDTO.getBatchStock().stream().map(b -> b.getAdvertisementId()).collect(Collectors.toList());
        List<BatchDTO> batchDTOList = BatchDTO.convertListToResponse(service.create(inboundOrder,warehouseCode,sectionCode,advertisementIdsList));
        return new ResponseEntity<>(batchDTOList, HttpStatus.CREATED);
    }

    @PutMapping("/inboundorder")
    public ResponseEntity<List<BatchDTO>> updateInboundOrder(@RequestBody InboundOrderUpdateDTO inboundOrderUpdateDTO){
        InboundOrder inboundOrder = InboundOrderUpdateDTO.convertToInboundOrder(inboundOrderUpdateDTO);
        Long sectionCode = inboundOrderUpdateDTO.getSectionCode();
        Long warehouseCode = inboundOrderUpdateDTO.getWarehouseCode();
        List<Long> advertisementIdsList = inboundOrderUpdateDTO.getBatchStock().stream().map(b -> b.getAdvertisementId()).collect(Collectors.toList());
        List<Long> batchNumbersList = inboundOrderUpdateDTO.getBatchStock().stream().map(b -> b.getBatchNumber()).collect(Collectors.toList());
        List<BatchDTO> batchDTOList = BatchDTO.convertListToResponse(service.update(inboundOrder, warehouseCode, sectionCode, advertisementIdsList, batchNumbersList));
        return new ResponseEntity<>(batchDTOList, HttpStatus.CREATED);
    }
}

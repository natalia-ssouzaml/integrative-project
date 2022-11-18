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
    private IInboundOrderService inboundOrderService;

    @PostMapping("/inboundorder")
    public ResponseEntity<List<BatchDTO>> createInboundOrder(@Valid @RequestBody InboundOrderCreateDTO inboundOrderCreateDTO) {
        InboundOrder inboundOrder = InboundOrderCreateDTO.convertToInboundOrder(inboundOrderCreateDTO);
        Long sectionCode = inboundOrderCreateDTO.getSectionCode();
        Long warehouseCode = inboundOrderCreateDTO.getWarehouseCode();
        List<Long> advertisementCodeList = inboundOrderCreateDTO.getBatchStock().stream().map(BatchDTO::getAdvertisementCode).collect(Collectors.toList());
        List<BatchDTO> batchDTOList = BatchDTO.convertListToResponse(inboundOrderService.create(inboundOrder, warehouseCode, sectionCode, advertisementCodeList));
        return new ResponseEntity<>(batchDTOList, HttpStatus.CREATED);
    }

    @PutMapping("/inboundorder")
    public ResponseEntity<List<BatchDTO>> updateInboundOrder(@RequestBody InboundOrderUpdateDTO inboundOrderUpdateDTO) {
        InboundOrder inboundOrder = InboundOrderUpdateDTO.convertToInboundOrder(inboundOrderUpdateDTO);
        Long sectionCode = inboundOrderUpdateDTO.getSectionCode();
        Long warehouseCode = inboundOrderUpdateDTO.getWarehouseCode();
        List<Long> advertisementCodeList = inboundOrderUpdateDTO.getBatchStock().stream().map(BatchDTO::getAdvertisementCode).collect(Collectors.toList());
        List<Long> batchCodeList = inboundOrderUpdateDTO.getBatchStock().stream().map(BatchDTO::getBatchCode).collect(Collectors.toList());
        List<BatchDTO> batchDTOList = BatchDTO.convertListToResponse(inboundOrderService.update(inboundOrder, warehouseCode, sectionCode, advertisementCodeList, batchCodeList));
        return new ResponseEntity<>(batchDTOList, HttpStatus.CREATED);
    }
}

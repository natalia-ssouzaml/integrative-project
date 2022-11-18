package com.example.finalproject.controller;

import com.example.finalproject.dto.PurchaseOrderCreateDTO;
import com.example.finalproject.dto.PurchaseOrderDTO;
import com.example.finalproject.dto.PurchaseOrderUpdateDTO;
import com.example.finalproject.model.PurchaseOrder;
import com.example.finalproject.service.IPurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class PurchaseOrderController {

    @Autowired
    private IPurchaseOrderService purchaseOrderService;

    @PostMapping("/orders")
    public ResponseEntity<String> createPurchaseOrder(@Valid @RequestBody PurchaseOrderCreateDTO purchaseOrderCreateDTO) {
        PurchaseOrder purchaseOrder = PurchaseOrderCreateDTO.convertToPurchaseOrder(purchaseOrderCreateDTO);
        return new ResponseEntity<>("Total price: " + purchaseOrderService.createPurchaseOrder(purchaseOrder), HttpStatus.CREATED);
    }

    @GetMapping("/orders/{purchaseCode}")
    public ResponseEntity<PurchaseOrderDTO> findAllAdvertisementsByPurchase(@PathVariable Long purchaseCode) {
        return new ResponseEntity<>(PurchaseOrderDTO.convertToResponse(purchaseOrderService.findAllAdvertisementsByPurchase(purchaseCode)), HttpStatus.OK);
    }

    @PutMapping("/orders/{purchaseCode}")
    public ResponseEntity<PurchaseOrderUpdateDTO> updatePurchaseStatus(@PathVariable Long purchaseCode) {
        return new ResponseEntity<>(PurchaseOrderUpdateDTO.convertToResponse(purchaseOrderService.updatePurchaseStatus(purchaseCode)), HttpStatus.CREATED);
    }
}

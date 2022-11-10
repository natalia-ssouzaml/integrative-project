package com.example.finalproject.controller;

import com.example.finalproject.dto.AdvertisementDTO;
import com.example.finalproject.model.PurchaseOrder;
import com.example.finalproject.service.IPurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/")
public class PurchaseOrderController {
    @Autowired
    IPurchaseOrderService purchaseOrderService;

    @GetMapping("/orders/{purchaseCode}")
    public ResponseEntity<List<AdvertisementDTO>> findAllAdvertisementsByPurchase(@PathVariable Long purchaseCode) {
        return ResponseEntity.ok(AdvertisementDTO.convertListToResponse(purchaseOrderService.findAllAdvertisementsByPurchase(purchaseCode)));
    }
    @PutMapping("/orders/{purchaseCode}")
    public ResponseEntity<PurchaseOrder> updatePurchaseStatus(@PathVariable Long purchaseCode){
        return ResponseEntity.ok(purchaseOrderService.updatePurchaseStatus(purchaseCode));

    }
}

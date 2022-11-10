package com.example.finalproject.service.impl;

import com.example.finalproject.exception.NotFoundException;
import com.example.finalproject.model.Advertisement;
import com.example.finalproject.model.PurchaseOrder;
import com.example.finalproject.repository.AdvertisementRepo;
import com.example.finalproject.repository.PurchaseOrderRepo;
import com.example.finalproject.service.IPurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService implements IPurchaseOrderService {

    @Autowired
    PurchaseOrderRepo purchaseRepo;

    @Autowired
    AdvertisementRepo advertisementRepo;

    @Override
    public List<Advertisement> findAllAdvertisementsByPurchase(Long purchaseCode) {
        PurchaseOrder purchaseOrder = (purchaseRepo.findById(purchaseCode).orElseThrow(() -> new NotFoundException("Purchase order not found")));
        return purchaseOrder.getAdvertisements();
    }

    public PurchaseOrder updatePurchaseStatus(Long purchaseCode) {
        PurchaseOrder purchaseOrder = (purchaseRepo.findById(purchaseCode).orElseThrow(() -> new NotFoundException("Purchase order not found")));
        purchaseOrder.setOrderStatus(true);
        return purchaseOrder;
    }
}

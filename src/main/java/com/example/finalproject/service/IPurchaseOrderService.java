package com.example.finalproject.service;

import com.example.finalproject.model.Advertisement;
import com.example.finalproject.model.PurchaseOrder;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IPurchaseOrderService {


    List<Advertisement> findAllAdvertisementsByPurchase(Long purchaseCode);

    PurchaseOrder updatePurchaseStatus(Long purchaseCode);
}

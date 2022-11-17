package com.example.finalproject.service;

import com.example.finalproject.model.PurchaseItem;
import com.example.finalproject.model.PurchaseOrder;

import java.math.BigDecimal;
import java.util.List;

public interface IPurchaseOrderService {

    List<PurchaseItem> findAllAdvertisementsByPurchase(Long purchaseCode);

    PurchaseOrder updatePurchaseStatus(Long purchaseCode);

    BigDecimal createPurchaseOrder(PurchaseOrder purchaseOrder);

}

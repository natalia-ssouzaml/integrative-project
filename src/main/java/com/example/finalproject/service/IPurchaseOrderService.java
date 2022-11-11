package com.example.finalproject.service;

import com.example.finalproject.dto.PurchaseOrderCreateDTO;
import com.example.finalproject.model.PurchaseOrder;

import java.math.BigDecimal;
import java.util.List;

public interface IPurchaseOrderService {

    BigDecimal createPurchaseOrder(PurchaseOrder purchaseOrder, List<Integer> quantity);
}

package com.example.finalproject.service;

import com.example.finalproject.model.PurchaseItem;
import com.example.finalproject.model.PurchaseOrder;

import java.math.BigDecimal;
import java.util.List;

public interface IPurchaseOrderService {

    /**
     * Find all advertisements by purchase.
     *
     * @param purchaseCode The code of the purchase.
     * @return List of PurchaseItem.
     */
    List<PurchaseItem> findAllAdvertisementsByPurchase(Long purchaseCode);

    /**
     * Update the status of a purchase order.
     *
     * @param purchaseCode The purchase code of the purchase order.
     * @return The updated purchase order.
     */
    PurchaseOrder updatePurchaseStatus(Long purchaseCode);

    /**
     * Create a purchase order and return its total price.
     *
     * @param purchaseOrder The purchase order object to be created.
     * @return The total price of the order.
     */
    BigDecimal createPurchaseOrder(PurchaseOrder purchaseOrder);

}

package com.example.finalproject.service;

import com.example.finalproject.model.PurchaseItem;
import com.example.finalproject.model.PurchaseOrder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IPurchaseOrderService {


    /**
     * It returns a list of advertisements that are associated with a purchase.
     *
     * @param purchaseCode The code of the purchase.
     * @return A Purchase order containing a list of advertisements associated with the purchase.
     */
    PurchaseOrder findAllAdvertisementsByPurchase(Long purchaseCode);

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


    /**
     * Find all purchase items by warehouse code, initial date and final date.
     *
     * @param warehouseCode The code of the warehouse you want to search for.
     * @param initialDate The initial date of the purchase.
     * @param finalDate The final date of the purchase.
     * @return List<PurchaseItem>
     */
    List<PurchaseItem> findAllByWarehouseInitialDateAndFinalDate(Long warehouseCode, LocalDate initialDate, LocalDate finalDate);
}

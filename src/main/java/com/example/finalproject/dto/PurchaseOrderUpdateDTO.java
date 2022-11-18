package com.example.finalproject.dto;

import com.example.finalproject.model.Enum.OrderStatus;
import com.example.finalproject.model.PurchaseOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderUpdateDTO {
    private LocalDateTime dateTime;
    private String buyerName;
    private OrderStatus orderStatus;
    private List<PurchaseAdvertisementDTO> purchaseAdvertisementList;
    private Double totalPrice;

    public PurchaseOrderUpdateDTO(PurchaseOrder purchaseOrder) {
        this.dateTime = LocalDateTime.now();
        this.buyerName = purchaseOrder.getBuyer().getName();
        this.orderStatus = purchaseOrder.getOrderStatus();
        this.purchaseAdvertisementList = PurchaseAdvertisementDTO.convertListToResponse(purchaseOrder.getPurchaseItems());
        this.totalPrice = purchaseOrder.getPurchaseItems().stream().mapToDouble(i -> i.getPrice().doubleValue()).sum();
    }

    public static PurchaseOrderUpdateDTO convertToResponse(PurchaseOrder purchaseOrder) {
        return new PurchaseOrderUpdateDTO(purchaseOrder);
    }
}

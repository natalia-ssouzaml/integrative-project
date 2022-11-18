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
public class PurchaseOrderDTO {
    private LocalDateTime dateTime;
    private String buyerName;
    private OrderStatus orderStatus;
    private List<PurchaseAdvertisementDTO> purchaseAdvertisementList;
    private Double totalPrice;

    public PurchaseOrderDTO(PurchaseOrder purchaseOrder) {
        this.dateTime = purchaseOrder.getDateTime();
        this.buyerName = purchaseOrder.getBuyer().getName();
        this.orderStatus = purchaseOrder.getOrderStatus();
        this.purchaseAdvertisementList = PurchaseAdvertisementDTO.convertListToResponse(purchaseOrder.getPurchaseItems());
        this.totalPrice = purchaseOrder.getPurchaseItems().stream().mapToDouble(i -> i.getPrice().doubleValue()).sum();
    }

    public static PurchaseOrderDTO convertToResponse(PurchaseOrder purchaseOrder) {
        return new PurchaseOrderDTO(purchaseOrder);
    }
}

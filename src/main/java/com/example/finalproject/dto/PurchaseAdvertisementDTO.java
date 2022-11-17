package com.example.finalproject.dto;

import com.example.finalproject.model.PurchaseItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseAdvertisementDTO {

    private String name;
    private BigDecimal price;
    private int quantity;
    private Double totalPrice;

    public PurchaseAdvertisementDTO(PurchaseItem purchaseItem) {
        this.name = purchaseItem.getAdvertisement().getName();
        this.price = purchaseItem.getAdvertisement().getPrice();
        this.quantity = purchaseItem.getQuantity();
        this.totalPrice = this.price.doubleValue() * this.quantity;
    }

    private static PurchaseAdvertisementDTO convertToResponse(PurchaseItem purchaseItem) {
        return new PurchaseAdvertisementDTO(purchaseItem);
    }

    public static List<PurchaseAdvertisementDTO> convertListToResponse(List<PurchaseItem> purchaseItemList) {
        return purchaseItemList.stream().map(PurchaseAdvertisementDTO::convertToResponse).collect(
                Collectors.toList());
    }
}

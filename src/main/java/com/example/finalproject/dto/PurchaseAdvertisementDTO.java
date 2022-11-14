package com.example.finalproject.dto;

import com.example.finalproject.model.Advertisement;
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

    public PurchaseAdvertisementDTO(Advertisement advertisement) {
        this.name = advertisement.getName();
        this.price = advertisement.getPrice();
        this.quantity = advertisement.getPurchaseItems().stream().mapToInt(i -> i.getQuantity()).sum();
        this.totalPrice = advertisement.getPurchaseItems().stream().mapToDouble(i -> i.getPrice().doubleValue()).sum();
    }

    private static PurchaseAdvertisementDTO convertToResponse(Advertisement advertisement) {
        return new PurchaseAdvertisementDTO(advertisement);
    }

    public static List<PurchaseAdvertisementDTO> convertListToResponse(List<Advertisement> advertisementList) {
        return advertisementList.stream().map(PurchaseAdvertisementDTO::convertToResponse).collect(
                Collectors.toList());
    }
}

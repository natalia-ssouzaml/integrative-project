package com.example.finalproject.dto;


import com.example.finalproject.model.Advertisement;
import com.example.finalproject.model.PurchaseItem;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseItemDTO {

    @NotNull
    private Long advertisementCode;

    @NotNull
    @Positive
    private int quantity;

    private static PurchaseItem convertToPurchaseItem(PurchaseItemDTO purchaseItemDTO) {
        return PurchaseItem.builder()
                .advertisement(Advertisement.builder().advertisementCode(purchaseItemDTO.getAdvertisementCode()).build())
                .quantity(purchaseItemDTO.getQuantity())
                .build();
    }

    public static List<PurchaseItem> convertToPurchaseItemList(List<PurchaseItemDTO> purchaseItemDTOList) {
        return purchaseItemDTOList.stream().map(PurchaseItemDTO::convertToPurchaseItem).collect(Collectors.toList());
    }

}

package com.example.finalproject.dto;


import com.example.finalproject.model.Buyer;
import com.example.finalproject.model.Enum.OrderStatus;
import com.example.finalproject.model.PurchaseOrder;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PurchaseOrderCreateDTO {

    @NotNull
    private Long buyerCode;

    @NotNull
    @Valid
    private List<PurchaseItemDTO> purchaseItems;

    public static PurchaseOrder convertToPurchaseOrder(PurchaseOrderCreateDTO purchaseOrderCreateDTO) {
        return PurchaseOrder.builder()
                .dateTime(LocalDateTime.now())
                .buyer(Buyer.builder().buyerCode(purchaseOrderCreateDTO.buyerCode).build())
                .orderStatus(OrderStatus.ABERTO)
                .purchaseItems(PurchaseItemDTO.convertToPurchaseItemList(purchaseOrderCreateDTO.getPurchaseItems()))
                .build();
    }
}

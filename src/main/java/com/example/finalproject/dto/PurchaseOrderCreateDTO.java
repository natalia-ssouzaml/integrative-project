package com.example.finalproject.dto;


import com.example.finalproject.model.Buyer;
import com.example.finalproject.model.Enum.OrderStatus;
import com.example.finalproject.model.PurchaseOrder;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PurchaseOrderCreateDTO {

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Positive
    private Long buyerCode;

    @NotNull
    @Valid
    private List<PurchaseItemDTO> purchaseItems;

    public static PurchaseOrder convertToPurchaseOrder(PurchaseOrderCreateDTO purchaseOrderCreateDTO) {
        return PurchaseOrder.builder()
                .dateTime(purchaseOrderCreateDTO.getDateTime())
                .buyer(Buyer.builder().buyerCode(purchaseOrderCreateDTO.buyerCode).build())
                .orderStatus(OrderStatus.ABERTO)
                .purchaseItems(PurchaseItemDTO.convertToPurchaseItemList(purchaseOrderCreateDTO.getPurchaseItems()))
                .build();
    }
}

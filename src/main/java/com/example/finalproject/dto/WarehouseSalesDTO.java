package com.example.finalproject.dto;

import com.example.finalproject.model.PurchaseItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class WarehouseSalesDTO {
    private Long warehouseCode;
    private Set<SectionSalesDTO> sections;

    private int totalQuantity;
    private double totalValue;

    public WarehouseSalesDTO(Long warehouseCode, List<PurchaseItem> purchaseItemList) {
        this.warehouseCode = warehouseCode;
        this.sections = SectionSalesDTO.convertToSet(warehouseCode, purchaseItemList);
        this.totalQuantity = getTotalQuantity(this.sections);
        this.totalValue = getTotalValue(this.sections);
    }

    private static int getTotalQuantity(Set<SectionSalesDTO> sectionSalesDTOS) {
        return sectionSalesDTOS.stream().mapToInt(SectionSalesDTO::getQuantity).sum();
    }
    private static double getTotalValue(Set<SectionSalesDTO> sectionSalesDTOS) {
        return sectionSalesDTOS.stream().mapToDouble(SectionSalesDTO::getTotalValue).sum();
    }
}
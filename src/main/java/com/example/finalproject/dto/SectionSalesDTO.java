package com.example.finalproject.dto;

import com.example.finalproject.model.Batch;
import com.example.finalproject.model.Enum.OrderStatus;
import com.example.finalproject.model.PurchaseItem;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class SectionSalesDTO {
    private String sectionName;
    private int quantity;
    private double totalValue;

    public static Set<SectionSalesDTO> convertToSet(Long warehouseCode, List<PurchaseItem> purchaseItemList) {
        Set<SectionSalesDTO> sectionsSalesSet = getSectionsSalesDTOWithName(warehouseCode, purchaseItemList);
        for (SectionSalesDTO sectionSalesDTO : sectionsSalesSet) {
            sectionSalesDTO.setQuantity(totalQuantityBySection(warehouseCode, sectionSalesDTO.getSectionName(), purchaseItemList));

            sectionSalesDTO.setTotalValue(totalSumBySection(warehouseCode,sectionSalesDTO.getSectionName(),purchaseItemList));
        }
        return sectionsSalesSet;
    }
    private static Set<SectionSalesDTO> getSectionsSalesDTOWithName(Long warehouseCode, List<PurchaseItem> purchaseItemList) {
        Set<SectionSalesDTO> sectionsSalesSet = new HashSet<>();
        Set<Batch> batchSet = getBatchesFilteredByWarehouse(warehouseCode, purchaseItemList);

        Iterator<String> sectionNames = batchSet.stream().map(b -> b.getInboundOrder().getSection().getCategory().name()).collect(Collectors.toSet()).iterator();

        while (sectionNames.hasNext()) {
            SectionSalesDTO sectionSalesDTO = new SectionSalesDTO();
            sectionSalesDTO.setSectionName(sectionNames.next());
            sectionsSalesSet.add(sectionSalesDTO);
        }

        return sectionsSalesSet;
    }
    private static Set<Batch> getBatchesFilteredByWarehouse(Long warehouseCode, List<PurchaseItem> purchaseItemList) {
        return purchaseItemList.stream().map(item -> item.getAdvertisement().getBatches()).flatMap(Collection::stream)
                .filter(batch -> batch.getInboundOrder().getSection().getWarehouse().getWarehouseCode().equals(warehouseCode)).collect(Collectors.toSet());
    }
    private static int totalQuantityBySection(Long warehouseCode, String sectionName, List<PurchaseItem> purchaseItemList) {
        Set<Batch> batchSet = getBatchesFilteredByWarehouse(warehouseCode, purchaseItemList);
        Set<PurchaseItem> filteredPurchaseItemSet = getPurchaseItemSetFilteredBySection(sectionName, batchSet);
        return filteredPurchaseItemSet.stream().mapToInt(PurchaseItem::getQuantity).sum();
    }
    private static double totalSumBySection(Long warehouseCode, String sectionName, List<PurchaseItem> purchaseItemList) {
        Set<Batch> batchSet = getBatchesFilteredByWarehouse(warehouseCode, purchaseItemList);
        Set<PurchaseItem> filteredPurchaseItemSet = getPurchaseItemSetFilteredBySection(sectionName, batchSet);
        return filteredPurchaseItemSet.stream().mapToDouble(item -> item.getPrice().doubleValue()).sum();
    }
    private static Set<PurchaseItem> getPurchaseItemSetFilteredBySection(String sectionName, Set<Batch> batchSet) {
        return batchSet.stream().filter(b -> b.getInboundOrder().getSection().getCategory().name().equals(sectionName)).map(
                filteredBatch -> filteredBatch.getAdvertisement().getPurchaseItems()
        ).flatMap(Collection::stream).filter(item -> item.getPurchaseOrder().getOrderStatus().equals(OrderStatus.FINALIZADO)).collect(Collectors.toSet());
    }



}
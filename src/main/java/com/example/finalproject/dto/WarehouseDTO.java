package com.example.finalproject.dto;

import com.example.finalproject.model.Batch;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class WarehouseDTO {
    private Long warehouseCode;
    private int totalQuantity;

    public WarehouseDTO(Long warehouseCode, List<Batch> batchList) {
        this.warehouseCode = warehouseCode;
        this.totalQuantity = getTotalQuantity(batchList);
    }

    private static int getTotalQuantity(List<Batch> batchList) {
        return batchList.stream().mapToInt(Batch::getProductQuantity).sum();
    }

    public static List<WarehouseDTO> convertListToResponse(Set<Long> warehouseCodeList, List<Batch> batchList) {
        List<WarehouseDTO> warehouseDTOList = new ArrayList<>();
        for (Long warehouseCode : warehouseCodeList) {
            List<Batch> filteredBatchListByWarehouse = batchList.stream().filter(b -> b.getInboundOrder().getSection().getWarehouse().getWarehouseCode().equals(warehouseCode)).collect(Collectors.toList());
            warehouseDTOList.add(new WarehouseDTO(warehouseCode, filteredBatchListByWarehouse));
        }
        return warehouseDTOList;
    }
}

package com.example.finalproject.dto;

import com.example.finalproject.model.Batch;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class WarehouseQuantityAdvertisementDTO {
    private Long advertisementCode;
    private String advertisementName;
    private List<WarehouseDTO> warehouses;


    public WarehouseQuantityAdvertisementDTO(Long advertisementCode, List<Batch> batchList) {
        this.advertisementCode = advertisementCode;
        this.advertisementName = batchList.get(0).getAdvertisement().getName();
        this.warehouses = WarehouseDTO.convertListToResponse(batchToWarehouseCodeList(batchList), batchList);

    }

    private Set<Long> batchToWarehouseCodeList(List<Batch> batchList) {
        return batchList.stream().map(b -> b.getInboundOrder().getSection().getWarehouse().getWarehouseCode()).collect(Collectors.toSet());
    }


}

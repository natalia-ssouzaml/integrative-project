package com.example.finalproject.dto;

import com.example.finalproject.model.Batch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BatchLocationDTO {

    private Long batchCode;
    private int currentQuantity;
    private LocalDate dueDate;
    private Long sectionCode;
    private Long warehouseCode;

    public BatchLocationDTO(Batch batch) {
        this.batchCode = batch.getBatchCode();
        this.currentQuantity = batch.getProductQuantity();
        this.dueDate = batch.getDueDate();
        this.sectionCode = batch.getInboundOrder().getSection().getSectionCode();
        this.warehouseCode = batch.getInboundOrder().getSection().getWarehouse().getWarehouseCode();
    }

    private static BatchLocationDTO convertToResponse(Batch batch) {
        return new BatchLocationDTO(batch);
    }

    public static List<BatchLocationDTO> convertListToResponse(List<Batch> batchList) {
        return batchList.stream().map(BatchLocationDTO::convertToResponse).collect(Collectors.toList());
    }

}

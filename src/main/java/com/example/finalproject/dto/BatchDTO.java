package com.example.finalproject.dto;

import com.example.finalproject.model.Batch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class
BatchDTO {
    private Long batchCode;

    @NotNull
    private Long advertisementCode;

    @NotNull
    private Float currentTemperature;

    @NotNull
    @Positive
    private int productQuantity;

    @NotNull
    private LocalDateTime manufacturingDateTime;

    @NotNull
    @Positive
    private Float volume;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    @Positive
    private BigDecimal price;


    public BatchDTO(Batch batch) {
        this.batchCode = batch.getBatchCode();
        this.advertisementCode = batch.getAdvertisement().getAdvertisementCode();
        this.currentTemperature = batch.getCurrentTemperature();
        this.productQuantity = batch.getProductQuantity();
        this.manufacturingDateTime = batch.getManufacturingDateTime();
        this.volume = batch.getVolume();
        this.dueDate = batch.getDueDate();
        this.price = batch.getPrice();
    }


    private static BatchDTO convertToResponse(Batch batch) {
        return new BatchDTO(batch);
    }

    public static List<BatchDTO> convertListToResponse(List<Batch> batchList) {
        return batchList.stream().map(BatchDTO::convertToResponse)
                .collect(Collectors.toList());
    }

    private static Batch convertToBatch(BatchDTO batchDTO) {
        return Batch.builder()
                .price(batchDTO.getPrice())
                .dueDate(batchDTO.getDueDate())
                .currentTemperature(batchDTO.getCurrentTemperature())
                .manufacturingDateTime(batchDTO.getManufacturingDateTime())
                .productQuantity(batchDTO.getProductQuantity())
                .volume(batchDTO.getVolume())
                .build();
    }

    public static List<Batch> convertToBatchList(List<BatchDTO> batchDTOList) {
        return batchDTOList.stream().map(BatchDTO::convertToBatch).collect(Collectors.toList());
    }
}
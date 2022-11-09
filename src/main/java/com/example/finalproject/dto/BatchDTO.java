package com.example.finalproject.dto;

import com.example.finalproject.model.Batch;
import com.example.finalproject.repository.AdvertisementRepo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class
BatchDTO {
    @Autowired
    private static AdvertisementRepo advertisementRepo;
    private Long batchNumber;

    @NotNull
    private Long advertisementId;

    @NotNull
    private Float currentTemperature;

    @NotNull
    private int productQuantity;

    @NotNull
    private LocalDateTime manufacturingDateTime;

    @NotNull
    private Float volume;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    private BigDecimal price;


    public BatchDTO(Batch batch) {
        this.batchNumber = batch.getBatchNumber();
        this.advertisementId = getAdvertisementId();
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
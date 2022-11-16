package com.example.finalproject.dto;

import com.example.finalproject.model.Batch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ListBatchesAdvertisementDTO {
    List<BatchLocationDTO> batchStock;
    private Long advertisementId;
    private String advertisementName;

    public ListBatchesAdvertisementDTO(List<Batch> batchList) {
        this.advertisementName = batchList.get(0).getAdvertisement().getName();
        this.advertisementId = batchList.get(0).getAdvertisement().getAdvertisementId();
        this.batchStock = BatchLocationDTO.convertListToResponse(batchList);
    }

}
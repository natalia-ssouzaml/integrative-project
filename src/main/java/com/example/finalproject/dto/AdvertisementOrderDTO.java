package com.example.finalproject.dto;


import com.example.finalproject.model.Advertisement;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementOrderDTO {

    @NotNull
    @Positive
    private Long advertisementId;

    @NotNull
    private int quantity;


    private static Advertisement convertToAdvertisement(AdvertisementOrderDTO advertisementOrderDTO) {
        return Advertisement.builder()
                .advertisementId(advertisementOrderDTO.getAdvertisementId())
                .build();
    }

    public static List<Advertisement> convertToAdvertisementList(List<AdvertisementOrderDTO> advertisementOrderDTOList) {
        return advertisementOrderDTOList.stream().map(AdvertisementOrderDTO::convertToAdvertisement).collect(Collectors.toList());
    }

}

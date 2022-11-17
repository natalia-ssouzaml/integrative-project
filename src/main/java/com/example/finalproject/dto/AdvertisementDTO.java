package com.example.finalproject.dto;

import com.example.finalproject.model.Advertisement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

//TODO  Separar DTOs por pastas request e response?
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementDTO {

    private String name;

    private BigDecimal price;

    public AdvertisementDTO(Advertisement advertisement) {
        this.name = advertisement.getName();
        this.price = advertisement.getPrice();
    }

    private static AdvertisementDTO convertToResponse(Advertisement advertisement) {
        return new AdvertisementDTO(advertisement);
    }

    public static List<AdvertisementDTO> convertListToResponse(List<Advertisement> advertisementList) {
        return advertisementList.stream().map(AdvertisementDTO::convertToResponse).collect(Collectors.toList());
    }
}

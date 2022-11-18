package com.example.finalproject.service.impl;

import com.example.finalproject.model.Advertisement;
import com.example.finalproject.model.Enum.Category;
import com.example.finalproject.model.Section;
import com.example.finalproject.model.Seller;
import com.example.finalproject.repository.AdvertisementRepo;
import com.example.finalproject.repository.SectionRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdvertisementServiceTest {

    @InjectMocks
    AdvertisementService advertisementService;

    @Mock
    SectionRepo sectionRepo;

    @Mock
    AdvertisementRepo advertisementRepo;

    private Advertisement advertisement;

    private List<Advertisement> advertisementList;

    private Section section;

    @BeforeEach
    void setup() {
        advertisementList = new ArrayList<>();
        advertisement = Advertisement.builder()
                .advertisementCode(2L)
                .name("Pizza")
                .price(BigDecimal.valueOf(4.0))
                .seller(Seller.builder().build())
                .build();

        advertisementList.add(advertisement);

        section = Section.builder()
                .sectionCode(1L)
                .category(Category.CONGELADO)
                .volume(10.0F)
                .accumulatedVolume(15.0F)
                .minTemperature(0F)
                .maxTemperature(15F)
                .build();

    }

    @Test
    void findAll_returnListAdvertisement_whenSuccess() {
        when(advertisementRepo.findAll()).thenReturn(advertisementList);
        var result = advertisementService.findAll();
        verify(advertisementRepo, times(1)).findAll();
        Assertions.assertNotNull(result);
    }

    @Test
    void findAllByCategory_returnListAdvertisement_whenCategoryExists() {
        when(advertisementRepo.findAllByCategory(any())).thenReturn(advertisementList);
        when(sectionRepo.findByCategory(any())).thenReturn(Optional.ofNullable(section));
        var result = advertisementService.findAllByCategory("congelado");
        verify(advertisementRepo, times(1)).findAllByCategory(any());
        Assertions.assertNotNull(result);
    }
}
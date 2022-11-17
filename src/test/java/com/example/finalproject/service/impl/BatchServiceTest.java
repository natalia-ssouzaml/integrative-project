package com.example.finalproject.service.impl;

import com.example.finalproject.exception.InvalidArgumentException;
import com.example.finalproject.exception.NotFoundException;
import com.example.finalproject.model.*;
import com.example.finalproject.model.Enum.Category;
import com.example.finalproject.repository.BatchRepo;
import com.example.finalproject.repository.SectionRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BatchServiceTest {

    @InjectMocks
    BatchService batchService;

    @Mock
    BatchRepo batchRepo;

    @Mock
    SectionRepo sectionRepo;

    private Advertisement advertisementI;
    private Advertisement advertisementII;
    private List<Batch> batchList;
    private Section section;
    private InboundOrder inboundOrder;
    private Batch batchIII;

    @BeforeEach
    void setup() {
        batchList = new ArrayList<>();
        section = Section.builder()
                .sectionCode(2L)
                .accumulatedVolume(100.0F)
                .volume(200.0F)
                .category(Category.CONGELADO)
                .maxTemperature(-18.0F)
                .minTemperature(-22.0F)
                .build();
        inboundOrder = InboundOrder.builder().orderCode(1L).orderDate(LocalDate.of(2019, 1, 1)).section(section).batchStock(batchList).build();

        advertisementI = Advertisement.builder()
                .advertisementCode(1L)
                .name("Hamburguer de Siri")
                .price(BigDecimal.valueOf(3.0))
                .seller(Seller.builder().build())
                .build();
        advertisementII = Advertisement.builder()
                .advertisementCode(2L)
                .name("Queijo de Siri")
                .price(BigDecimal.valueOf(10.0))
                .seller(Seller.builder().build())
                .build();

        Batch batchI =
                Batch.builder()
                        .batchCode(1L)
                        .advertisement(advertisementI)
                        .currentTemperature(-20.0F)
                        .productQuantity(100)
                        .manufacturingDateTime(LocalDateTime.of(2019, 1, 31, 22, 34))
                        .volume(15.0F)
                        .dueDate(LocalDate.of(2023, 1, 31))
                        .price(BigDecimal.valueOf(45.0D))
                        .inboundOrder(inboundOrder)
                        .build();
        Batch batchII =
                Batch.builder()
                        .batchCode(2L)
                        .advertisement(advertisementI)
                        .currentTemperature(-20.0F)
                        .productQuantity(50)
                        .manufacturingDateTime(LocalDateTime.of(2019, 1, 31, 22, 34))
                        .volume(10.0F)
                        .dueDate(LocalDate.of(2025, 1, 31))
                        .price(BigDecimal.valueOf(45.0D))
                        .inboundOrder(inboundOrder)
                        .build();
        batchIII =
                Batch.builder()
                        .batchCode(3L)
                        .advertisement(advertisementII)
                        .currentTemperature(-20.0F)
                        .productQuantity(50)
                        .manufacturingDateTime(LocalDateTime.of(2019, 1, 31, 22, 34))
                        .volume(10.0F)
                        .dueDate(LocalDate.of(2025, 1, 31))
                        .price(BigDecimal.valueOf(45.0D))
                        .inboundOrder(inboundOrder)
                        .build();
        batchList.add(batchI);
        batchList.add(batchII);
        batchList.add(batchIII);

    }

    @Test
    void findAllBatchBySectorAndDueDate_returnListBatch_whenSuccess() {
        int days = 100;
        when(sectionRepo.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(section));
        when(batchRepo.findAll()).thenReturn(batchList);

        List<Batch> batchListService = batchService.findAllBatchBySectorAndDueDate(days, section.getSectionCode());
        Assertions.assertNotNull(batchListService);
        Assertions.assertEquals(1, batchListService.size());
    }

    @Test
    void findAllBatchBySectorAndDueDate_returnInvalidArgumentException_whenNegativeDays() {
        int days = -100;
        Assertions.assertThrows(InvalidArgumentException.class, () -> batchService.findAllBatchBySectorAndDueDate(days, section.getSectionCode()));
    }

    @Test
    void findAllBatchBySectorAndDueDate_returnNotFoundException_whenSectionNotFound() {
        int days = 100;
        Assertions.assertThrows(NotFoundException.class, () -> batchService.findAllBatchBySectorAndDueDate(days, section.getSectionCode() + 1));
    }

    @Test
    void findAllBatchByCategoryAndDueDate_returnListBatch_whenSuccess() {
        int days = 1000;
        String category = "CONGELADO";
        when(sectionRepo.findByCategory(ArgumentMatchers.anyString())).thenReturn(Optional.ofNullable(section));
        when(batchRepo.findAll()).thenReturn(batchList);
        List<Batch> batchListService = batchService.findAllBatchByCategoryAndDueDate(days, category, "asc");

        Assertions.assertNotNull(batchListService);
        Assertions.assertEquals(LocalDate.of(2023, 01, 31), batchListService.get(0).getDueDate());

    }

    @Test
    void findByAdvertisementCode_returnListBatch_whenSuccess() {
        batchList.remove(batchIII);
        when(batchRepo.findByAdvertisementAdvertisementCode(ArgumentMatchers.anyLong())).thenReturn(batchList);
        List<Batch> batchListService = batchService.findByAdvertisementCode(advertisementI.getAdvertisementCode());
        Assertions.assertNotNull(batchListService);
        Assertions.assertEquals(2,batchListService.size());
    }

    @Test
    void findByAdvertisementCode_returnListBatchFilterByQuantity_whenSuccess() {
        batchList.remove(batchIII);
        when(batchRepo.findByAdvertisementAdvertisementCode(ArgumentMatchers.anyLong())).thenReturn(batchList);
        List<Batch> batchListService = batchService.findByAdvertisementCode(advertisementI.getAdvertisementCode(),"Q");
        Assertions.assertNotNull(batchListService);
        Assertions.assertEquals(2,batchListService.size());
        Assertions.assertEquals(50,batchListService.get(0).getProductQuantity());
    }

    @Test
    void findByAdvertisementCode_returnListBatchFilterByValid_whenSuccess() {
        batchList.remove(batchIII);
        when(batchRepo.findByAdvertisementAdvertisementCode(ArgumentMatchers.anyLong())).thenReturn(batchList);
        List<Batch> batchListService = batchService.findByAdvertisementCode(advertisementI.getAdvertisementCode(),"V");
        Assertions.assertNotNull(batchListService);
        Assertions.assertEquals(2,batchListService.size());
        Assertions.assertEquals(LocalDate.of(2023, 1, 31),batchListService.get(0).getDueDate());
    }

    @Test
    void findByAdvertisementCode_returnListBatchWhenFilterIsEmpty_whenSuccess() {
        batchList.remove(batchIII);
        when(batchRepo.findByAdvertisementAdvertisementCode(ArgumentMatchers.anyLong())).thenReturn(batchList);
        List<Batch> batchListService = batchService.findByAdvertisementCode(advertisementI.getAdvertisementCode(),"");
        Assertions.assertNotNull(batchListService);
        Assertions.assertEquals(2,batchListService.size());
    }

}
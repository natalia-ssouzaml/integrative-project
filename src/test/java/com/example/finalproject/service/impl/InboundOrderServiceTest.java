package com.example.finalproject.service.impl;

import com.example.finalproject.exception.*;
import com.example.finalproject.model.*;
import com.example.finalproject.model.Enum.Category;
import com.example.finalproject.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InboundOrderServiceTest {

    @InjectMocks
    InboundOrderService inboundOrderService;
    @Mock
    AdvertisementRepo advertisementRepo;
    @Mock
    private InboundOrderRepo inboundOrderRepo;
    @Mock
    private WarehouseRepo warehouseRepo;
    @Mock
    private SectionRepo sectionRepo;
    @Mock
    private BatchRepo batchRepo;

    private Warehouse warehouse;
    private Warehouse warehouseII;


    private Section section;

    private InboundOrder inboundOrder;

    private List<Long> advertisementIdsList;

    private List<Long> batchNumberList;

    private Batch batch;

    private Batch batchII;

    private List<Batch> batchList;

    private Advertisement advertisement;


    @BeforeEach
    void setup() {

        advertisementIdsList = new ArrayList<>();

        batchNumberList = new ArrayList<>();

        batchList = new ArrayList<>();

        advertisement = Advertisement.builder().advertisementCode(2L).name("Pizza").price(BigDecimal.valueOf(4.0)).seller(Seller.builder().build()).build();

        batch = Batch.builder()
                .batchCode(3L)
                .inboundOrder(inboundOrder)
                .advertisement(advertisement)
                .currentTemperature(-20.0F)
                .productQuantity(100)
                .manufacturingDateTime(LocalDateTime.of(2019, 01, 20, 22, 34))
                .volume(12.0F)
                .dueDate(LocalDate.of(2023, 01, 05))
                .price(BigDecimal.valueOf(45.0D))
                .build();

        batchII = Batch.builder()
                .batchCode(1L)
                .inboundOrder(inboundOrder)
                .advertisement(advertisement)
                .currentTemperature(-20.0F)
                .productQuantity(10)
                .manufacturingDateTime(LocalDateTime.of(2019, 02, 02, 05, 55))
                .volume(3.0F).dueDate(LocalDate.of(2023, 10, 25))
                .price(BigDecimal.valueOf(5.0D))
                .build();

        batchList.add(batch);
        batchList.add(batchII);


        warehouse = Warehouse.builder().warehouseCode(1L).sections(new ArrayList<>()).volume(10.0F).build();
        warehouseII = Warehouse.builder().warehouseCode(5L).sections(new ArrayList<>()).volume(10.0F).build();

        section = Section.builder().sectionCode(1L).warehouse(warehouse).category(Category.CONGELADO).volume(100.0F).accumulatedVolume(15.0F).minTemperature(-22F).maxTemperature(-18F).build();


        inboundOrder = InboundOrder.builder().orderCode(3L).orderDate(LocalDate.of(2022, 11, 14)).section(section).batchStock(batchList).build();


        advertisementIdsList = inboundOrder.getBatchStock().stream().map(b -> b.getAdvertisement().getAdvertisementCode()).collect(Collectors.toList());
        batchNumberList = inboundOrder.getBatchStock().stream().map(b -> b.getBatchCode()).collect(Collectors.toList());


    }

    @Test
    void createInboundOrder_returnListOfBatch_whenValidationsCorrect() {
        when(warehouseRepo.findById(anyLong())).thenReturn(Optional.ofNullable(warehouse));
        when(sectionRepo.findById(anyLong())).thenReturn(Optional.ofNullable(section));
        when(advertisementRepo.findById(any())).thenReturn(Optional.ofNullable(advertisement));
        when(inboundOrderRepo.save(inboundOrder)).thenReturn(inboundOrder);
        inboundOrder.getBatchStock().stream().mapToDouble(b -> b.getVolume()).sum();
        inboundOrderService.create(inboundOrder, warehouse.getWarehouseCode(), section.getSectionCode(), advertisementIdsList);
        verify(batchRepo, times(1)).saveAll(inboundOrder.getBatchStock());

    }


    @Test
    void createInboundOrder_returnInvalidDueDateException_whenDueDateLessThanThreeWeeks() {
        LocalDate dueDate = LocalDate.now();

        when(warehouseRepo.findById(anyLong())).thenReturn(Optional.ofNullable(warehouse));
        when(sectionRepo.findById(anyLong())).thenReturn(Optional.ofNullable(section));
        when(advertisementRepo.findById(any())).thenReturn(Optional.ofNullable(advertisement));

        inboundOrder.getBatchStock().get(0).setDueDate(dueDate);

        Assertions.assertThrows(InvalidDueDateException.class, () -> inboundOrderService.create(inboundOrder, warehouse.getWarehouseCode(), section.getSectionCode(), advertisementIdsList));
    }

    @Test
    void updateInboundOrder_returnListOfBatch_whenValidationsCorrect() {
        when(warehouseRepo.findById(anyLong())).thenReturn(Optional.ofNullable(warehouse));
        when(sectionRepo.findById(anyLong())).thenReturn(Optional.ofNullable(section));
        when(advertisementRepo.findById(any())).thenReturn(Optional.ofNullable(advertisement));
        when(batchRepo.findById(any())).thenReturn(Optional.ofNullable(batch));
        when(batchRepo.existsById(any())).thenReturn(true);
        inboundOrderService.update(inboundOrder, warehouse.getWarehouseCode(), section.getSectionCode(), advertisementIdsList, batchNumberList);
        verify(batchRepo, times(1)).saveAll(inboundOrder.getBatchStock());
    }


    @Test
    void createInboundOrder_returnNotFoundException_whenWarehouseAndSectionNotEquals() {
        section.setSectionCode(5L);
        section.setWarehouse(warehouseII);
        when(warehouseRepo.findById(any())).thenReturn(Optional.ofNullable(warehouse));
        when(sectionRepo.findById(anyLong())).thenReturn(Optional.ofNullable(section));
        assertThrows(NotFoundException.class, () -> inboundOrderService.create(inboundOrder, warehouse.getWarehouseCode(), section.getSectionCode(), advertisementIdsList));
    }

    @Test
    void createInboundOrder_returnInvalidTemperatureException_whenTemperatureInvalid() {
        batch.setCurrentTemperature(10f);
        when(warehouseRepo.findById(any())).thenReturn(Optional.ofNullable(warehouse));
        when(sectionRepo.findById(anyLong())).thenReturn(Optional.ofNullable(section));
        when(advertisementRepo.findById(any())).thenReturn(Optional.ofNullable(advertisement));
        assertThrows(InvalidTemperatureException.class, () -> inboundOrderService.create(inboundOrder, warehouse.getWarehouseCode(), section.getSectionCode(), advertisementIdsList));

    }

    @Test
    void createInboundOrder_returnVolumeNotAvailableExceptionn_whenVolumeInvalid() {
        batch.setVolume(90.0f);
        inboundOrder.getBatchStock().stream().mapToDouble(b -> b.getVolume()).sum();
        when(warehouseRepo.findById(any())).thenReturn(Optional.ofNullable(warehouse));
        when(sectionRepo.findById(anyLong())).thenReturn(Optional.ofNullable(section));
        when(advertisementRepo.findById(any())).thenReturn(Optional.ofNullable(advertisement));
        assertThrows(VolumeNotAvailableException.class, () -> inboundOrderService.create(inboundOrder, warehouse.getWarehouseCode(), section.getSectionCode(), advertisementIdsList));
    }
}
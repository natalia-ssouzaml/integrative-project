package com.example.finalproject.service.impl;

import com.example.finalproject.exception.PurchaseFailureException;
import com.example.finalproject.exception.QuantityNotAvailableException;
import com.example.finalproject.model.*;
import com.example.finalproject.model.Enum.Category;
import com.example.finalproject.model.Enum.OrderStatus;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseOrderServiceTest {
    @InjectMocks
    PurchaseOrderService purchaseOrderService;

    @Mock
    private PurchaseOrderRepo purchaseOrderRepo;

    @Mock
    private PurchaseItemRepo purchaseItemRepo;

    @Mock
    private AdvertisementRepo advertisementRepo;

    @Mock
    private BuyerRepo buyerRepo;

    @Mock
    private BatchRepo batchRepo;

    @Mock
    private SectionRepo sectionRepo;

    private PurchaseOrder purchaseOrder;

    private PurchaseItem purchaseItem;

    private Advertisement advertisement;

    private List<Advertisement> advertisementList;

    private List<PurchaseItem> purchaseItemList;

    private Buyer buyer;
    private Batch batch;
    private Batch batchII;
    private InboundOrder inboundOrder;
    private Section section;
    private Warehouse warehouse;
    private List<Batch> batchList;

    @BeforeEach
    void setUp() {

        buyer = Buyer.builder()
                .buyerCode(1L)
                .name("Neymar")
                .purchases(new ArrayList<>())
                .build();
        warehouse = Warehouse.builder().warehouseCode(1L).sections(new ArrayList<>()).volume(10.0F).build();

        section = Section.builder().sectionCode(1L).warehouse(warehouse).category(Category.CONGELADO).volume(100.0F).accumulatedVolume(15.0F).minTemperature(-22F).maxTemperature(-18F).build();
        batchList = new ArrayList<>();


        inboundOrder = InboundOrder.builder()
                .orderCode(3L)
                .orderDate(LocalDate.of(2022, 11, 14))
                .section(section)
                .batchStock(batchList)
                .build();


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
                .batchCode(3L)
                .inboundOrder(inboundOrder)
                .advertisement(advertisement)
                .currentTemperature(-20.0F)
                .productQuantity(20)
                .manufacturingDateTime(LocalDateTime.of(2019, 01, 20, 22, 34))
                .volume(12.0F)
                .dueDate(LocalDate.of(2023, 01, 05))
                .price(BigDecimal.valueOf(45.0D))
                .build();


        batchList.add(batch);
        batchList.add(batchII);


        advertisement = Advertisement.builder()
                .advertisementCode(2L)
                .name("Pizza")
                .price(BigDecimal.valueOf(4.0))
                .batches(batchList)
                .seller(Seller.builder().build())
                .build();


        purchaseItem = PurchaseItem.builder()
                .itemCode(1L)
                .price(BigDecimal.valueOf(22.0))
                .quantity(100)
                .purchaseOrder(purchaseOrder)
                .advertisement(advertisement)
                .build();

        advertisementList = new ArrayList<>();
        advertisementList.add(advertisement);
        purchaseItemList = new ArrayList<>();
        purchaseItemList.add(purchaseItem);

        purchaseOrder = PurchaseOrder.builder()
                .purchaseCode(1L)
                .dateTime(LocalDateTime.of(2019, 01, 20, 22, 34))
                .orderStatus(OrderStatus.ABERTO)
                .buyer(buyer)
                .purchaseItems(purchaseItemList)
                .build();

    }

    @Test
    void createPurchaseOrder() {
        when(buyerRepo.findById(anyLong())).thenReturn(Optional.ofNullable(buyer));
        when(advertisementRepo.findById(anyLong())).thenReturn(Optional.ofNullable(advertisement));
        purchaseOrderRepo.save(purchaseOrder);
        purchaseOrderService.createPurchaseOrder(purchaseOrder);
        verify(purchaseItemRepo, times(1)).saveAll(purchaseItemList);
    }

    @Test
    void createPurchaseOrder_returnQuantityNotAvailableException_whenQuantityInvalid() {
        purchaseItem.setQuantity(10000);
        when(buyerRepo.findById(anyLong())).thenReturn(Optional.ofNullable(buyer));
        when(advertisementRepo.findById(anyLong())).thenReturn(Optional.ofNullable(advertisement));
        purchaseOrderRepo.save(purchaseOrder);
        assertThrows(QuantityNotAvailableException.class, () -> purchaseOrderService.createPurchaseOrder(purchaseOrder));

    }

    @Test
    void findAllAdvertisementsByPurchase_returnListAdvertisement_whenSuccess() {
        when(purchaseOrderRepo.findById(anyLong())).thenReturn(Optional.ofNullable(purchaseOrder));
        var result = purchaseOrderService.findAllAdvertisementsByPurchase(purchaseOrder.getPurchaseCode());
        Assertions.assertNotNull(result);
    }

    @Test
    void updatePurchaseStatus_returnPurchaseOrder_whenValidationsCorrect() {
        when(purchaseOrderRepo.findById(anyLong())).thenReturn(Optional.ofNullable(purchaseOrder));
        purchaseOrderService.updatePurchaseStatus(purchaseOrder.getPurchaseCode());
        assertEquals(purchaseOrder.getOrderStatus(), OrderStatus.FINALIZADO);
        verify(purchaseOrderRepo, times(1)).save(purchaseOrder);
    }

    @Test
    void updatePurchaseStatus_returnPurchaseFailureException_whenOrderStatusEqualsFinalizado() {
        purchaseOrder.setOrderStatus(OrderStatus.FINALIZADO);
        when(purchaseOrderRepo.findById(anyLong())).thenReturn(Optional.ofNullable(purchaseOrder));
        assertThrows(PurchaseFailureException.class, () -> purchaseOrderService.updatePurchaseStatus(purchaseOrder.getPurchaseCode()));

    }

    @Test
    void updatePurchaseStatus_returnSetQuantityAndVolume_whenLastBatch() {
        purchaseItem.setQuantity(110);
        when(purchaseOrderRepo.findById(anyLong())).thenReturn(Optional.ofNullable(purchaseOrder));
        purchaseOrderService.updatePurchaseStatus(purchaseOrder.getPurchaseCode());
        verify(sectionRepo, times(2)).save(section);
        verify(batchRepo, times(1)).save(batch);
    }

    @Test
    void updatePurchaseStatus_returnPurchaseFailureException_whenBatchQuantityIsInvalid() {
        purchaseItem.setQuantity(1210);
        when(purchaseOrderRepo.findById(anyLong())).thenReturn(Optional.ofNullable(purchaseOrder));
        assertThrows(PurchaseFailureException.class, () -> purchaseOrderService.updatePurchaseStatus(purchaseOrder.getPurchaseCode()));
        assertEquals(purchaseOrder.getOrderStatus(), OrderStatus.INDISPONIVEL);
        verify(purchaseOrderRepo, times(1)).save(purchaseOrder);
    }
}
package com.example.finalproject.service.impl;

import com.example.finalproject.model.Advertisement;
import com.example.finalproject.model.Enum.OrderStatus;
import com.example.finalproject.model.PurchaseItem;
import com.example.finalproject.model.PurchaseOrder;
import com.example.finalproject.model.Seller;
import com.example.finalproject.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
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

    @BeforeEach
    void setUp() {

        purchaseOrder = PurchaseOrder.builder()
                .purchaseCode(1l)
                .dateTime(LocalDateTime.of(2019, 01, 20, 22, 34))
                .orderStatus(OrderStatus.ABERTO)
                .buyer(null)
                .purchaseItems(new ArrayList<>())
                .build();

        advertisement = Advertisement.builder()
                .advertisementCode(2l)
                .name("Pizza")
                .price(BigDecimal.valueOf(4.0))
                .seller(Seller.builder().build())
                .build();


        purchaseItem = PurchaseItem.builder()
                .itemCode(1L)
                .price(BigDecimal.valueOf(22.0))
                .quantity(10)
                .purchaseOrder(purchaseOrder)
                .advertisement(advertisement)
                .build();

        advertisementList = new ArrayList<>();
        advertisementList.add(advertisement);

    }

    @Test
    void createPurchaseOrder() {
    }

    @Test
    void findAllAdvertisementsByPurchase_returnListAdvertisement_whenSuccess() {
        when(purchaseOrderRepo.findById(anyLong())).thenReturn(Optional.ofNullable(purchaseOrder));
        var result = purchaseOrderService.findAllAdvertisementsByPurchase(purchaseOrder.getPurchaseCode());
        Assertions.assertNotNull(result);
    }

    @Test
    void updatePurchaseStatus() {
    }
}
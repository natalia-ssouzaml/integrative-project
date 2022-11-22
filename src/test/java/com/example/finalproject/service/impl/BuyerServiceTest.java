package com.example.finalproject.service.impl;

import com.example.finalproject.exception.BuyerException;
import com.example.finalproject.model.Buyer;
import com.example.finalproject.model.BuyerAuth;
import com.example.finalproject.repository.BuyerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuyerServiceTest {


    @InjectMocks
    BuyerService buyerService;

    @Mock
    BuyerRepo buyerRepo;

    private Buyer buyer;

    private BuyerAuth buyerAuth;
    @Captor
    private ArgumentCaptor<Buyer> buyerArgumentCaptor;

    @BeforeEach
    void setUp() {

        buyerAuth = BuyerAuth.builder()
                .name("Comprador")
                .password("senhasegura")
                .username("comprador@gmail.com")
                .build();
    }

    @Test
    void saveBuyer_returnNewBuyer_whenTheAttributesAreCorrect() {
        when(buyerRepo.existsByUsername(buyerAuth.getUsername())).thenReturn(false);
        when(buyerRepo.save(any())).thenReturn(buyer);
        buyerService.saveBuyer(buyerAuth);
        verify(buyerRepo).save(buyerArgumentCaptor.capture());

        assertEquals("Comprador", buyerArgumentCaptor.getValue().getName());
    }

    @Test
    void saveBuyer_returnBuyerException_whenBuyerAlreadyExists() {
        when(buyerRepo.existsByUsername(buyerAuth.getUsername())).thenReturn(true);
        assertThrows(BuyerException.class, () -> buyerService.saveBuyer(buyerAuth));


    }
}
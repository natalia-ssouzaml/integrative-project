package com.example.finalproject.service.impl;

import com.example.finalproject.exception.BuyerException;
import com.example.finalproject.model.Buyer;
import com.example.finalproject.model.BuyerAuth;
import com.example.finalproject.repository.BuyerRepo;
import com.example.finalproject.service.IBuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class BuyerService implements IBuyerService {

    @Autowired
    private BuyerRepo buyerRepo;

    public Buyer saveBuyer(BuyerAuth buyerAuth) {
        if (buyerRepo.existsByUsername(buyerAuth.getUsername())) {
            throw new BuyerException("This username has already been registered");
        }
        Buyer buyer = new Buyer();
        buyer.setName(buyerAuth.getName());
        buyer.setUsername(buyerAuth.getUsername());
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        buyer.setPassword(passwordEncoder.encode(buyerAuth.getPassword()).substring(8));
        buyer.setAuthorities("Buyer");
        Buyer savedBuyer = buyerRepo.save(buyer);
        return savedBuyer;
    }
}

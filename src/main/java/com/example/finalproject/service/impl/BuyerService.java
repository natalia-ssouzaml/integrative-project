package com.example.finalproject.service.impl;

import com.example.finalproject.exception.NotFoundException;
import com.example.finalproject.model.Buyer;
import com.example.finalproject.model.BuyerAuth;
import com.example.finalproject.repository.BuyerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuyerService {

    @Autowired
    private BuyerRepo buyerRepo;

    public Buyer saveBuyer(BuyerAuth buyerAuth) {
        if (buyerRepo.existsByUsername(buyerAuth.getUsername())) {
            throw new NotFoundException("This username has already been registered");
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

    public Optional<Buyer> findBuyerByUsername(String username) {
        return buyerRepo.findBuyerByUsername(username);
    }
}

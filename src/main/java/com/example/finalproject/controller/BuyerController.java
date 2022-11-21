package com.example.finalproject.controller;

import com.example.finalproject.jwt.JwtTokenUtil;
import com.example.finalproject.model.Buyer;
import com.example.finalproject.model.BuyerAuth;
import com.example.finalproject.service.impl.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/buyer")
public class BuyerController {

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    BuyerService service;

    @Autowired
    JwtTokenUtil jwtUtil;

    @PostMapping("/new")
    public ResponseEntity<Buyer> insertBuyer(@RequestBody @Valid BuyerAuth authUser) {

        Buyer buyer = service.saveBuyer(authUser);
        if (buyer != null) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid BuyerAuth authUser) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authUser.getUsername(), authUser.getPassword())
            );

            Buyer buyer = (Buyer) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(buyer);

            return ResponseEntity.ok().body(accessToken);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }

}

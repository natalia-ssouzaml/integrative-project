package com.example.finalproject.service;

import com.example.finalproject.model.Buyer;
import com.example.finalproject.model.BuyerAuth;

public interface IBuyerService {
    /**
     * Save buyer.
     *
     * @param buyerAuth  object received by controller, dto from Buyer .
     * @return A buyer.
     */
   Buyer saveBuyer(BuyerAuth buyerAuth);
}

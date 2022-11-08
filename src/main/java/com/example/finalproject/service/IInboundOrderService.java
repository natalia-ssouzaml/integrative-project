package com.example.finalproject.service;

import com.example.finalproject.dto.InboundOrderRequestDTO;
import com.example.finalproject.model.Batch;
import com.example.finalproject.model.InboundOrder;

import java.util.List;

public interface IInboundOrderService {
    List<Batch> create(InboundOrderRequestDTO orderRequest);
}

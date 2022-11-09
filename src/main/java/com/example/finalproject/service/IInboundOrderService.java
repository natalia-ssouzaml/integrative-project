package com.example.finalproject.service;

import com.example.finalproject.dto.InboundOrderCreateDTO;
import com.example.finalproject.dto.InboundOrderUpdateDTO;
import com.example.finalproject.model.Batch;

import java.util.List;

public interface IInboundOrderService {
    List<Batch> create(InboundOrderCreateDTO inboundOrderCreateDTO);
    List<Batch> update(InboundOrderUpdateDTO inboundOrderUpdateDTO);
}

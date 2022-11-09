package com.example.finalproject.service;

import com.example.finalproject.dto.BatchDTO;
import com.example.finalproject.dto.InboundOrderCreateDTO;
import com.example.finalproject.dto.InboundOrderUpdateDTO;
import com.example.finalproject.model.Batch;
import com.example.finalproject.model.InboundOrder;

import java.util.List;

public interface IInboundOrderService {
    List<Batch> create(InboundOrder inboundOrder,Long warehouseCode, Long section);
    List<Batch> update(InboundOrderUpdateDTO inboundOrderUpdateDTO);
}

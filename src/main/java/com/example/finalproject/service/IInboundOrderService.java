package com.example.finalproject.service;

import com.example.finalproject.model.Batch;
import com.example.finalproject.model.InboundOrder;

import java.util.List;

public interface IInboundOrderService {
    List<Batch> create(InboundOrder inboundOrder, Long warehouseCode, Long section, List<Long> advertisementList);

    List<Batch> update(InboundOrder inboundOrder, Long warehouseCode, Long section, List<Long> advertisementList, List<Long> batchNumbersList);
}

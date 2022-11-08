package com.example.finalproject.service.impl;

import com.example.finalproject.dto.InboundOrderRequestDTO;
import com.example.finalproject.model.Batch;
import com.example.finalproject.model.InboundOrder;
import com.example.finalproject.repository.BatchRepo;
import com.example.finalproject.repository.InboundOrderRepo;
import com.example.finalproject.repository.SectionRepo;
import com.example.finalproject.repository.WarehouseRepo;
import com.example.finalproject.service.IInboundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InboundOrderService implements IInboundOrderService {

    @Autowired
    private InboundOrderRepo inboundOrderRepo;

    @Autowired
    private WarehouseRepo warehouseRepo;

    @Autowired
    private SectionRepo sectionRepo;

    @Autowired
    private BatchRepo batchRepo;

    @Override
    public List<Batch> create(InboundOrderRequestDTO inboundOrderRequestDTO) {
        var warehouse = warehouseRepo.findById(inboundOrderRequestDTO.getWarehouseCode()).orElseThrow();
        var section = sectionRepo.findById(inboundOrderRequestDTO.getSectionCode()).orElseThrow();

        // Create inboundOrder
        InboundOrder inboundOrder = InboundOrder.builder()
                .orderDate(LocalDate.now())
                .section(section)
                .batchStock(inboundOrderRequestDTO.getBatchStock())
                .build();
        var response =  inboundOrderRepo.save(inboundOrder);

        for(Batch batch : inboundOrderRequestDTO.getBatchStock()) {
            batch.setOrderNumber(response);
        }

        // SAVE DO BATCH
        batchRepo.saveAll(inboundOrderRequestDTO.getBatchStock());
        return inboundOrderRequestDTO.getBatchStock();
    }

}

package com.example.finalproject.service.impl;

import com.example.finalproject.dto.InboundOrderUpdateDTO;
import com.example.finalproject.exception.InvalidTemperatureException;
import com.example.finalproject.exception.NotFoundException;
import com.example.finalproject.exception.VolumeNotAvailableException;
import com.example.finalproject.model.Batch;
import com.example.finalproject.model.InboundOrder;
import com.example.finalproject.model.Section;
import com.example.finalproject.model.Warehouse;
import com.example.finalproject.repository.*;
import com.example.finalproject.service.IInboundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    AdvertisementRepo advertisementRepo;

    @Autowired
    private BatchRepo batchRepo;

    @Override
    public List<Batch> create(InboundOrder inboundOrder,Long warehouseCode, Long sectionCode,List<Long>advertisementList) {

        // TODO: refazer validacao porque sempre é null
        //TODO validar se os Ids estao duplicados.

//        inboundOrderCreateDTO.getBatchStock().forEach(
//                id -> {
//                    var result = batchRepo.existsById(id.getBatchNumber());
//                    if (result) {
//                        throw new NotFoundException("BatchNumber already exists");
//                    }
//                });
        Warehouse warehouse = warehouseRepo.findById(warehouseCode).orElseThrow(() -> new NotFoundException("Warehouse not found"));
        Section section = sectionRepo.findById(sectionCode).orElseThrow(() -> new NotFoundException("Section not found"));
        warehouseSectionValidation(section, warehouse);

        inboundOrder.setSection(section);
        for (int i = 0; i < advertisementList.size() ; i++) {
            inboundOrder.getBatchStock().get(i).setAdvertisement(advertisementRepo.findById(advertisementList.get(i)).get());
        }

        InboundOrder savedInboundOrder = inboundOrderRepo.save(inboundOrder);

        setBatchOrderNumber(inboundOrder.getBatchStock(), section, savedInboundOrder);
        float totalVolume = batchesTotalVolume(inboundOrder.getBatchStock()).floatValue();
        volumeValidation(section, totalVolume);

        section.setAccumulatedVolume(totalVolume + section.getAccumulatedVolume());
        for (Batch batch : inboundOrder.getBatchStock()) {
            System.out.println(batch);
        }
        return batchRepo.saveAll(inboundOrder.getBatchStock());
    }

    public List<Batch> update(InboundOrderUpdateDTO inboundOrderUpdateDTO,Long warehouseCode, Long sectionCode,List<Long>advertisementList) {
       batchIdValidation(inboundOrderUpdateDTO);
        Warehouse warehouse = warehouseRepo.findById(inboundOrderUpdateDTO.getWarehouseCode()).orElseThrow(() -> new NotFoundException("Warehouse not found"));
        Section section = sectionRepo.findById(inboundOrderUpdateDTO.getSectionCode()).orElseThrow(() -> new NotFoundException("Section not found"));
        warehouseSectionValidation(section, warehouse);

        InboundOrder inboundOrder = InboundOrder.builder()
                .orderNumber(inboundOrderUpdateDTO.getOrderNumber())
                .section(section)
                .orderDate(LocalDate.now())
                //.batchStock(inboundOrderUpdateDTO.getBatchStock())
                .build();
        InboundOrder savedInboundOrder = inboundOrderRepo.save(inboundOrder);

        //setBatchOrderNumber(inboundOrderUpdateDTO.getBatchStock(), section, savedInboundOrder);

        return batchRepo.saveAll(inboundOrder.getBatchStock());
    }
    private void batchIdValidation(InboundOrderUpdateDTO inboundOrderUpdateDTO){
        inboundOrderUpdateDTO.getBatchStock().forEach(
                id -> {
                    var result = batchRepo.existsById(id.getBatchNumber());
                    if (!result) {
                        throw new NotFoundException("BatchNumber does not exists");
                    }
                });
    }


    private void setBatchOrderNumber(List<Batch> batchList, Section section, InboundOrder savedInboundOrder) {
        for (Batch batch : batchList) {
            temperatureValidation(section, batch);
            batch.setInboundOrder(savedInboundOrder);
        }
    }

    private Double batchesTotalVolume(List<Batch> batchList) {
        return batchList.stream().mapToDouble(b -> b.getVolume()).sum();
    }

    private void warehouseSectionValidation(Section section, Warehouse warehouse) {
        if (!(section.getWarehouse().equals(warehouse)))
            throw new NotFoundException("The section doesn't belong to this warehouse");
    }

    private void temperatureValidation(Section section, Batch batch) {
        float temperature = batch.getCurrentTemperature();

        if (!(temperature >= section.getMinTemperature() && temperature <= section.getMaxTemperature())) {
            throw new InvalidTemperatureException("Invalid temperature for this section: " + section.getCategory().name() + ". You sent the product " +
                    batch.getAdvertisement().getName() + " with the temperature: " + batch.getCurrentTemperature() + "°, but the acceptable range is between: " + section.getMinTemperature() + "° and " + section.getMaxTemperature() + "°");
        }
    }

    private void volumeValidation(Section section, float totalVolumeBatch) {
        float availableVolume = section.getVolume() - section.getAccumulatedVolume();
        if (availableVolume < totalVolumeBatch) {
            throw new VolumeNotAvailableException("You sent: " + totalVolumeBatch + "m³ but only: " + availableVolume +
                    "m³ is available");
        }
    }
}

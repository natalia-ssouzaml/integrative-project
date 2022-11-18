package com.example.finalproject.service.impl;

import com.example.finalproject.exception.InvalidTemperatureException;
import com.example.finalproject.exception.NotFoundException;
import com.example.finalproject.exception.VolumeNotAvailableException;
import com.example.finalproject.model.*;
import com.example.finalproject.repository.*;
import com.example.finalproject.service.IInboundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InboundOrderService implements IInboundOrderService {

    @Autowired
    AdvertisementRepo advertisementRepo;
    @Autowired
    private InboundOrderRepo inboundOrderRepo;
    @Autowired
    private WarehouseRepo warehouseRepo;
    @Autowired
    private SectionRepo sectionRepo;
    @Autowired
    private BatchRepo batchRepo;

    @Override
    public List<Batch> create(InboundOrder inboundOrder, Long warehouseCode, Long sectionCode, List<Long> advertisementList) {

        // TODO: validar se os Ids estao duplicados

        Warehouse warehouse = warehouseRepo.findById(warehouseCode).orElseThrow(() -> new NotFoundException("Warehouse not found"));
        Section section = sectionRepo.findById(sectionCode).orElseThrow(() -> new NotFoundException("Section not found"));
        warehouseSectionValidation(section, warehouse);

        inboundOrder.setSection(section);
        for (int i = 0; i < advertisementList.size(); i++) {
            Advertisement advertisement = advertisementRepo.findById(advertisementList.get(i)).orElseThrow(() -> new NotFoundException("Advertisement not found"));
            inboundOrder.getBatchStock().get(i).setAdvertisement(advertisement);
        }

        float totalVolume = batchesTotalVolume(inboundOrder.getBatchStock()).floatValue();
        volumeValidation(section, totalVolume);
        temperatureValidation(inboundOrder);
        inboundOrderRepo.save(inboundOrder);
        setBatchOrderCode(inboundOrder);

        section.setAccumulatedVolume(totalVolume + section.getAccumulatedVolume());

        return batchRepo.saveAll(inboundOrder.getBatchStock());
    }

    public List<Batch> update(InboundOrder inboundOrder, Long warehouseCode, Long sectionCode, List<Long> advertisementList, List<Long> batchCodeList) {
        Warehouse warehouse = warehouseRepo.findById(warehouseCode).orElseThrow(() -> new NotFoundException("Warehouse not found"));
        Section section = sectionRepo.findById(sectionCode).orElseThrow(() -> new NotFoundException("Section not found"));
        warehouseSectionValidation(section, warehouse);

        inboundOrder.setSection(section);
        for (int i = 0; i < advertisementList.size(); i++) {
            Advertisement advertisement = advertisementRepo.findById(advertisementList.get(i)).orElseThrow(() -> new NotFoundException("Advertisement not found"));
            Batch batch = batchRepo.findById(batchCodeList.get(i)).orElseThrow(() -> new NotFoundException("Batch not found"));

            inboundOrder.getBatchStock().get(i).setAdvertisement(advertisement);
            inboundOrder.getBatchStock().get(i).setBatchCode(batch.getBatchCode());
        }

        float totalVolume = batchesTotalVolume(inboundOrder.getBatchStock()).floatValue();
        volumeValidation(inboundOrder.getSection(), totalVolume);
        temperatureValidation(inboundOrder);

        setBatchOrderCode(inboundOrder);
        batchCodeValidation(inboundOrder);


        return batchRepo.saveAll(inboundOrder.getBatchStock());
    }

    private void batchCodeValidation(InboundOrder inboundOrder) {
        inboundOrder.getBatchStock().forEach(
                id -> {
                    boolean result = batchRepo.existsById(id.getBatchCode());
                    if (!result) throw new NotFoundException("BatchCode does not exists");
                });
    }


    private void setBatchOrderCode(InboundOrder inboundOrder) {
        List<Batch> batchList = inboundOrder.getBatchStock();
        for (Batch batch : batchList) {
            batch.setInboundOrder(inboundOrder);
        }
    }

    private Double batchesTotalVolume(List<Batch> batchList) {
        return batchList.stream().mapToDouble(Batch::getVolume).sum();
    }

    private void warehouseSectionValidation(Section section, Warehouse warehouse) {
        if (!(section.getWarehouse().equals(warehouse)))
            throw new NotFoundException("The section doesn't belong to this warehouse");
    }

    private void temperatureValidation(InboundOrder inboundOrder) {
        List<Batch> batchList = inboundOrder.getBatchStock();

        Section section = inboundOrder.getSection();

        for (Batch batch : batchList) {
            float temperature = batch.getCurrentTemperature();
            if (!(temperature >= section.getMinTemperature() && temperature <= section.getMaxTemperature())) {
                throw new InvalidTemperatureException("Invalid temperature for this section: " + section.getCategory().name() + ". You sent the product " +
                        batch.getAdvertisement().getName() + " with the temperature: " + batch.getCurrentTemperature() + "°, but the acceptable range is between: " + section.getMinTemperature() + "° and " + section.getMaxTemperature() + "°");
            }
        }
    }

    private void volumeValidation(Section section, float totalVolumeBatch) {
        float availableVolume = section.getVolume() - section.getAccumulatedVolume();
        if (availableVolume < totalVolumeBatch) {
            throw new VolumeNotAvailableException("You sent: " + totalVolumeBatch + " m³ but only: " + availableVolume +
                    " m³ is available");
        }
    }
}

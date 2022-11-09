package com.example.finalproject.service.impl;

import com.example.finalproject.dto.InboundOrderRequestDTO;
import com.example.finalproject.exception.InvalidTemperatureException;
import com.example.finalproject.exception.NotFoundException;
import com.example.finalproject.exception.VolumeNotAvailableException;
import com.example.finalproject.model.Batch;
import com.example.finalproject.model.Enum.Category;
import com.example.finalproject.model.InboundOrder;
import com.example.finalproject.model.Section;
import com.example.finalproject.model.Warehouse;
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


    //    CENÁRIO 1: O produto de um vendedor é registrado.
//    DESDE que o produto de um Vendedor é registrado
//    E que o armazém é válido OK
//    E que o representante pertence ao armazém OK
//    E que o setor é válido OK
//    E que o setor corresponde ao tipo de produto OK
//    E que o setor tenha espaço disponível OK
//    QUANDO o representante entra no setor
//    ENTÃO o registro de compra é criado
//    E o lote é atribuído a um setor OK
//    E o representante é associado ao registro de estoque OK
//            VALIDAÇÃO
//▪ Registre o lote no setor correspondente.
//            ▪ Verifique se o setor de warehouse está sendo registrado corretamente.
    @Override
    public List<Batch> create(InboundOrderRequestDTO inboundOrderRequestDTO) {
        Warehouse warehouse = warehouseRepo.findById(inboundOrderRequestDTO.getWarehouseCode()).orElseThrow(() -> new NotFoundException("Warehouse not found"));
        Section section = sectionRepo.findById(inboundOrderRequestDTO.getSectionCode()).orElseThrow(() -> new NotFoundException("Section not found"));
        warehouseSectionValidation(section, warehouse);

        // Create inboundOrder
        InboundOrder inboundOrder = InboundOrder.builder()
                .orderDate(LocalDate.now())
                .section(section)
                .batchStock(inboundOrderRequestDTO.getBatchStock())
                .build();

        InboundOrder savedInboundOrder = inboundOrderRepo.save(inboundOrder);

        setBatchOrderNumber(inboundOrderRequestDTO.getBatchStock(), section, savedInboundOrder);
        float totalVolume = batchesTotalVolume(inboundOrderRequestDTO.getBatchStock()).floatValue();
        volumeValidation(section, totalVolume);

        section.setAccumulatedVolume(totalVolume + section.getAccumulatedVolume());

        return batchRepo.saveAll(inboundOrderRequestDTO.getBatchStock());
    }

    private void setBatchOrderNumber(List<Batch> batchList, Section section, InboundOrder savedInboundOrder) {
        for (Batch batch : batchList) {
            temperatureValidation(section, batch);
            batch.setOrderNumber(savedInboundOrder);
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

        //TODO FindByIdAdvertsiment and get name
        if (!(temperature >= section.getMinTemperature() && temperature <= section.getMaxTemperature())) {
            throw new InvalidTemperatureException("Invalid temperature for this section: " + section.getCategory().name() + ". You sent the product " +
                    batch.getAdvertisementId() + " with the temperature: " + batch.getCurrentTemperature() + "°, but the acceptable range is between: " + section.getMinTemperature() + "° and " + section.getMaxTemperature() + "°");
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

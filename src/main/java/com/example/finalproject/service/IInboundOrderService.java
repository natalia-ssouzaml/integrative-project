package com.example.finalproject.service;

import com.example.finalproject.model.Batch;
import com.example.finalproject.model.InboundOrder;

import java.util.List;

public interface IInboundOrderService {
    /**
     * Create an inbound order given a list of batches.
     *
     * @param inboundOrder The inbound order object.
     * @param warehouseCode The warehouse code of the warehouse where the inbound order is to be created.
     * @param section The section of the warehouse where the goods are stored.
     * @param advertisementList The list of advertisement codes.
     * @return A list of Batch objects.
     */
    List<Batch> create(InboundOrder inboundOrder, Long warehouseCode, Long section, List<Long> advertisementList);

    /**
     * Update the inbound order, warehouse, section, advertisement, and batch
     *
     * @param inboundOrder The inbound order object.
     * @param warehouseCode The warehouse code of the warehouse where the inbound order is located.
     * @param section The section of the warehouse where the goods are stored.
     * @param advertisementList The list of advertisement codes.
     * @param batchCodeList The list of batch codes to be updated.
     * @return An updated list of Batch objects.
     */
    List<Batch> update(InboundOrder inboundOrder, Long warehouseCode, Long section, List<Long> advertisementList, List<Long> batchCodeList);
}

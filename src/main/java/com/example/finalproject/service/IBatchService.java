package com.example.finalproject.service;

import com.example.finalproject.model.Batch;

import java.util.List;

/**
 * The interface Batch service.
 */
public interface IBatchService {

    /**
     * Find all batches that are due in the next X days and belong to a specific sector
     *
     * @param days        The number of days from the current date.
     * @param sectionCode The code of the section you want to find batches for.
     * @return List of Batch objects
     */
    List<Batch> findAllBatchBySectorAndDueDate(int days, Long sectionCode);

    List<Batch> findAllBatchByCategoryAndDueDate(int days, String category, String order);

    List<Batch> findByAdvertisementCode(Long advertisementCode, String filter);

    List<Batch> findByAdvertisementCode(Long advertisementCode);
}

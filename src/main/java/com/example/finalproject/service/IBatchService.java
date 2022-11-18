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

    /**
     * Find all batches that are due in the next X days, and belong to a specific category and order by due date.
     *
     * @param days The number of days from the current date to search for batches.
     * @param category The category of the batch.
     * @param order "ASC" or "DESC"
     * @return A list of batches that are due in the next x days and are of a certain category.
     */
    List<Batch> findAllBatchByCategoryAndDueDate(int days, String category, String order);

    /**
     * "Find all batches that have the given advertisement code and order by the given param."
     *
     * The order param is a string that can be used to order the results.
     *
     * @param advertisementCode The advertisement code of the batch you want to find.
     * @param order This is a string that will be used to order the results.
     * @return A list of Batch objects.
     */
    List<Batch> findByAdvertisementCode(Long advertisementCode, String order);

    /**
     * Find all batches that have the given advertisement code.
     *
     * @param advertisementCode The advertisement code of the batch you want to find.
     * @return A list of Batch objects.
     */
    List<Batch> findByAdvertisementCode(Long advertisementCode);


}

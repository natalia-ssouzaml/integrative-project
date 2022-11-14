package com.example.finalproject.service.impl;

import com.example.finalproject.exception.NotFoundException;
import com.example.finalproject.model.Batch;
import com.example.finalproject.repository.BatchRepo;
import com.example.finalproject.service.IBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchService implements IBatchService {
    @Autowired
    BatchRepo batchRepo;

    @Override
    public List<Batch> findByAdvertisementId(Long advertisementId,String filter) {
        List<Batch> batchList = batchRepo.findByAdvertisementAdvertisementId(advertisementId);
        if (batchList.isEmpty()) throw new NotFoundException("Advertisement does not belong to any batch");
        return sortByFilter(batchList,filter);
    }

    private List<Batch> sortByFilter(List<Batch>batchList, String filter) {
        if(filter == null) return batchList;
        switch (filter) {
            case "Q":
                return batchList.stream().sorted(Comparator.comparing(Batch::getProductQuantity)).collect(Collectors.toList());
            case "V":
                return batchList.stream().sorted(Comparator.comparing(Batch::getDueDate)).collect(Collectors.toList());
            default:
                return batchList;
        }
    }
}

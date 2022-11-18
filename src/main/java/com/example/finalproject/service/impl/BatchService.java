package com.example.finalproject.service.impl;

import com.example.finalproject.exception.InvalidArgumentException;
import com.example.finalproject.exception.NotFoundException;
import com.example.finalproject.model.Batch;
import com.example.finalproject.model.Section;
import com.example.finalproject.repository.BatchRepo;
import com.example.finalproject.repository.SectionRepo;
import com.example.finalproject.service.IBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchService implements IBatchService {

    @Autowired
    BatchRepo batchRepo;

    @Autowired
    SectionRepo sectionRepo;

    @Override
    public List<Batch> findAllBatchBySectorAndDueDate(int days, Long sectionCode) {
        if (days <= 0) throw new InvalidArgumentException("Number of days must be positive");
        sectionRepo.findById(sectionCode).orElseThrow(() -> new NotFoundException("Section not found"));
        LocalDate initialDate = LocalDate.now();
        LocalDate limitDate = initialDate.plusDays(days);

        return batchRepo.findAll().stream()
                .filter(b -> b.getDueDate().isAfter(initialDate.minusDays(1))
                        && b.getDueDate().isBefore(limitDate.plusDays(1)))
                .filter(b -> b.getInboundOrder().getSection().getSectionCode().equals(sectionCode))
                .sorted(Comparator.comparing(Batch::getDueDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Batch> findAllBatchByCategoryAndDueDate(int days, String category, String order) {
        if (days <= 0) throw new InvalidArgumentException("Number of days must be positive");
        if (order == null) order = "asc";
        Section section = sectionRepo.findByCategory(category).orElseThrow(() -> new NotFoundException("Category does not exist"));
        LocalDate initialDate = LocalDate.now();
        LocalDate limitDate = initialDate.plusDays(days);

        return batchRepo.findAll().stream()
                .filter(b -> b.getDueDate().isAfter(initialDate.minusDays(1))
                        && b.getDueDate().isBefore(limitDate.plusDays(1)))
                .filter(b -> b.getInboundOrder().getSection().getCategory().equals(section.getCategory()))
                .sorted(order.equalsIgnoreCase("desc") ? Comparator.comparing(Batch::getDueDate).reversed() : Comparator.comparing(Batch::getDueDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Batch> findByAdvertisementCode(Long advertisementCode, String order) {
        List<Batch> batchList = findByAdvertisementCode(advertisementCode);
        return sortByFilter(batchList, order);
    }

    @Override
    public List<Batch> findByAdvertisementCode(Long advertisementCode) {
        List<Batch> batchList = batchRepo.findByAdvertisementAdvertisementCode(advertisementCode);
        if (batchList.isEmpty()) throw new NotFoundException("Advertisement does not belong to any batch");
        return batchList;
    }

    private List<Batch> sortByFilter(List<Batch> batchList, String order) {
        if (order == null) return batchList;
        switch (order.toLowerCase()) {
            case "q":
                return batchList.stream().sorted(Comparator.comparing(Batch::getProductQuantity)).collect(Collectors.toList());
            case "v":
                return batchList.stream().sorted(Comparator.comparing(Batch::getDueDate)).collect(Collectors.toList());
            default:
                return batchList;
        }
    }
}

package com.example.finalproject.service.impl;

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
    public List<Batch> FindAllBatchBySectorAndDueDate(int days, Long sectionCode) {
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
    public List<Batch> FindAllBatchByCategoryAndDueDate(int days, String category, String order) {
        Section section = sectionRepo.findByCategory(category).orElseThrow(() -> new NotFoundException("Category does not exist"));
        LocalDate initialDate = LocalDate.now();
        LocalDate limitDate = initialDate.plusDays(days);

        return batchRepo.findAll().stream()
                .filter(b -> b.getDueDate().isAfter(initialDate.minusDays(1))
                        && b.getDueDate().isBefore(limitDate.plusDays(1)))
                .filter(b -> b.getInboundOrder().getSection().getCategory().equals(section.getCategory()))
                .sorted(order.equals("asc") ? Comparator.comparing(Batch::getDueDate) : Comparator.comparing(Batch::getDueDate).reversed())
                .collect(Collectors.toList());
    }
}

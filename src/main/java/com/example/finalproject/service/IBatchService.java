package com.example.finalproject.service;

import com.example.finalproject.model.Batch;

import java.util.List;

public interface IBatchService {

    List<Batch> FindAllBatchBySectorAndDueDate(int days, Long sectionCode);

    List<Batch> FindAllBatchByCategoryAndDueDate(int days, String category);
}

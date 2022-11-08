package com.example.finalproject.dto;

import com.example.finalproject.model.Batch;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class InboundOrderRequestDTO {

    private Long orderNumber;

    private LocalDate orderDate;

    private Long sectionCode;

    private Long warehouseCode;

    private List<Batch> batchStock;

}

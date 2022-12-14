package com.example.finalproject.dto;

import com.example.finalproject.model.InboundOrder;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

import static com.example.finalproject.dto.BatchDTO.convertToBatchList;

@Getter
public class InboundOrderCreateDTO {

    @NotNull
    @Positive
    private Long sectionCode;

    @NotNull
    private Long warehouseCode;

    @NotNull
    @Valid
    private List<BatchDTO> batchStock;

    public static InboundOrder convertToInboundOrder(InboundOrderCreateDTO inboundOrderCreateDTO) {
        return InboundOrder.builder()
                .batchStock(convertToBatchList(inboundOrderCreateDTO.getBatchStock()))
                .orderDate(LocalDate.now())
                .build();
    }
}

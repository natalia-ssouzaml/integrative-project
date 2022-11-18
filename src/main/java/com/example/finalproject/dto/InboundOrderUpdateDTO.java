package com.example.finalproject.dto;

import com.example.finalproject.model.InboundOrder;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;


@Getter
public class InboundOrderUpdateDTO {
    @NotNull
    @Positive
    private Long sectionCode;

    @NotNull
    private Long warehouseCode;

    @NotNull
    private Long orderCode;

    @NotNull
    @Valid
    private List<BatchDTO> batchStock;

    public static InboundOrder convertToInboundOrder(InboundOrderUpdateDTO inboundOrderUpdateDTO) {
        return InboundOrder.builder()
                .batchStock(BatchDTO.convertToBatchList(inboundOrderUpdateDTO.getBatchStock()))
                .orderDate(LocalDate.now())
                .orderCode(inboundOrderUpdateDTO.getOrderCode())
                .build();
    }

}

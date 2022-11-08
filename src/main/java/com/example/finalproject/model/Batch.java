package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchNumber;

    @Column(nullable = false)
    private Long advertisementId;

    @Column(nullable = false)
    private Float currentTemperature;

    @Column(nullable = false)
    private int productQuantity;

    @Column(nullable = false)
    private LocalDateTime manufacturingDateTime;

    @Column(nullable = false)
    private Float volume;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "order_number")
    @JsonIgnoreProperties("batchStock")
    private InboundOrder orderNumber;
}

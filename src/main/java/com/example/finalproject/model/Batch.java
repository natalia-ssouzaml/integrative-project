package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @NotNull
    @OneToOne
    @JoinColumn(name = "advertisementId")
    private Advertisement advertisement;

    @NotNull
    @Column(nullable = false)
    private Float currentTemperature;

    @NotNull
    @Column(nullable = false)
    private int productQuantity;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime manufacturingDateTime;

    @NotNull
    @Column(nullable = false)
    private Float volume;

    @NotNull
    @Column(nullable = false)
    private LocalDate dueDate;

    @NotNull
    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "order_number")
    @JsonIgnoreProperties("batchStock")
    @JsonIgnore
    private InboundOrder orderNumber;
}

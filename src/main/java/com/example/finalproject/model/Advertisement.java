package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long advertisementId;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal price;

    @OneToOne(mappedBy = "advertisement", cascade = CascadeType.PERSIST)
    private Batch batch;
}

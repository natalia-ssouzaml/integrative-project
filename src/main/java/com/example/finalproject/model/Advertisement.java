package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

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

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.PERSIST)
    private List<Batch> batches;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    @JsonIgnoreProperties("advertisements")
    private Seller seller;

    @ManyToMany(mappedBy = "advertisements")
    @JsonIgnoreProperties("advertisements")
    private List<PurchaseOrder> purchases;

}

package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long advertisementCode;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal price;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @JsonIgnore
    private List<Batch> batches;

    @ManyToOne
    @JoinColumn(name = "seller_code")
    @JsonIgnoreProperties("advertisements")
    private Seller seller;

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("advertisement")
    @ToString.Exclude
    private List<PurchaseItem> purchaseItems;

}

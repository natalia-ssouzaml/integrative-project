package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buyerCode;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("buyer")
    private List<PurchaseOrder> purchases;
}

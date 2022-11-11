package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseCode;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private boolean orderStatus;

    @NotNull
    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    @JsonIgnoreProperties("purchases")
    private Buyer buyer;

    @ManyToMany
    @JoinTable(
            name = "PurchaseOrder_Advertisement",
            joinColumns = @JoinColumn(name = "id_purchaseOrder", referencedColumnName = "purchaseCode"),
            inverseJoinColumns = @JoinColumn(name = "id_advertisement", referencedColumnName = "advertisementId")
    )
    @JsonIgnoreProperties("purchases")
    private List<Advertisement> advertisements;
}

package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InboundOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderNumber;

    @Column(nullable = false)
    private LocalDate orderDate;

    @ManyToOne
    @JoinColumn(name = "section_code")
    @JsonIgnoreProperties("orders")
    private Section section;

    @OneToMany(mappedBy = "orderNumber", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("orderNumber")
    private List<Batch> batchStock;


}

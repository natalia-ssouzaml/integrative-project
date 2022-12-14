package com.example.finalproject.model;

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
    private Long orderCode;

    @Column(nullable = false)
    private LocalDate orderDate;

    @ManyToOne
    @JoinColumn(name = "section_code")
    @JsonIgnoreProperties("orders")
    private Section section;

    @OneToMany(mappedBy = "inboundOrder", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("inboundOrder")
    private List<Batch> batchStock;


}

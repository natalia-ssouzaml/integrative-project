package com.example.finalproject.model;

import com.example.finalproject.model.Enum.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sectionCode;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private Float volume;

    @Column(nullable = false)
    private Float minTemperature;

    @Column(nullable = false)
    private Float maxTemperature;

    @Column(nullable = false)
    private Float accumulatedVolume;

    @ManyToOne
    @JoinColumn(name = "warehouse_code")
    @JsonIgnoreProperties("sections")
    private Warehouse warehouse;

    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("section")
    private List<InboundOrder> orders;

}

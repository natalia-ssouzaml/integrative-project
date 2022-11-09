package com.example.finalproject.model;

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
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseCode;

    @Column(nullable = false)
    private Float volume;

    @OneToOne
    @JoinColumn(name = "manager_code")
    private Manager manager;

    @OneToMany(mappedBy = "warehouse", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("warehouse")
    private List<Section> sections;

}

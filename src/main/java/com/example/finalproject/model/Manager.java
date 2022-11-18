package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long managerCode;

    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "manager", cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties("manager")
    private Warehouse warehouse;
}

package com.example.appealsservice.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "costCats")
@Data
public class CostCat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition="TEXT")
    private String name;
}

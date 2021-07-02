package com.example.employeesservice.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "role")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToMany
    @JoinTable(
            name = "role_right",
            joinColumns = @JoinColumn(name = "rights_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Right> rights;
}

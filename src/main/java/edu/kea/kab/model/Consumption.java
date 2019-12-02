package edu.kea.kab.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Consumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Categories category;

    private double hoursStreamed;

}

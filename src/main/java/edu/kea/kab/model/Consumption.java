package edu.kea.kab.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Consumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Double musicHours;

    Double videoHours;

    Double mobileHours;

    Double hoursStreamed;

    Byte week;

    Integer year;

    String session;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    User user;
}

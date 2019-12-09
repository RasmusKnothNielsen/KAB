package edu.kea.kab.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Consumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    double musicHours;

    double videoHours;

    double mobileHours;

    int week;

    int year;

    String session;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    User user;
}

package com.example.jeebackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Offre {

    @Id
    @GeneratedValue
    private Long id;
    private String universite;
    private String prix;
    private String logement;
    private String genre;
    private String localisation;
    private String contact;
    private boolean verified = false;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> images;
}

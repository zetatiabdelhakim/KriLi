package com.example.jeebackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Universite {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
}

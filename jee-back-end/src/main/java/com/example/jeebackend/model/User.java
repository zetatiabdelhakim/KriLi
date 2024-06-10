package com.example.jeebackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String login;
    private String password;
    private boolean isAdmin = false;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Offre> likes;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Offre> myOffres;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Offre> hands;
}

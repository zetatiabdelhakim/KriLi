package com.example.jeebackend.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Image {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] data;
}

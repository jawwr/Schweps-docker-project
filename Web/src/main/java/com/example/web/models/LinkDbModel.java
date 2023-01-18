package com.example.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="links")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LinkDbModel extends Link {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "status")
    private String status;

    public LinkDbModel(Link link){
        this.setUrl(link.getUrl());
    }
}

package org.example.xmlparser.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CastObce {

    @Id
    @Column(name = "kod_casti")
    private String kod;



    private String nazev;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kod_obce", referencedColumnName = "kod")
    private Obec kodObce;
}


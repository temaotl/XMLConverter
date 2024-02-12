package org.example.emlparser.data.entity;

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
public class Obec {
    @Id
    @Column(name = "kod")
    private String kod;

    private String nazev;
}

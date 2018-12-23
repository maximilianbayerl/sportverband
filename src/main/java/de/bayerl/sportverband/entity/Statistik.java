package de.bayerl.sportverband.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
public class Statistik extends BasisEntity implements Serializable {

    @Getter
    @Setter
    private Integer ungeschlagenSeitAnzahlSpiele;

    @Getter
    @Setter
    private Integer siegesSerie;

    @Getter
    @Setter
    private Integer gewonneneSaisons;

    @Getter
    @Setter
    private Integer bestePlatzierungEndeSaison;

    @Getter
    @Setter
    private Integer besteTordifferenz;

    @Getter
    @Setter
    private Double toreProSpiel;

    @Getter
    @Setter
    private Double punkteProSpiel;

    public Statistik(){
        this.ungeschlagenSeitAnzahlSpiele = 0;
        this.siegesSerie = 0;
        this.gewonneneSaisons = 0;
        this.bestePlatzierungEndeSaison = 0;
        this.besteTordifferenz = 0;
        this.toreProSpiel = 0.0;
        this.punkteProSpiel = 0.0;
    }
}

package de.bayerl.sportverband.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@Entity
@XmlTransient
public class Tabellenposition extends BasisEntity implements Serializable {

    @Getter
    @Setter
    private Integer platz;

    @Getter
    @Setter
    private Integer anzahlSiege;

    @Getter
    @Setter
    private Integer anzahlUnentschieden;

    @Getter
    @Setter
    private Integer anzahlNiederlage;

    @Getter
    @Setter
    private Integer anzahlPunkte;

    @Getter
    @Setter
    private Integer anzahlTore;

    @Getter
    @Setter
    private Integer anzahlGegentore;

    @Getter
    @Setter
    private Integer anzahlTorDifferenz;

    @Getter
    @Setter
    private Integer anzahlAbsolvierteSpiele;

    @Getter
    @Setter
    @OneToOne(mappedBy = "tabellenPosition")
    private Mannschaft mannschaft;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Tabelle tabelle;

    public Tabellenposition(){

    }

    public Tabellenposition(Integer anzahlSiege, Integer anzahlNiederlage,
                            Integer anzahlUnentschieden, Integer anzahlPunkte, Integer anzahlTore,
                            Integer  anzahlGegentore, Integer anzahlTorDifferenz, Integer  anzahlAbsolvierteSpiele){
        this.anzahlSiege = anzahlSiege;
        this.anzahlUnentschieden = anzahlUnentschieden;
        this.anzahlNiederlage = anzahlNiederlage;
        this.anzahlPunkte = anzahlPunkte;
        this.anzahlTore = anzahlTore;
        this.anzahlGegentore = anzahlGegentore;
        this.anzahlTorDifferenz = anzahlTorDifferenz;
        this.anzahlAbsolvierteSpiele = anzahlAbsolvierteSpiele;
    }
    public String getMannschaftsName(Mannschaft m){
        return m.getMannschaftsName();
    }

    public Tabellenposition aktualisiereWerte(Integer anzahlSiege, Integer anzahlNiederlage,
                                              Integer anzahlUnentschieden, Integer anzahlPunkte, Integer anzahlTore,
                                              Integer  anzahlGegentore, Integer anzahlTorDifferenz,
                                              Integer  anzahlAbsolvierteSpiele){
        this.anzahlSiege = anzahlSiege;
        this.anzahlUnentschieden = anzahlUnentschieden;
        this.anzahlNiederlage = anzahlNiederlage;
        this.anzahlPunkte = anzahlPunkte;
        this.anzahlTore = anzahlTore;
        this.anzahlGegentore = anzahlGegentore;
        this.anzahlTorDifferenz = anzahlTorDifferenz;
        this.anzahlAbsolvierteSpiele = anzahlAbsolvierteSpiele;
        return this;
    }
}

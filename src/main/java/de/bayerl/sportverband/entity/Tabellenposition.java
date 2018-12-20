package de.bayerl.sportverband.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
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

    public Tabellenposition(Tabelle t){
        List <Tabellenposition> m = t.getTabellenPositionen();
        m.add(this);
        t.setTabellenPositionen(m);
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
        System.out.println(anzahlSiege);
        System.out.println("SIEGERRR");
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
        System.out.println(anzahlSiege);
        System.out.println("SIEGE");

        return this;
    }
}

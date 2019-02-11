package de.bayerl.sportverband.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Getter;
import lombok.Setter;


@Entity
@XmlTransient
public class Mannschaft extends BasisEntity implements Serializable {

    @Getter
    @Setter
    private String mannschaftsName;

    @Getter
    @Setter
    private Integer anzahlMitgliederFanClub;

    @OneToOne(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Statistik statistik;

    @OneToOne(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Tabellenposition tabellenPosition;

    @OneToMany(mappedBy = "mannschaftGast", cascade = CascadeType.REMOVE )
    @Getter
    @Setter
    private List<Spiel> spielGast;

    @OneToMany(mappedBy = "mannschaftHeim", cascade = CascadeType.REMOVE )
    @Getter
    @Setter
    private  List<Spiel> spielHeim;

    public Mannschaft () {

    }

    public Mannschaft(String mannschaftsName, Integer anzahlMitgliederFanClub) {
        this.mannschaftsName = mannschaftsName;
        this.anzahlMitgliederFanClub = anzahlMitgliederFanClub;
        this.tabellenPosition = new Tabellenposition();
    }


    public void createStatistik(){
        this.statistik = new Statistik();
    }

    public void createTabellenPosition(){
        this.tabellenPosition = new Tabellenposition(0,0,0,0,0,0,0,0);
    }

}

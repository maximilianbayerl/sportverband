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
    @XmlTransient
    private Statistik statistik;

    @OneToOne(cascade = CascadeType.ALL)
    @Getter
    @Setter
    @XmlTransient
    private Tabellenposition tabellenPosition;

    @OneToMany(mappedBy = "mannschaftGast", cascade = CascadeType.REMOVE )
    @Getter
    @Setter
    @XmlTransient
    private List<Spiel> spielGast;

    @OneToMany(mappedBy = "mannschaftHeim", cascade = CascadeType.REMOVE )
    @Getter
    @Setter
    @XmlTransient
    private  List<Spiel> spielHeim;

    public Mannschaft () {

    }

    public Mannschaft(String mannschaftsName, Integer anzahlMitgliederFanClub) {
        this.mannschaftsName = mannschaftsName;
        this.anzahlMitgliederFanClub = anzahlMitgliederFanClub;
        this.tabellenPosition = new Tabellenposition();
    }

    public Long getMannschaftsId(){ return this.getId();}

    public Statistik createStatistik(){
        this.statistik = new Statistik();
        return this.statistik;
    }

    public Tabellenposition createTabellenPosition(){
        this.tabellenPosition = new Tabellenposition(0,0,0,0,0,0,0,0);
        return this.tabellenPosition;}

}

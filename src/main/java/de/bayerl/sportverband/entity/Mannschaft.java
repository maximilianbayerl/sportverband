package de.bayerl.sportverband.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Mannschaft extends BasisEntity implements Serializable {

    @Column(nullable = false)
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

package de.bayerl.sportverband.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;


@Entity
public class Spiel extends BasisEntity implements Serializable {

    @Getter
    @Setter
    @ManyToOne()
    private Mannschaft mannschaftHeim;

    @Getter
    @Setter
    private Integer trefferHeimErsteHalbzeit;

    @Getter
    @Setter
    private Integer trefferHeimEnde;

    @Getter
    @Setter
    @ManyToOne()
    private Mannschaft mannschaftGast;

    @Getter
    @Setter
    private Integer trefferGastErsteHalbzeit;

    @Getter
    @Setter
    private Integer trefferGastEnde;

    @Getter
    @Setter
    private Date datum;

    @Getter
    @Setter
    private String ligaName;

    @Getter
    @Setter
    private Boolean absolviert;

    @Getter
    @Setter
    private Long stadionId;

    public Spiel () {

    }

    public Spiel(Mannschaft mannschaftHeim, Mannschaft mannschaftGast,String ligaName){
        this.mannschaftGast = mannschaftGast;
        this.mannschaftHeim = mannschaftHeim;
        this.ligaName = ligaName;
        this.absolviert = false;
    }

    public void punkteEintragen(Integer trefferHeimErsteHalbzeit,
    Integer trefferHeimEnde, Integer trefferGastErsteHalbzeit,
                 Integer trefferGastEnde, Boolean absolviert){
        this.trefferHeimErsteHalbzeit = trefferHeimErsteHalbzeit;
        this.trefferHeimEnde = trefferHeimEnde;
        this.trefferGastErsteHalbzeit = trefferGastErsteHalbzeit;
        this.trefferGastEnde = trefferGastEnde;
        this.absolviert = absolviert;
    }

    public void bucheStadionFake(Long stadionId, Date datum){
        this.stadionId = stadionId;
        this.datum = datum;
    }

    public String getMannschaftHeimName(){
        return mannschaftHeim.getMannschaftsName();
    }

    public String getMannschaftGastName(){
        return mannschaftGast.getMannschaftsName();
    }
}

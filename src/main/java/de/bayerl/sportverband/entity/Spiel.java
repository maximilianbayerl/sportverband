package de.bayerl.sportverband.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Date;


@Entity
public class Spiel extends BasisEntity implements Serializable {

    @Getter
    @Setter
    @OneToOne()
    private Mannschaft mannschaftHeim;

    @Getter
    @Setter
    private Integer trefferHeimErsteHalbzeit;

    @Getter
    @Setter
    private Integer trefferHeimEnde;

    @Getter
    @Setter
    @OneToOne()
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
    private String schiedsrichterName;

    @Getter
    @Setter
    private Boolean absolviert;


    public Spiel () {

    }

    public Spiel(Mannschaft mannschaftHeim, Mannschaft mannschaftGast, Date datum, String schiedsrichterName){
        this.mannschaftGast = mannschaftGast;
        this.mannschaftHeim = mannschaftHeim;
        this.datum = datum;
        this.schiedsrichterName = schiedsrichterName;
        this.absolviert = false;
    }

    public Boolean punkteEintragen(Integer trefferHeimErsteHalbzeit,
    Integer trefferHeimEnde, Integer trefferGastErsteHalbzeit,
                 Integer trefferGastEnde, Boolean absolviert){
        this.trefferHeimErsteHalbzeit = trefferHeimErsteHalbzeit;
        this.trefferHeimEnde = trefferHeimEnde;
        this.trefferGastErsteHalbzeit = trefferGastErsteHalbzeit;
        this.trefferGastEnde = trefferGastEnde;
        this.absolviert = absolviert;
        return true;
    }

    public String getMannschaftHeimName(){
        return mannschaftHeim.getMannschaftsName();
    }

    public String getMannschaftGastName(){
        return mannschaftGast.getMannschaftsName();
    }

    public Integer getTrefferHeimEnde(){return trefferHeimEnde;}

    public Integer getTrefferGastEnde(){return trefferGastEnde;}


}

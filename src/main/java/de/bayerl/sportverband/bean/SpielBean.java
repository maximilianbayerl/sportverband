package de.bayerl.sportverband.bean;


import de.bayerl.sportverband.entity.Mannschaft;
import de.bayerl.sportverband.entity.Spiel;
import de.bayerl.sportverband.service.SpielplanService;
import de.bayerl.sportverband.service.TabellenPositionService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class SpielBean implements Serializable {
    @Inject
    SpielplanService spServ;

    @Inject
    TabellenPositionService tabPosServ;


    @Getter
    @Setter
    private Mannschaft mannschaftHeim;

    @Getter
    private Long id;

    @Getter
    @Setter
    private Integer trefferHeimErsteHalbzeit;

    @Getter
    @Setter
    private Integer trefferHeimEnde;

    @Getter
    @Setter
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
    private Spiel spiel;

    @Getter
    @Setter
    private List<Spiel> spiele;

    public List<Spiel> createHR(){
        return spServ.createHR(this.datum, this.schiedsrichterName);
    }

    public List<Spiel> createHRRR(){
        return spServ.createHRRR(this.datum, this.schiedsrichterName);
    }

    public List<Spiel> getSpiele(){
        return  spServ.getSpiele();
    }

    public String getMannschaftHeimName(){
        return this.getMannschaftHeim().getMannschaftsName();
    }

    public String getMannschaftGastmName(){
        return this.getMannschaftGast().getMannschaftsName();
    }

    public Spiel tragePunkteEin (Spiel m){
        Spiel s = spServ.tragePunkteEin(m.getId(), this.trefferHeimErsteHalbzeit,
                this.trefferHeimEnde,
                this.trefferGastErsteHalbzeit, this.trefferGastEnde, true);
        this.trefferGastEnde = null;
        this.trefferGastErsteHalbzeit = null;
        this.trefferHeimEnde = null;
        this.trefferHeimErsteHalbzeit = null;

        this.mannschaftHeim = s.getMannschaftHeim();
        this.mannschaftGast = s.getMannschaftGast();
        System.out.println("ALARM");
        System.out.println(s);
        System.out.println(this.mannschaftHeim);
        System.out.println(this.mannschaftGast);
        tabPosServ.trageDatenInTabellenpositionEin(this.mannschaftHeim);
        tabPosServ.trageDatenInTabellenpositionEin(this.mannschaftGast);
        return s;
    }

    public void init(){
        this.spiele = getSpiele();
    }
}

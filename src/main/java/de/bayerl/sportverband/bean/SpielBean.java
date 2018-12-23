package de.bayerl.sportverband.bean;


import de.bayerl.sportverband.entity.Mannschaft;
import de.bayerl.sportverband.entity.Spiel;
import de.bayerl.sportverband.entity.Tabelle;
import de.bayerl.sportverband.service.SpielplanService;
import de.bayerl.sportverband.service.TabellenPositionService;
import de.bayerl.sportverband.service.TabellenService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Named
@SessionScoped
public class SpielBean implements Serializable {
    @Inject
    SpielplanService spServ;

    @Inject
    TabellenPositionService tabPosServ;

    @Inject
    TabellenService tabServ;

    @Getter
    @Setter
    private Mannschaft mannschaftHeim;

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
    private String ligaName;

    @Getter
    @Setter
    private List <String> ligen;

    @Getter
    @Setter
    private String ligaNameAnzeigen;

    @Getter
    @Setter
    private List <String> ligenAnzeigen;

    @Getter
    @Setter
    private List<Spiel> spiele;

    public List<Spiel> createHR(){
        this.ligaNameAnzeigen = this.ligaName;
        return spServ.createHR(this.ligaName);
    }

    public List<Spiel> createHRRR(){
        this.ligaNameAnzeigen = this.ligaName;
        return spServ.createHRRR(this.ligaName);
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

    public List <Spiel> spieleAnzeigen(){
        this.spiele = null;
        this.spiele = spServ.getSpieleByLigaName(this.ligaNameAnzeigen);
        System.out.println(this.ligaNameAnzeigen);
        return this.spiele;
    }

    public Spiel bucheStadion(Spiel m){
        // todo: greife auf chris bla bla zu buche usw now fake:
        // schicke datum und anzahl fans beider mannschaften bspw.
        // returns true if ok...
        Random r = new Random();
        Long stadionId = 100L + r.nextLong();
        Spiel s = spServ.trageStadionEin(m.getId(), this.datum, stadionId);

        return s;
    }

    public void init(){
        this.ligen = new ArrayList<>();
        this.ligenAnzeigen = new ArrayList<>();
        List <Tabelle> ligas = tabServ.getAlle();
        for(int i = 0; i< ligas.size(); i++){
            this.ligen.add(ligas.get(i).getLigaName());
            this.ligenAnzeigen.add(ligas.get(i).getLigaName());
        }
    }
}

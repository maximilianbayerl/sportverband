package de.bayerl.sportverband.bean;


import de.bayerl.sportverband.converter.TabellenConverter;
import de.bayerl.sportverband.entity.*;
import de.bayerl.sportverband.service.MannschaftsService;
import de.bayerl.sportverband.service.SpielplanService;
import de.bayerl.sportverband.service.TabellenService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class MannschaftsBean implements Serializable {
    @Inject
    MannschaftsService manServ;

    @Inject
    TabellenService tabServ;

    @Inject
    SpielplanService spServ;

    @Inject
    @Getter
    @Setter
    TabellenConverter tabConv;

    @Getter
    @Setter
    private String mannschaftsName;

    @Getter
    @Setter
    private Integer anzahlMitgliederFanClub;

    @Getter
    @Setter
    private Statistik ownStatistik;

    @Getter
    @Setter
    private Tabellenposition ownTabellenPosition;

    @Getter
    @Setter
    private Mannschaft mannschaft;

    @Getter
    @Setter
    private List <Tabelle> ligen;

    @Getter
    @Setter
    private List<Mannschaft> mannschaften;

    @Getter
    @Setter
    private Tabelle selectedLiga;

    @Getter
    @Setter
    private Date datum;

    @Getter
    @Setter
    private Long mannschaftStat;

    public List<Mannschaft> getMannschaften(){
        return manServ.getMannschaften();
    }

    public Mannschaft createMannschaft(){
        if(this.ligen.size()>0) {
            Mannschaft m = manServ.create(this.mannschaftsName, this.anzahlMitgliederFanClub);
            this.ownTabellenPosition = m.getTabellenPosition();
            List <Spiel> spiele = spServ.getSpieleByLigaName(this.selectedLiga.getLigaName());
            if(spiele.size() == 0) {
                if(this.anzahlMitgliederFanClub>=100 && this.anzahlMitgliederFanClub <= 1000000){
                    tabServ.addTabellenpositionenToTabelle(this.ownTabellenPosition, this.selectedLiga.getLigaName());
                    return m;
                }else {
                    FacesContext.getCurrentInstance().addMessage("mannschaftForm:createMannschaft", new FacesMessage(
                            "Bitte geben sie einen Wert im Feld Anzahl der Fans zwischen 100 und 1.000.000 ein."));
                    manServ.deleteMannschaft(m);
                    return null;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("mannschaftForm:createMannschaft", new FacesMessage(
                        "Dieser Liga können keine weiteren Mannschaften hinzugefügt werden, da bereits ein Spielplan " +
                                "erstellt wurde."));
                manServ.deleteMannschaft(m);
                return null;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("mannschaftForm:createMannschaft", new FacesMessage(
                    "Bitte zuerste eine Liga erstellen in der die Mannschaft angelegt werden soll."));
            return null;
        }
    }
    public void init(){
        this.mannschaftStat = null;
        this.mannschaften = getMannschaften();
        this.ligen = tabServ.getAlle();
    }

    public String zeigeStatistik(Long m){
        this.mannschaftStat = m;
        return "statistik.xhtml?faces-redirect=true";
    }
}

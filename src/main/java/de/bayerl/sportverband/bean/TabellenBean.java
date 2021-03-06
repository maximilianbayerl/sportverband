package de.bayerl.sportverband.bean;

import de.bayerl.sportverband.converter.TabellenConverter;
import de.bayerl.sportverband.entity.Tabelle;
import de.bayerl.sportverband.entity.Tabellenposition;
import de.bayerl.sportverband.service.Sortbyroll;
import de.bayerl.sportverband.service.TabellenPositionService;
import de.bayerl.sportverband.service.TabellenService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
public class TabellenBean implements Serializable {
    @Inject
    TabellenService tabServ;

    @Inject
    TabellenPositionService tabPosServ;

    @Inject
    @Getter
    @Setter
    TabellenConverter tabConv;

    @Getter
    @Setter
    private String ligaName;

    @Getter
    @Setter
    private List <Tabelle> ligen;

    @Getter
    @Setter
    private Tabelle selectedLiga;

    @Getter
    @Setter
    private Tabelle tabelle;

    @Getter
    @Setter
    private List<Tabellenposition> tabellenpositionen;

    public Tabelle createTabelle(){
        if(this.ligaName != null) {
            List <Tabelle> tabellen = tabServ.getAlle();
            for (Tabelle aTabellen : tabellen) {
                if (aTabellen.getLigaName().equals(this.ligaName)) {
                    FacesContext.getCurrentInstance().addMessage("tabellenForm", new FacesMessage(
                            "Es gibt bereits eine Tabelle mit diesem Namen, bitte Eingabe verändern."));
                    return null;
                }
            }
            this.selectedLiga = tabServ.createTabelle(this.ligaName);
            this.ligen = tabServ.getAlle();
            FacesContext.getCurrentInstance().addMessage("tabellenForm", new FacesMessage(
                    "Neue Tabelle wurde erfolgreich erstellt."));
            return this.selectedLiga;
        } else {
            FacesContext.getCurrentInstance().addMessage("tabellenForm", new FacesMessage(
                    "Es wird mindestens ein Zeichen als Tabellenname benötigt."));
            return null;
        }
    }

    private List<Tabellenposition> getAllTabellenpositionen(Tabelle t){
        return tabServ.getAllTabellenpositionen(t);
    }

    private Tabelle findTabelleByLigaName(String ligaName){
        return tabServ.findTabelleByName(ligaName);
    }

    private ArrayList<Tabellenposition> sortTabelle(List<Tabellenposition> tabelleUnsortiert){
        if(tabelleUnsortiert != null) {
            ArrayList<Tabellenposition> tabelleSortiert = new ArrayList<>(tabelleUnsortiert);
            tabelleSortiert.sort(new Sortbyroll());
            for (int m = 0; m < tabelleSortiert.size(); m++) {
                tabelleSortiert.get(m).setPlatz(m + 1);
            }
            return tabelleSortiert;
        } else {
            return null;
        }
    }

    public void anzeigenByLigaName(){
        if(this.ligen.size()>0) {
            Tabelle t = findTabelleByLigaName(this.selectedLiga.getLigaName());
            if (t != null) {
                List <Tabellenposition>test = tabServ.getAllTabellenpositionen(t);
                if(test != null) {
                    for (Tabellenposition aTest : test) {
                        tabPosServ.trageDatenInTabellenpositionEin(aTest.getMannschaft());
                    }
                }
                this.tabellenpositionen = sortTabelle(getAllTabellenpositionen(t));
            }
        }
    }

    public void init() {
        this.ligen = tabServ.getAlle();
        if (this.selectedLiga != null) {
            Tabelle t = findTabelleByLigaName(this.selectedLiga.getLigaName());
            if (this.selectedLiga != null) {
                this.tabellenpositionen = sortTabelle(getAllTabellenpositionen(t));
            }
        }
    }

}

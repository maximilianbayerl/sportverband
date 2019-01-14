package de.bayerl.sportverband.bean;

import de.bayerl.sportverband.converter.TabellenConverter;
import de.bayerl.sportverband.entity.Mannschaft;
import de.bayerl.sportverband.entity.Spiel;
import de.bayerl.sportverband.entity.Tabelle;
import de.bayerl.sportverband.service.SpielplanService;
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
public class SpielBean implements Serializable {
    @Inject
    SpielplanService spServ;

    @Inject
    private AbstrakteBean stadionBean;

    @Inject
    TabellenPositionService tabPosServ;

    @Inject
    TabellenService tabServ;

    @Inject
    @Getter
    @Setter
    TabellenConverter tabConv;

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
    private Long stadionId;

    @Getter
    @Setter
    private List <Tabelle> ligen;

    @Getter
    @Setter
    private Tabelle selectedLigaAnzeigen;

    @Getter
    @Setter
    private Tabelle selectedLiga;

    @Getter
    @Setter
    private List <Tabelle> ligenAnzeigen;

    @Getter
    @Setter
    private List<Spiel> spiele;

    public List<Spiel> hrAnlegen(){
        if(ligen.size()> 0) {
            if (spServ.getSpieleByLigaName(this.selectedLiga.getLigaName()).size()== 0){
             this.selectedLigaAnzeigen = this.selectedLiga;
             try {
                 List <Spiel> spielplan = spServ.hrAnlegen(this.selectedLiga.getLigaName());
                 if(spielplan.size() > 0){
                     FacesContext.getCurrentInstance().addMessage("spielPlanForm:hrAnlegen", new FacesMessage(
                             "Hinrunde erfolgreich erstellt."));
                     return spielplan;
                 } else {
                     FacesContext.getCurrentInstance().addMessage("spielPlanForm:hrAnlegen", new FacesMessage(
                             "Um eine Hinrunde zu erstellen sind mindestens zwei Mannschaften nötig, " +
                                     "stellen Sie " +
                                     "sicher, dass bereits mindestens zwei Mannschaften erstellt wurden."));
                 }
             } catch (Exception e){
                 FacesContext.getCurrentInstance().addMessage("spielPlanForm:hrAnlegen", new FacesMessage(
                         "Es konnten keine Spiele erstellt werden, stellen Sie sicher, dass bereits Mannschaften " +
                                 "erstellt wurden."));
                 return null;
             }
           } else {
                FacesContext.getCurrentInstance().addMessage("spielPlanForm:hrAnlegen", new FacesMessage(
                        "Es wurden bereits Spiele für diese Liga erstellt."));
            }
            return null;
        } else {
            FacesContext.getCurrentInstance().addMessage("spielPlanForm:hrAnlegen", new FacesMessage(
                    "Bitte zuerste eine Liga erstellen."));
            return null;
        }
    }

    public List<Spiel> hrRrAnlegen(){
        if (ligen.size() > 0) {
        if (spServ.getSpieleByLigaName(this.selectedLiga.getLigaName()).size()== 0) {

                this.selectedLigaAnzeigen = this.selectedLiga;
                try {
                    List <Spiel> spielplan = spServ.hrRrAnlegen(this.selectedLiga.getLigaName());
                    if(spielplan.size()> 0){
                        FacesContext.getCurrentInstance().addMessage("spielPlanForm:hrAnlegen", new FacesMessage(
                                "Hin- und Rückrunde erfolgreich erstellt."));
                        return spielplan;
                    } else {
                        FacesContext.getCurrentInstance().addMessage("spielPlanForm:hrRrAnlegen", new FacesMessage(
                                "Um eine Hin- und Rückrunde zu erstellen sind mindestens zwei Mannschaften nötig, " +
                                        "stellen Sie " +
                                        "sicher, dass bereits mindestens zwei Mannschaften erstellt wurden."));
                        return null;
                    }
                } catch(Exception e){
                    FacesContext.getCurrentInstance().addMessage("spielPlanForm:hrRrAnlegen", new FacesMessage(
                            "Es konnten keine Spiele erstellt werden, stellen Sie sicher, dass bereits Mannschaften " +
                                    "erstellt wurden."));
                    return null;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("spielPlanForm:hrRrAnlegen", new FacesMessage(
                        "Es wurden bereits Spiele für diese Liga erstellt."));
                return null;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("spielPlanForm:hrRrAnlegen", new FacesMessage(
                    "Bitte zuerste eine Liga erstellen."));
            return null;
        }
    }

    public Spiel tragePunkteEin (Spiel m){
        if(m.getStadionName()!= null) {
            if(this.trefferHeimErsteHalbzeit!=null && this.trefferGastErsteHalbzeit!=null && this.trefferHeimEnde!=null && this.trefferGastEnde!=null){
                if (this.trefferHeimEnde >= this.trefferHeimErsteHalbzeit && this.trefferGastEnde >= this.trefferGastErsteHalbzeit) {
                    Spiel s = spServ.tragePunkteEin(m.getId(), this.trefferHeimErsteHalbzeit,
                            this.trefferHeimEnde,
                            this.trefferGastErsteHalbzeit, this.trefferGastEnde, true);
                    this.mannschaftHeim = s.getMannschaftHeim();
                    this.mannschaftGast = s.getMannschaftGast();
                    tabPosServ.trageDatenInTabellenpositionEin(this.mannschaftHeim);
                    tabPosServ.trageDatenInTabellenpositionEin(this.mannschaftGast);
                    this.spiele = spServ.getSpieleByLigaName(this.selectedLigaAnzeigen.getLigaName());
                    this.trefferGastEnde = null;
                    this.trefferGastErsteHalbzeit = null;
                    this.trefferHeimEnde = null;
                    this.trefferHeimErsteHalbzeit = null;
                    return s;
                } else {
                    FacesContext.getCurrentInstance().addMessage("tragePunkteEinForm", new FacesMessage(
                            "Der Endstand muss mindestens dem Halbzeitstand entsprechen."));
                    this.trefferGastEnde = null;
                    this.trefferGastErsteHalbzeit = null;
                    this.trefferHeimEnde = null;
                    this.trefferHeimErsteHalbzeit = null;
                    return null;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("tragePunkteEinForm", new FacesMessage(
                        "Bitte vollständiges Ergebniss inklusive Halbzeitstand eintragen."));
                return null;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("tragePunkteEinForm", new FacesMessage(
                    "Es muss zuerst ein Stadion für dieses Spiel gebucht werden."));
            return null;
        }
    }

    public List <Spiel> spieleAnzeigen(){
        if(this.selectedLigaAnzeigen !=null) {
            this.spiele = spServ.getSpieleByLigaName(this.selectedLigaAnzeigen.getLigaName());
            if (this.spiele != null) {
                return this.spiele;
            } else {
                return this.spiele;
            }
        } else {
            return null;
        }
    }


    public void bucheStadion(Spiel m){
      stadionBean.bucheStadion(m, this.datum, this.selectedLigaAnzeigen);
      this.datum = null;
        this.spiele = spServ.getSpieleByLigaName(this.selectedLigaAnzeigen.getLigaName());
    }

    public void init(){
        this.ligen = tabServ.getAlle();
        this.ligenAnzeigen = tabServ.getAlle();
    }
}

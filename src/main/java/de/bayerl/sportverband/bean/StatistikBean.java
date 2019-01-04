package de.bayerl.sportverband.bean;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

import de.bayerl.sportverband.entity.Mannschaft;
import de.bayerl.sportverband.entity.Statistik;
import de.bayerl.sportverband.service.*;
import lombok.Getter;
import lombok.Setter;


@Named
@RequestScoped
public class StatistikBean implements Serializable {

    @Inject
    MannschaftsBean manBean;

    @Inject
    StatistikService statServ;

    @Inject
    MannschaftsService manServ;

    @Getter
    @Setter
    private Integer ungeschlagenSeitAnzahlSpiele;

    @Getter
    @Setter
    private Integer siegesSerie;


    @Getter
    @Setter
    private Integer besteTordifferenz;

    @Getter
    @Setter
    private Double toreProSpiel;

    @Getter
    @Setter
    private String toreProSpielRounded;

    @Getter
    @Setter
    private Double punkteProSpiel;

    @Getter
    @Setter
    private String punkteProSpielRounded;

    @Getter
    @Setter
    private Statistik statistik;

    @Getter
    @Setter
    private Long mannschafter;

    @Getter
    @Setter
    private Mannschaft mannschaftNeu;

    @Getter
    @Setter
    private String mannschaftsName;

    @Getter
    @Setter
    private int anzahlFans;



    public void init(){
        this.mannschafter = manBean.getMannschaftStat();
        this.mannschaftNeu = manServ.getMannschaftById(this.mannschafter);
        this.statistik = statServ. getStatistikByMannschaft(this.mannschaftNeu);
        this.mannschaftsName = this.mannschaftNeu.getMannschaftsName();
        this.anzahlFans = this.mannschaftNeu.getAnzahlMitgliederFanClub();
        if(this.statistik.getToreProSpiel() != null){
            this.toreProSpielRounded = String.format("%.2f", this.statistik.getToreProSpiel());
        }
        if(this.statistik.getPunkteProSpiel() != null){
            this.punkteProSpielRounded = String.format("%.2f", this.statistik.getPunkteProSpiel());
        }
    }

    public String deleteMannschaft(){
        this.mannschafter = manBean.getMannschaftStat();
        this.mannschaftNeu = manServ.getMannschaftById(this.mannschafter);
        if(this.mannschaftNeu!= null){
            manServ.deleteMannschaft(this.mannschaftNeu);
            return "mannschaften.xhtml?faces-redirect=true";
        } else {
            return null;
        }
    }

    public void changeMannschaft(){
        this.mannschafter = manBean.getMannschaftStat();
        this.mannschaftNeu = manServ.getMannschaftById(this.mannschafter);
        if(this.anzahlFans >= 100 && this.anzahlFans <= 1000000 && this.mannschaftsName != null){
            this.mannschaftNeu.setAnzahlMitgliederFanClub(this.anzahlFans);
            this.mannschaftNeu.setMannschaftsName(this.mannschaftsName);
            manServ.changeMannschaft(this.mannschaftNeu);
            this.statistik = statServ. getStatistikByMannschaft(this.mannschaftNeu);
            if(this.statistik.getToreProSpiel() != null){
                this.toreProSpielRounded = String.format("%.2f", this.statistik.getToreProSpiel());
            }
            if(this.statistik.getPunkteProSpiel() != null){
                this.punkteProSpielRounded = String.format("%.2f", this.statistik.getPunkteProSpiel());
            }
            FacesContext.getCurrentInstance().addMessage("statistikForm", new FacesMessage(
                    "Mannschaft erfolgreich geändert."));
        }else {
            FacesContext.getCurrentInstance().addMessage("statistikForm", new FacesMessage(
                "Es wird ein Mannschaftsname und die Anzahl der Fans zwischen 100 und 1.000.000 benötigt."));
    }
    }

}

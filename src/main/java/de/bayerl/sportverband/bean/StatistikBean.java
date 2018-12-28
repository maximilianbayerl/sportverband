package de.bayerl.sportverband.bean;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

import de.bayerl.sportverband.entity.Mannschaft;
import de.bayerl.sportverband.entity.Statistik;
import de.bayerl.sportverband.service.*;
import lombok.Getter;
import lombok.Setter;
import java.util.*;


@Named
@RequestScoped
@ManagedBean
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
    private String mannschafter;

    @Getter
    @Setter
    private Mannschaft mannschaftNeu;



    public void init(){
        this.mannschafter = manBean.getMannschaftStat();
        this.mannschaftNeu = manServ.getMannschaftByMannschaftsName(this.mannschafter);
        this.statistik = statServ. getStatistikByMannschaft(this.mannschaftNeu);
        if(this.statistik.getToreProSpiel() != null){
            this.toreProSpielRounded = String.format("%.2f", this.statistik.getToreProSpiel());
        }
        if(this.statistik.getPunkteProSpiel() != null){
            this.punkteProSpielRounded = String.format("%.2f", this.statistik.getPunkteProSpiel());
        }
    }

}

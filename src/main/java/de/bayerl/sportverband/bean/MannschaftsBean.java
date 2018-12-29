package de.bayerl.sportverband.bean;


import de.bayerl.sportverband.entity.*;
import de.bayerl.sportverband.service.MannschaftsService;
import de.bayerl.sportverband.service.SpielplanService;
import de.bayerl.sportverband.service.TabellenService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@ApplicationScoped
public class MannschaftsBean implements Serializable {
    @Inject
    MannschaftsService manServ;

    @Inject
    TabellenService tabServ;

    @Inject
    SpielplanService spServ;

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
    private String ligaName;

    @Getter
    @Setter
    private List <String> ligen;

    @Getter
    @Setter
    private List<Mannschaft> mannschaften;

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
            List <Spiel> spiele = spServ.getSpieleByLigaName(this.ligaName);
            if(spiele.size() == 0) {
                tabServ.addTabellenpositionenToTabelle(this.ownTabellenPosition, this.ligaName);
                return m;
            } else {
                manServ.deleteMannschaft(m);
                return null;
            }
        } else {
            return null;
        }
    }
    public void init(){
        this.mannschaftStat = null;
        this.mannschaften = getMannschaften();
        this.ligen = new ArrayList<>();
        List <Tabelle> ligas = tabServ.getAlle();
        for(int i = 0; i< ligas.size(); i++){
            this.ligen.add(ligas.get(i).getLigaName());
        }

    }

    public String zeigeStatistik(Long m){
        this.mannschaftStat = m;
        return "statistik.xhtml?faces-redirect=true";
    }
}

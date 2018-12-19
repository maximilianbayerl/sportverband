package de.bayerl.sportverband.bean;


import de.bayerl.sportverband.entity.Mannschaft;
import de.bayerl.sportverband.entity.Statistik;
import de.bayerl.sportverband.entity.Tabelle;
import de.bayerl.sportverband.entity.Tabellenposition;
import de.bayerl.sportverband.service.MannschaftsService;
import de.bayerl.sportverband.service.TabellenService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class MannschaftsBean implements Serializable {
    @Inject
    MannschaftsService manServ;

    @Inject
    TabellenService tabServ;

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
    private List<Mannschaft> mannschaften;

    public List<Mannschaft> getMannschaften(){
        return manServ.getMannschaften();
    }

    public Mannschaft createMannschaft(){
        Mannschaft m = manServ.create(this.mannschaftsName, this.anzahlMitgliederFanClub);
        this.ownTabellenPosition = m.getTabellenPosition();
        tabServ.addTabellenpositionenToTabelle(this.ownTabellenPosition, this.ligaName);
        return m;
    }
    public void init(){
        this.mannschaften = getMannschaften();

    }
}

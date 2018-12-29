package de.bayerl.sportverband.bean;

import de.bayerl.sportverband.entity.Tabelle;
import de.bayerl.sportverband.entity.Tabellenposition;
import de.bayerl.sportverband.service.Sortbyroll;
import de.bayerl.sportverband.service.TabellenPositionService;
import de.bayerl.sportverband.service.TabellenService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@ApplicationScoped
public class TabellenBean implements Serializable {
    @Inject
    TabellenService tabServ;

    @Inject
    TabellenPositionService tabPosServ;

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
    private Date saison;

    @Getter
    @Setter
    private Tabelle tabelle;

    @Getter
    @Setter
    private List<Tabellenposition> tabellenpositionen;

    public Tabelle createTabelle(){
        if(this.ligaName != null && this.saison != null) {
            this.ligaNameAnzeigen = this.ligaName;
            Tabelle t = tabServ.createTabelle(this.ligaName, this.saison);
            List<String> ligen = new ArrayList<>();
            List<Tabelle> ligas = tabServ.getAlle();
            for (int i = 0; i < ligas.size(); i++) {
                ligen.add(ligas.get(i).getLigaName());
            }
            this.ligen = ligen;
            return t;
        } else {
            return null;
        }
    }

    public List<Tabellenposition> getAllTabellenpositionen(Tabelle t){
        return tabServ.getAllTabellenpositionen(t);
    }

    public Tabelle findTabelleByLigaName(String ligaName){
        return tabServ.findTabelleByName(ligaName);
    }

    public ArrayList<Tabellenposition> sortTabelle(List<Tabellenposition> tabelleUnsortiert){
        ArrayList<Tabellenposition> tabelleSortiert = new ArrayList<>();
        if(tabelleUnsortiert != null) {
            for (int i = 0; i < tabelleUnsortiert.size(); i++) {
                tabelleSortiert.add(tabelleUnsortiert.get(i));
            }
            Collections.sort(tabelleSortiert, new Sortbyroll());
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
            Tabelle t = findTabelleByLigaName(this.ligaNameAnzeigen);
            if (t != null) {
                List <Tabellenposition>test = tabServ.getAllTabellenpositionen(t);
                if(test != null) {
                    for (int i = 0; i < test.size(); i++) {
                        tabPosServ.trageDatenInTabellenpositionEin(test.get(i).getMannschaft());
                    }
                }
                this.tabellenpositionen = sortTabelle(getAllTabellenpositionen(t));
            }
        }
    }

    public void init(){
        this.ligen = new ArrayList<>();
        List <Tabelle> ligas = tabServ.getAlle();
        for(int i = 0; i< ligas.size(); i++){
            this.ligen.add(ligas.get(i).getLigaName());
        }
            Tabelle t = findTabelleByLigaName(this.ligaNameAnzeigen);
            if(this.ligaNameAnzeigen != null){
                this.tabellenpositionen = sortTabelle(getAllTabellenpositionen(t));
            }

    }

}

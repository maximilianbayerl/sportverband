package de.bayerl.sportverband.bean;

import de.bayerl.sportverband.entity.Tabelle;
import de.bayerl.sportverband.entity.Tabellenposition;
import de.bayerl.sportverband.service.Sortbyroll;
import de.bayerl.sportverband.service.TabellenPositionService;
import de.bayerl.sportverband.service.TabellenService;
import javafx.scene.control.Tab;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
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

    @Getter
    @Setter
    private String ligaName;

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
        return tabServ.createTabelle(this.ligaName, this.saison);
    }

    public List<Tabellenposition> getAllTabellenpositionen(Tabelle t){
        return tabServ.getAllTabellenpositionen(t);
    }

    public Tabelle findTabelleByLigaName(String ligaName){
        return tabServ.findTabelleByName(ligaName);
    }

    public ArrayList<Tabellenposition> sortTabelle(List<Tabellenposition> tabelleUnsortiert){
        ArrayList<Tabellenposition> tabelleSortiert = new ArrayList<>();
        Tabellenposition t;
        for(int i = 0;  i < tabelleUnsortiert.size(); i++){
            tabelleSortiert.add(tabelleUnsortiert.get(i));
        }
        Collections.sort(tabelleSortiert, new Sortbyroll());
        for(int m = 0; m<tabelleSortiert.size(); m++){
            tabelleSortiert.get(m).setPlatz(m+1);
        }
        return tabelleSortiert;
    }

    public void init(){
            Tabelle t = findTabelleByLigaName(this.ligaName);
            if(this.ligaName != null){
                this.tabellenpositionen = sortTabelle(getAllTabellenpositionen(t));
            }

    }

}

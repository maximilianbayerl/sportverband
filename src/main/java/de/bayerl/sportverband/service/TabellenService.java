package de.bayerl.sportverband.service;

import de.bayerl.sportverband.entity.Tabelle;
import de.bayerl.sportverband.entity.Tabellenposition;
import de.bayerl.sportverband.repository.TabellenPositionRepository;
import de.bayerl.sportverband.repository.TabellenRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@RequestScoped
public class TabellenService {

    @Inject
    private TabellenPositionRepository tabPosRep;

    @Inject
    private TabellenRepository tabRep;

    @Transactional
    public Tabelle createTabelle(String ligaName, Date saison){
        Tabelle tab = new Tabelle(ligaName, saison);
        return tabRep.persist(tab);
    }

    @Transactional
    public Tabelle addTabellenpositionenToTabelle(Tabellenposition tabpos, String ligaName){
        Tabelle t = tabRep.findByName(ligaName);
        System.out.println(tabpos);
        t.addTabellenPosition(tabpos);
        return tabRep.merge(t);
    }

    @Transactional
    public List<Tabellenposition> getAllTabellenpositionen(Tabelle t){
       List <Tabellenposition> tabpos = tabPosRep.getTabellenpositionenVonTabelle(t);
        return tabpos;
    }
    @Transactional
    public Tabelle findTabelleByName(String ligaName){
        return tabRep.findByName(ligaName);
    }

    @Transactional
    public List<Tabelle> getAlle(){
        return tabRep.findAll();
    }


//todo: delete tabelle, change...
}

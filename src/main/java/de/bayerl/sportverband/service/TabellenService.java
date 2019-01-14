package de.bayerl.sportverband.service;

import de.bayerl.sportverband.entity.Tabelle;
import de.bayerl.sportverband.entity.Tabellenposition;
import de.bayerl.sportverband.repository.TabellenPositionRepository;
import de.bayerl.sportverband.repository.TabellenRepository;
import org.apache.logging.log4j.Logger;
import utils.qualifiers.OptionTabelle;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class TabellenService {

    @Inject
    private TabellenPositionRepository tabPosRep;

    @Inject
    private TabellenRepository tabRep;

    @Inject
    @OptionTabelle
    private Logger logger;

    @Transactional
    public Tabelle createTabelle(String ligaName){
        Tabelle tab = new Tabelle(ligaName);
        logger.info("Neue Tabelle " + ligaName + " erstellt.");
        return tabRep.persist(tab);
    }

    @Transactional
    public void addTabellenpositionenToTabelle(Tabellenposition tabpos, String ligaName){
        Tabelle t = tabRep.findByName(ligaName);
        t.addTabellenPosition(tabpos);
        logger.info("Mannschaft zu Tabelle "+ ligaName + " hinzugefügt.");
        tabRep.merge(t);
    }

    @Transactional
    public List getAllTabellenpositionen(Tabelle t){
       return tabPosRep.getTabellenpositionenVonTabelle(t);
    }
    @Transactional
    public Tabelle findTabelleByName(String ligaName){
        return tabRep.findByName(ligaName);
    }

    @Transactional
    public List<Tabelle> getAlle(){
        return tabRep.findAll();
    }

}

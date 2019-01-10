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
    public Tabelle addTabellenpositionenToTabelle(Tabellenposition tabpos, String ligaName){
        Tabelle t = tabRep.findByName(ligaName);
        t.addTabellenPosition(tabpos);
        logger.info("Mannschaft zu Tabelle "+ ligaName + " hinzugefügt.");
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

    @Transactional
    public Tabelle getTabelleById(long id){return tabRep.findById(id);}

}

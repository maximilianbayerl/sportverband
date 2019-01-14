package de.bayerl.sportverband.service;

import de.bayerl.sportverband.entity.Mannschaft;
import de.bayerl.sportverband.repository.MannschaftsRepository;
import org.apache.logging.log4j.Logger;
import utils.qualifiers.OptionMannschaft;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class MannschaftsService {

    @Inject
    @OptionMannschaft
    private Logger logger;

    @Inject
   private MannschaftsRepository manRep;

    @Transactional
    public Mannschaft create(String mannschaftsName, Integer anzahlMitgliederFanClub){
        Mannschaft m = new Mannschaft(mannschaftsName, anzahlMitgliederFanClub);
        m.createTabellenPosition();
        m.createStatistik();
        manRep.persist(m);
        logger.info("Neue Mannschaft erstellt: " +  m.getMannschaftsName());
        return m;
    }

    @Transactional
    public String deleteMannschaft(Mannschaft mannschaft){
       manRep.remove(mannschaft.getId());
       logger.info("Mannschaft erfolgreich gelöscht.");
        return "Mannschaft erfolgreich gelöscht.";
    }


    @Transactional
    public Mannschaft changeMannschaft (Mannschaft mannschaft){
        logger.info("Mannschaft erfolgreich aktualisiert.");
        return manRep.merge(mannschaft);
    }

    @Transactional
    public Mannschaft getMannschaftById(Long id){
        return manRep.findById(id);
    }

    @Transactional
    public List<Mannschaft> getMannschaftenByLigaName(String ligaName){
        return manRep.findByLigaName(ligaName);
    }


    @Transactional
    public List<Mannschaft> getMannschaften() {return manRep.findAll();}



}

package de.bayerl.sportverband.service;

import de.bayerl.sportverband.entity.Mannschaft;
import de.bayerl.sportverband.repository.MannschaftsRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
@WebService
public class MannschaftsService {
    @Inject
   private MannschaftsRepository manRep;

    @Transactional
    public Mannschaft create(String mannschaftsName, Integer anzahlMitgliederFanClub){
        Mannschaft m = new Mannschaft(mannschaftsName, anzahlMitgliederFanClub);
        m.createTabellenPosition();
        m.createStatistik();
        manRep.persist(m);
        return m;
    }

    @Transactional
    public String deleteMannschaft(Mannschaft mannschaft){
       manRep.remove(mannschaft.getId());
        return "Mannschaft successfully deleted";
    }

    @Transactional
    public Mannschaft getMannschaftByMannschaftsName(String mannschaftsName){
        return manRep.findByName(mannschaftsName);
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

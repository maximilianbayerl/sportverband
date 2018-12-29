package de.bayerl.sportverband.service;

import de.bayerl.sportverband.entity.Mannschaft;
import de.bayerl.sportverband.entity.Spiel;
import de.bayerl.sportverband.repository.SpielplanRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.transaction.Transactional;
import java.util.List;


@WebService
@RequestScoped
public class SpielplanAnzeigenService {

    @Inject
    private SpielplanRepository spRep;

    @Transactional
    public List<Spiel> getSpiele() {return spRep.findAll();}

    @Transactional
    public List<Spiel> getSpieleByLigaName(String ligaName) {return spRep.findByLigaName(ligaName); }

    @Transactional
    public List<Spiel> getSpieleEinerMannschaft(Mannschaft m){
        return spRep.findByMannschaft(m);
    }

    @Transactional
    public Spiel getSpielByID(Long id){
        return spRep.findById(id);
    }
}
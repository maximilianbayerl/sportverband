package de.bayerl.sportverband.service;


import de.bayerl.sportverband.entity.Mannschaft;
import de.bayerl.sportverband.entity.Spiel;
import de.bayerl.sportverband.repository.SpielplanRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@RequestScoped
@WebService
public class SpielplanService {
    @Inject
    MannschaftsService mannschaftsService;

    @Inject
    private SpielplanRepository spRep;

    @Transactional
    public List <Spiel> createHRRR(String ligaName){
        List <Mannschaft> m = mannschaftsService.getMannschaftenByLigaName(ligaName);
        for(int i = 0; i < m.size(); i++){
            for(int x = 0; x < m.size(); x++){
                if(x!=i){
                    Spiel s = new Spiel(m.get(i), m.get(x), ligaName);
                    spRep.persist(s);
                }
            }
        }
        return spRep.findByLigaName(ligaName);
    }

    @Transactional
    public List<Spiel> createHR(String ligaName){
        List <Mannschaft> m = mannschaftsService.getMannschaftenByLigaName(ligaName);
        for(int i = 0; i < m.size(); i++){
            for(int x = i; x < m.size(); x++){
                if(x!=i){
                    Spiel s = new Spiel(m.get(i), m.get(x),ligaName);
                    spRep.persist(s);
                }
            }
        }
        return spRep.findByLigaName(ligaName);
    }


    @Transactional
    public Spiel tragePunkteEin (Long spielId, int heimH, int heimE, int gastH, int gastE, Boolean gespielt){
        Spiel s = spRep.findById(spielId);
        s.punkteEintragen(heimH, heimE, gastH, gastE,gespielt);
        return spRep.merge(s);
    }

    @Transactional
    public Spiel trageStadionEin (Long spielId, Date datum, Long stadionId){
        Spiel s = spRep.findById(spielId);
        s.bucheStadionFake(stadionId, datum);
        System.out.println(datum);
        System.out.println("Stadion gebucht");
        return spRep.merge(s);
    }


    @Transactional
    public String deleteSpiel(Spiel spiel){
        spRep.remove(spiel.getId());
        return "Spiel successfully deleted";
    }

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



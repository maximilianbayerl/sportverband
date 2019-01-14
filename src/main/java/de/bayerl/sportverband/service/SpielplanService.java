package de.bayerl.sportverband.service;


import de.bayerl.sportverband.entity.Mannschaft;
import de.bayerl.sportverband.entity.Spiel;
import de.bayerl.sportverband.repository.SpielplanRepository;
import org.apache.logging.log4j.Logger;
import utils.qualifiers.OptionSpielplan;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@RequestScoped
public class SpielplanService {
    @Inject
    MannschaftsService mannschaftsService;

    @Inject
    private SpielplanRepository spRep;

    @Inject
    @OptionSpielplan
    private Logger logger;


    @Transactional
    public List hrRrAnlegen(String ligaName){
        List <Mannschaft> m = mannschaftsService.getMannschaftenByLigaName(ligaName);
        if (m.size()>0) {
            for (int i = 0; i < m.size(); i++) {
                for (int x = 0; x < m.size(); x++) {
                    if (x != i) {
                        Spiel s = new Spiel(m.get(i), m.get(x), ligaName);
                        spRep.persist(s);
                    }
                }
            }
            logger.info("Spielplan(Hin- und Rückrunde) erfolgreich erstellt.");
            return spRep.findByLigaName(ligaName);
        } else {
            return null;
        }
    }

    @Transactional
    public List hrAnlegen(String ligaName){
        List <Mannschaft> m = mannschaftsService.getMannschaftenByLigaName(ligaName);
        if(m.size()>0) {
            for (int i = 0; i < m.size(); i++) {
                for (int x = i; x < m.size(); x++) {
                    if (x != i) {
                        Spiel s = new Spiel(m.get(i), m.get(x), ligaName);
                        spRep.persist(s);
                    }
                }
            }
            logger.info("Spielplan(Hinrunde) erfolgreich erstellt.");
            return spRep.findByLigaName(ligaName);
        } else {
            return null;
        }
    }


    @Transactional
    public Spiel tragePunkteEin (Long spielId, int heimH, int heimE, int gastH, int gastE, Boolean gespielt){
        Spiel s = spRep.findById(spielId);
        s.punkteEintragen(heimH, heimE, gastH, gastE,gespielt);
        logger.info("Ergebnis eingetragen für Spiel: " + s.getId());
        return spRep.merge(s);
    }

    @Transactional
    public Spiel trageStadionEin (Long spielId, Date datum, String stadionName){
        Spiel s = spRep.findById(spielId);
        s.bucheStadion(stadionName, datum);
        return spRep.merge(s);
    }

    @Transactional
    public List getSpieleByLigaName(String ligaName) {
        List spiele = spRep.findByLigaName(ligaName);
        if(spiele != null){
            return 	spiele;
        } else {
            return null;
        }
    }

    @Transactional
    public List getSpieleEinerMannschaft(Mannschaft m){
        return spRep.findByMannschaft(m);
    }




}



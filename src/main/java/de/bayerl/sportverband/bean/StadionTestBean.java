package de.bayerl.sportverband.bean;

import de.bayerl.sportverband.entity.Spiel;
import de.bayerl.sportverband.entity.Tabelle;
import de.bayerl.sportverband.service.SpielplanService;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Alternative;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SessionScoped
@Alternative
public class StadionTestBean implements AbstrakteBean, Serializable {
    @Inject
    SpielplanService spServ;

    @Override
    public List <Spiel> bucheStadion(Spiel m, Date datum, Tabelle selectedLigaAnzeigen){
        Random r = new Random();
        Long stadionId = 100L + r.nextLong();
        List<Spiel> spieleHeim = spServ.getSpieleEinerMannschaft(m.getMannschaftHeim());
        List <Spiel> spieleGast = spServ.getSpieleEinerMannschaft(m.getMannschaftHeim());
        List <Spiel> alleSpiele = new ArrayList<Spiel>(spieleHeim);
        alleSpiele.addAll(spieleGast);
        boolean checker = true;
        long minuteInMillis = 60000;
        for(int i = 0; i< alleSpiele.size(); i++){
            if(alleSpiele.get(i).getDatum()!= null) {
                long t = alleSpiele.get(i).getDatum().getTime() + ((45 * 2) + 15 + 10 + 10) * minuteInMillis;
                Date afterAddingMinutes = new Date(t);
                if (!afterAddingMinutes.before(datum) && !alleSpiele.get(i).getDatum().after(datum)) {
                    checker = false;
                }
            }
        }
        if(checker){
            Spiel s = spServ.trageStadionEin(m.getId(), datum, "FAKE STADION " + stadionId);
            return spServ.getSpieleByLigaName(selectedLigaAnzeigen.getLigaName());
        } else {
            FacesContext.getCurrentInstance().addMessage("bucheStadion", new FacesMessage(
                    "Stadion kann nicht gebucht werden, da sich der Termin mit einem anderem Spiel dieser " +
                            "Mannschaften überschneidet."));
            return null;
        }
    }
}

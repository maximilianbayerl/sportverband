package de.bayerl.sportverband.bean;

import de.bayerl.sportverband.entity.Spiel;
import de.bayerl.sportverband.entity.Tabelle;
import de.bayerl.sportverband.service.SpielplanService;
import de.stadionverbund.BuchungService;
import de.stadionverbund.BuchungServiceService;
import de.stadionverbund.Stadion;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Alternative;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@SessionScoped
@Alternative
public class StadionBean implements AbstrakteBean, Serializable {
    @Inject
    SpielplanService spServ;

    @Override
    public List <Spiel> bucheStadion(Spiel m, Date datum, Tabelle selectedLigaAnzeigen){
        GregorianCalendar c = new GregorianCalendar();
        BuchungServiceService service = new BuchungServiceService();
        BuchungService stub = service.getBuchungServicePort();
        int anzahlFansGemeinsam = m.getMannschaftHeim().getAnzahlMitgliederFanClub() +   m.getMannschaftGast().getAnzahlMitgliederFanClub();
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
            c.setTime(datum);
            try {
                XMLGregorianCalendar datumZumSenden = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
               Stadion stad = stub.bucheStadionAutomatisch(m.getId(),anzahlFansGemeinsam, datumZumSenden);
                if(stad!= null) {
                    spServ.trageStadionEin(m.getId(), datum, stad.getName());
                    return spServ.getSpieleByLigaName(selectedLigaAnzeigen.getLigaName());
                } else {
                    FacesContext.getCurrentInstance().addMessage("bucheStadion", new FacesMessage(
                            "An diesem Termin ist kein passendes Stadion frei oder es wurde bereits ein Stadion für " +
                                    "dieses Spiel gebucht."));
                    return null;
                }
            } catch (Exception e){
                FacesContext.getCurrentInstance().addMessage("bucheStadion", new FacesMessage(
                        "Fehler beim Buchen des Stadions aufgetreten."));
                return null;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("bucheStadion", new FacesMessage(
                    "Stadion kann nicht gebucht werden, da sich der Termin mit einem anderem Spiel dieser " +
                            "Mannschaften überschneidet."));
            return null;
        }
    }
}

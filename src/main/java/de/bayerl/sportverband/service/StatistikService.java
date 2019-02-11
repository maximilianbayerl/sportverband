package de.bayerl.sportverband.service;

import de.bayerl.sportverband.entity.Mannschaft;
import de.bayerl.sportverband.entity.Spiel;
import de.bayerl.sportverband.entity.Statistik;
import de.bayerl.sportverband.repository.SpielplanRepository;
import de.bayerl.sportverband.repository.StatistikRepository;
import org.apache.logging.log4j.Logger;
import utils.qualifiers.OptionStatistik;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


@RequestScoped
public class StatistikService {

    @Inject
    private SpielplanRepository spRep;

    @Inject
    StatistikRepository statRep;

    @Inject
    @OptionStatistik
    private Logger logger;


    void aktualisiereStatistik(Mannschaft m){
        boolean init = true;
        int ungeschlagenSeitAnzahlSpiele = 0;
        int besteSiegeSerie = 0;
        int aktuelleSiegeSerie = 0;
        int besteTordifferenzProSpiel = 0;
        int anzahlTore = 0;
        int anzahlToreProSpiel;
        int anzahlGegenToreProSpiel;
        double schnittToreProSpiel = 0;
        double schnittPunkteProSpiel = 0;
        int anzahlAbsolvierteSpieleGesamt = 0;
        int punkte = 0;
        List spiele = spRep.findByMannschaft(m);
        ArrayList<Spiel> spieleSortiert = new ArrayList<>();
        Statistik s = statRep.findByMannschaft(m);

        if(spiele != null){
            for (Object aSpiele : spiele) {
                spieleSortiert.add((Spiel) aSpiele);
            }
            spieleSortiert.sort(new Sortbydate());
            for (Spiel aSpieleSortiert : spieleSortiert) {
                if (aSpieleSortiert.getAbsolviert()) {
                    //heim
                    if (aSpieleSortiert.getMannschaftHeim().getId().equals(m.getId())) {
                        if (aSpieleSortiert.getTrefferHeimEnde() > aSpieleSortiert.getTrefferGastEnde()) {
                            //sieg
                            punkte = punkte + 3;
                            ungeschlagenSeitAnzahlSpiele++;
                            aktuelleSiegeSerie++;
                            if (aktuelleSiegeSerie > besteSiegeSerie) {
                                besteSiegeSerie = aktuelleSiegeSerie;
                            }
                            anzahlTore = anzahlTore + aSpieleSortiert.getTrefferHeimEnde();
                            anzahlToreProSpiel = aSpieleSortiert.getTrefferHeimEnde();
                            anzahlGegenToreProSpiel = aSpieleSortiert.getTrefferGastEnde();
                            if ((anzahlToreProSpiel - anzahlGegenToreProSpiel) > besteTordifferenzProSpiel) {
                                besteTordifferenzProSpiel = anzahlToreProSpiel - anzahlGegenToreProSpiel;
                                init = false;
                            }
                        } else if (aSpieleSortiert.getTrefferHeimEnde().equals(aSpieleSortiert.getTrefferGastEnde())) {
                            //unentschieden
                            punkte++;
                            aktuelleSiegeSerie = 0;
                            ungeschlagenSeitAnzahlSpiele++;
                            anzahlTore = anzahlTore + aSpieleSortiert.getTrefferHeimEnde();
                            anzahlToreProSpiel = aSpieleSortiert.getTrefferHeimEnde();
                            anzahlGegenToreProSpiel = aSpieleSortiert.getTrefferGastEnde();
                            if (besteTordifferenzProSpiel < 0) {
                                besteTordifferenzProSpiel = anzahlToreProSpiel - anzahlGegenToreProSpiel;
                                init = false;
                            }
                        } else {
                            //verloren
                            aktuelleSiegeSerie = 0;
                            anzahlTore = anzahlTore + aSpieleSortiert.getTrefferHeimEnde();
                            anzahlToreProSpiel = aSpieleSortiert.getTrefferHeimEnde();
                            anzahlGegenToreProSpiel = aSpieleSortiert.getTrefferGastEnde();
                            ungeschlagenSeitAnzahlSpiele = 0;
                            if (init) {
                                besteTordifferenzProSpiel = anzahlToreProSpiel - anzahlGegenToreProSpiel;
                                init = false;
                            }
                        }
                    } else { // gast
                        if (aSpieleSortiert.getTrefferHeimEnde() > aSpieleSortiert.getTrefferGastEnde()) {
                            //verloren
                            ungeschlagenSeitAnzahlSpiele = 0;
                            anzahlTore = anzahlTore + aSpieleSortiert.getTrefferGastEnde();
                            anzahlToreProSpiel = aSpieleSortiert.getTrefferGastEnde();
                            anzahlGegenToreProSpiel = aSpieleSortiert.getTrefferHeimEnde();
                            if (init) {
                                besteTordifferenzProSpiel = anzahlToreProSpiel - anzahlGegenToreProSpiel;
                                init = false;
                            }
                        } else if (aSpieleSortiert.getTrefferHeimEnde().equals(aSpieleSortiert.getTrefferGastEnde())) {
                            //unentschieden
                            punkte++;
                            ungeschlagenSeitAnzahlSpiele++;
                            anzahlTore = anzahlTore + aSpieleSortiert.getTrefferGastEnde();
                            anzahlToreProSpiel = aSpieleSortiert.getTrefferGastEnde();
                            anzahlGegenToreProSpiel = aSpieleSortiert.getTrefferHeimEnde();
                            if (besteTordifferenzProSpiel < 0) {
                                besteTordifferenzProSpiel = anzahlToreProSpiel - anzahlGegenToreProSpiel;
                                init = false;
                            }
                        } else {
                            //sieg
                            punkte = punkte + 3;
                            ungeschlagenSeitAnzahlSpiele++;
                            aktuelleSiegeSerie++;
                            anzahlTore = anzahlTore + aSpieleSortiert.getTrefferGastEnde();
                            if (aktuelleSiegeSerie > besteSiegeSerie) {
                                besteSiegeSerie = aktuelleSiegeSerie;
                            }
                            anzahlToreProSpiel = aSpieleSortiert.getTrefferGastEnde();
                            anzahlGegenToreProSpiel = aSpieleSortiert.getTrefferHeimEnde();
                            if ((anzahlToreProSpiel - anzahlGegenToreProSpiel) > besteTordifferenzProSpiel) {
                                besteTordifferenzProSpiel = anzahlToreProSpiel - anzahlGegenToreProSpiel;
                                init = false;
                            }

                        }

                    }
                    anzahlAbsolvierteSpieleGesamt++;
                    schnittToreProSpiel = (float) anzahlTore / anzahlAbsolvierteSpieleGesamt;
                    schnittPunkteProSpiel = (float) punkte / anzahlAbsolvierteSpieleGesamt;
                }
            }
        }
        s.setUngeschlagenSeitAnzahlSpiele(ungeschlagenSeitAnzahlSpiele);
        s.setBesteTordifferenz(besteTordifferenzProSpiel);
        s.setPunkteProSpiel(schnittPunkteProSpiel);
        s.setToreProSpiel(schnittToreProSpiel);
        s.setSiegesSerie(besteSiegeSerie);
        logger.info("Statistik erfolgreich erstellt für Mannschaft: " + m.getMannschaftsName());
        statRep.merge(s);
    }

    public Statistik getStatistikByMannschaft(Mannschaft m){
        return statRep.findByMannschaft(m);
    }

}

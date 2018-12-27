package de.bayerl.sportverband.service;

import de.bayerl.sportverband.entity.Mannschaft;
import de.bayerl.sportverband.entity.Spiel;
import de.bayerl.sportverband.entity.Statistik;
import de.bayerl.sportverband.repository.SpielplanRepository;
import de.bayerl.sportverband.repository.StatistikRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RequestScoped
@WebService
public class StatistikService {

    @Inject
    private SpielplanRepository spRep;

    @Inject
    StatistikRepository statRep;

    public void aktualisiereStatistik (Mannschaft m){
        int ungeschlagenSeitAnzahlSpiele = 0;
        int besteSiegeSerie = 0;
        int aktuelleSiegeSerie = 0;
        int besteTordifferenzProSpiel = 0;
        int anzahlTore = 0;
        int anzahlToreProSpiel = 0;
        int anzahlGegenToreProSpiel = 0;
        double schnittToreProSpiel = 0;
        double schnittPunkteProSpiel = 0;
        int anzahlAbsolvierteSpieleGesamt = 0;
        int punkte = 0;
        List<Spiel> spiele = spRep.findByMannschaft(m);
        ArrayList<Spiel> spieleSortiert = new ArrayList<>();
        Statistik s = statRep.findByMannschaft(m);

        if(spiele != null){
            for(int i = 0; i<spiele.size(); i++) {
                spieleSortiert.add(spiele.get(i));
            }
            Collections.sort(spieleSortiert, new Sortbydate());
            for(int i = 0; i<spieleSortiert.size(); i++){
                if(spieleSortiert.get(i).getAbsolviert()) {
                    //heim
                    if(spieleSortiert.get(i).getMannschaftHeim().getId() == m.getId()){
                        if(spieleSortiert.get(i).getTrefferHeimEnde()> spieleSortiert.get(i).getTrefferGastEnde()){
                            //sieg
                            punkte = punkte +3;
                            ungeschlagenSeitAnzahlSpiele ++;
                            aktuelleSiegeSerie ++;
                            if(aktuelleSiegeSerie>besteSiegeSerie){
                                besteSiegeSerie = aktuelleSiegeSerie;
                            }
                            anzahlTore = anzahlTore + spieleSortiert.get(i).getTrefferHeimEnde();
                            anzahlToreProSpiel = spieleSortiert.get(i).getTrefferHeimEnde();
                            anzahlGegenToreProSpiel = spieleSortiert.get(i).getTrefferGastEnde();
                            if((anzahlToreProSpiel- anzahlGegenToreProSpiel) > besteTordifferenzProSpiel){
                                besteTordifferenzProSpiel = anzahlToreProSpiel - anzahlGegenToreProSpiel;
                            }
                        } else if(spieleSortiert.get(i).getTrefferHeimEnde()==
                                spieleSortiert.get(i).getTrefferGastEnde()){
                            //unentschieden
                            punkte ++;
                            aktuelleSiegeSerie = 0;
                            ungeschlagenSeitAnzahlSpiele ++;
                            anzahlTore = anzahlTore + spieleSortiert.get(i).getTrefferHeimEnde();


                        } else {
                            //verloren
                            aktuelleSiegeSerie = 0;
                            anzahlTore = anzahlTore + spieleSortiert.get(i).getTrefferHeimEnde();
                            ungeschlagenSeitAnzahlSpiele = 0;

                        }
                    } else { // gast
                        if(spieleSortiert.get(i).getTrefferHeimEnde()> spieleSortiert.get(i).getTrefferGastEnde()){
                            //verloren
                            ungeschlagenSeitAnzahlSpiele = 0;
                            anzahlTore = anzahlTore + spieleSortiert.get(i).getTrefferGastEnde();

                        } else if(spieleSortiert.get(i).getTrefferHeimEnde()==
                                spieleSortiert.get(i).getTrefferGastEnde()){
                            //unentschieden
                            punkte ++;
                            ungeschlagenSeitAnzahlSpiele ++;
                            anzahlTore = anzahlTore + spieleSortiert.get(i).getTrefferGastEnde();


                        } else {
                            //sieg
                            punkte = punkte +3;
                            ungeschlagenSeitAnzahlSpiele ++;
                            aktuelleSiegeSerie ++;
                            anzahlTore = anzahlTore + spieleSortiert.get(i).getTrefferGastEnde();
                            if(aktuelleSiegeSerie>besteSiegeSerie){
                                besteSiegeSerie = aktuelleSiegeSerie;
                            }
                            anzahlToreProSpiel = spieleSortiert.get(i).getTrefferGastEnde();
                            anzahlGegenToreProSpiel = spieleSortiert.get(i).getTrefferHeimEnde();
                            if((anzahlToreProSpiel- anzahlGegenToreProSpiel) > besteTordifferenzProSpiel){
                                besteTordifferenzProSpiel = anzahlToreProSpiel - anzahlGegenToreProSpiel;
                            }

                        }

                    }
                    anzahlAbsolvierteSpieleGesamt ++;
                    schnittToreProSpiel =(float) anzahlTore / anzahlAbsolvierteSpieleGesamt;
                    schnittPunkteProSpiel = (float) punkte / anzahlAbsolvierteSpieleGesamt;

                }
            }
        }
        s.setUngeschlagenSeitAnzahlSpiele(ungeschlagenSeitAnzahlSpiele);
        s.setBesteTordifferenz(besteTordifferenzProSpiel);
        s.setPunkteProSpiel(schnittPunkteProSpiel);
        s.setToreProSpiel(schnittToreProSpiel);
        s.setSiegesSerie(besteSiegeSerie);
        statRep.merge(s);
    }

    public Statistik getStatistikByMannschaft(Mannschaft m){
        return statRep.findByMannschaft(m);
    }

}

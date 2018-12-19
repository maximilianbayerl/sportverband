package de.bayerl.sportverband.service;

import de.bayerl.sportverband.entity.Mannschaft;
import de.bayerl.sportverband.entity.Spiel;
import de.bayerl.sportverband.entity.Tabellenposition;
import de.bayerl.sportverband.repository.SpielplanRepository;
import de.bayerl.sportverband.repository.TabellenPositionRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
@WebService
public class TabellenPositionService {

    @Inject
    private SpielplanRepository spRep;

    @Inject
    private TabellenPositionRepository tabPosRep;


    public Tabellenposition getTabellenPositionOfMannschaft(Mannschaft m){
       return m.getTabellenPosition();
    }

    @Transactional
    public Tabellenposition trageDatenInTabellenpositionEin(Mannschaft m){
        List<Spiel> spiele = spRep.findByMannschaft(m);
        Tabellenposition aktuellePosition = m.getTabellenPosition();
        System.out.println(aktuellePosition);
        int anzahlSiege = 0;
        int anzahlUnentschieden = 0;
        int anzahlNiederlage = 0;
        int anzahlPunkte = 0;
        int anzahlTore = 0;
        int anzahlGegentore = 0;
        int anzahlTorDifferenz = 0;
        int anzahlAbsolvierteSpiele = 0;

        for(int i = 0; i<spiele.size(); i++){
            if(spiele.get(i).getAbsolviert()) {
                int anzahlToreProSpiel;
            int anzahlGegenToreProSpiel;
            if(spiele.get(i).getMannschaftHeim().getId() == m.getId()){
                anzahlTore = anzahlTore + spiele.get(i).getTrefferHeimEnde();
                anzahlToreProSpiel = spiele.get(i).getTrefferHeimEnde();
                anzahlGegentore = anzahlGegentore + spiele.get(i).getTrefferGastEnde();
                anzahlGegenToreProSpiel = spiele.get(i).getTrefferGastEnde();
            } else {
                anzahlTore = anzahlTore + spiele.get(i).getTrefferGastEnde();
                anzahlToreProSpiel = spiele.get(i).getTrefferGastEnde();
                anzahlGegentore = anzahlGegentore + spiele.get(i).getTrefferHeimEnde();
                anzahlGegenToreProSpiel = spiele.get(i).getTrefferHeimEnde();
            }
            if(anzahlToreProSpiel > anzahlGegenToreProSpiel){
                anzahlSiege ++;
            } else if(anzahlToreProSpiel == anzahlGegenToreProSpiel){
                anzahlUnentschieden ++;
            } else if(anzahlToreProSpiel < anzahlGegenToreProSpiel && anzahlGegenToreProSpiel > 0){
                anzahlNiederlage ++;
            }
            }
        }
        anzahlPunkte = anzahlSiege * 3 + anzahlUnentschieden;
        anzahlTorDifferenz = anzahlTore - anzahlGegentore;
        anzahlAbsolvierteSpiele = anzahlSiege + anzahlUnentschieden + anzahlNiederlage;

        aktuellePosition.aktualisiereWerte(anzahlSiege, anzahlNiederlage, anzahlUnentschieden, anzahlPunkte,
                anzahlTore,anzahlGegentore, anzahlTorDifferenz, anzahlAbsolvierteSpiele);

        return tabPosRep.merge(aktuellePosition);
    }

}

package de.bayerl.sportverband.bean;

import de.bayerl.sportverband.entity.Mannschaft;
import de.bayerl.sportverband.entity.Spiel;
import de.bayerl.sportverband.entity.Tabelle;
import de.bayerl.sportverband.service.SpielplanService;
import de.bayerl.sportverband.service.TabellenPositionService;
import de.bayerl.sportverband.service.TabellenService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@ApplicationScoped
public class SpielBean implements Serializable {
    @Inject
    SpielplanService spServ;

    @Inject
    TabellenPositionService tabPosServ;

    @Inject
    TabellenService tabServ;

    @Getter
    @Setter
    private Mannschaft mannschaftHeim;

    @Getter
    @Setter
    private Integer trefferHeimErsteHalbzeit;

    @Getter
    @Setter
    private Integer trefferHeimEnde;

    @Getter
    @Setter
    private Mannschaft mannschaftGast;

    @Getter
    @Setter
    private Integer trefferGastErsteHalbzeit;

    @Getter
    @Setter
    private Integer trefferGastEnde;

    @Getter
    @Setter
    private Date datum;

    @Getter
    @Setter
    private Long stadionId;

    @Getter
    @Setter
    private String ligaName;

    @Getter
    @Setter
    private List <String> ligen;

    @Getter
    @Setter
    private String ligaNameAnzeigen;

    @Getter
    @Setter
    private List <String> ligenAnzeigen;

    @Getter
    @Setter
    private List<Spiel> spiele;

    public List<Spiel> createHR(){
        if (spServ.getSpieleByLigaName(this.ligaName).size()== 0){
            if(ligen.size()> 0) {
             this.ligaNameAnzeigen = this.ligaName;
             try {
                 return spServ.createHR(this.ligaName);
             } catch (Exception e){
                 System.out.println("Could not create Spiele");
                 return null;
             }
           } else {
              return null;
          }
        } else {
            return null;
        }
    }

    public List<Spiel> createHRRR(){
        if (spServ.getSpieleByLigaName(this.ligaName).size()== 0) {
            if (ligen.size() > 0) {
                this.ligaNameAnzeigen = this.ligaName;
                try {
                    return spServ.createHRRR(this.ligaName);
                } catch(Exception e){
                    System.out.println("Could not create Spiele");
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Spiel tragePunkteEin (Spiel m){
        if(m.getStadionId()!= null){
            if(this.trefferHeimEnde>= this.trefferHeimErsteHalbzeit && this.trefferGastEnde>=this.trefferGastErsteHalbzeit) {
                Spiel s = spServ.tragePunkteEin(m.getId(), this.trefferHeimErsteHalbzeit,
                        this.trefferHeimEnde,
                        this.trefferGastErsteHalbzeit, this.trefferGastEnde, true);
                this.mannschaftHeim = s.getMannschaftHeim();
                this.mannschaftGast = s.getMannschaftGast();
                tabPosServ.trageDatenInTabellenpositionEin(this.mannschaftHeim);
                tabPosServ.trageDatenInTabellenpositionEin(this.mannschaftGast);
                this.spiele = spServ.getSpieleByLigaName(this.ligaNameAnzeigen);
                this.trefferGastEnde = null;
                this.trefferGastErsteHalbzeit = null;
                this.trefferHeimEnde = null;
                this.trefferHeimErsteHalbzeit = null;
                return s;
            } else {
                this.trefferGastEnde = null;
                this.trefferGastErsteHalbzeit = null;
                this.trefferHeimEnde = null;
                this.trefferHeimErsteHalbzeit = null;
                return null;
            }
        } else {
            return null;
        }
    }

    public List <Spiel> spieleAnzeigen(){
        this.spiele = null;
        this.spiele = spServ.getSpieleByLigaName(this.ligaNameAnzeigen);
        return this.spiele;
    }

    public Spiel bucheStadion(Spiel m){
        // todo: greife auf chris bla bla zu buche usw now fake:
        // schicke datum und anzahl fans beider mannschaften bspw.
        // returns true if ok...
        Random r = new Random();
        this.stadionId = 100L + r.nextLong();
        List <Spiel> spieleHeim = spServ.getSpieleEinerMannschaft(m.getMannschaftHeim());
        List <Spiel> spieleGast = spServ.getSpieleEinerMannschaft(m.getMannschaftHeim());
        List <Spiel> alleSpiele = new ArrayList<Spiel>(spieleHeim);
        alleSpiele.addAll(spieleGast);
        boolean checker = true;
        long minuteInMillis = 60000;
        for(int i = 0; i< alleSpiele.size(); i++){
            if(alleSpiele.get(i).getDatum()!= null) {
                long t = alleSpiele.get(i).getDatum().getTime() + ((45 * 2) + 15 + 10 + 10) * minuteInMillis;
                Date afterAddingMinutes = new Date(t);
                if (!afterAddingMinutes.before(this.datum) && !alleSpiele.get(i).getDatum().after(this.datum)) {
                    checker = false;
                }
            }
        }
        if(checker){
        Spiel s = spServ.trageStadionEin(m.getId(), this.datum, this.stadionId);
        this.spiele = spServ.getSpieleByLigaName(this.ligaNameAnzeigen);
        this.datum = null;
        return s;
        } else {
            return null;
        }
    }

    public void init(){
        this.ligen = new ArrayList<>();
        this.ligenAnzeigen = new ArrayList<>();
        List <Tabelle> ligas = tabServ.getAlle();
        for(int i = 0; i< ligas.size(); i++){
            this.ligen.add(ligas.get(i).getLigaName());
            this.ligenAnzeigen.add(ligas.get(i).getLigaName());
        }
    }
}

package de.bayerl.sportverband.service;

import de.bayerl.sportverband.entity.Tabellenposition;

import java.util.Comparator;

public class Sortbyroll implements Comparator <Tabellenposition> {
    public int compare(Tabellenposition a, Tabellenposition b) {
        if (a != null && b != null) {
            if (b.getAnzahlPunkte() - a.getAnzahlPunkte() == 0) {
                return b.getAnzahlTorDifferenz() - a.getAnzahlTorDifferenz();
            } else {
                return b.getAnzahlPunkte() - a.getAnzahlPunkte();
            }
        }else {
            return 0;
        }
    }

}

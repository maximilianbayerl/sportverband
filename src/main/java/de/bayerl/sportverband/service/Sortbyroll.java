package de.bayerl.sportverband.service;

import de.bayerl.sportverband.entity.Tabellenposition;

import java.util.Comparator;

public class Sortbyroll implements Comparator <Tabellenposition> {
    public int compare(Tabellenposition a, Tabellenposition b){
        return a.getPlatz() - b.getPlatz();
    }
}

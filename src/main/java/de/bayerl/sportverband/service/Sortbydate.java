package de.bayerl.sportverband.service;

import de.bayerl.sportverband.entity.Spiel;

import java.util.Comparator;

public class Sortbydate implements Comparator <Spiel> {
    public int compare(Spiel a, Spiel b) {
        if (a.getDatum() !=null && b.getDatum() != null) {
            return a.getDatum().compareTo(b.getDatum());
        }else {
            return 0;
        }
    }
}

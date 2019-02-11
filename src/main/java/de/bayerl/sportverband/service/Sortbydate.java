package de.bayerl.sportverband.service;

import de.bayerl.sportverband.entity.Spiel;

import java.util.Comparator;

/**
 * übernommen von:
 * https://stackoverflow.com/questions/5927109/sort-objects-in-arraylist-by-date
 */

public class Sortbydate implements Comparator <Spiel> {
    public int compare(Spiel a, Spiel b) {
        if (a.getDatum() !=null && b.getDatum() != null) {
            return a.getDatum().compareTo(b.getDatum());
        }else {
            return 0;
        }
    }
}

package de.bayerl.sportverband.bean;


import de.bayerl.sportverband.entity.Spiel;
import de.bayerl.sportverband.entity.Tabelle;
import java.util.Date;


public interface AbstrakteBean{
  void bucheStadion(Spiel m, Date datum, Tabelle selectedLigaAnzeigen);
}

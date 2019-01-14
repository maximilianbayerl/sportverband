package de.bayerl.sportverband.bean;


import de.bayerl.sportverband.entity.Spiel;
import de.bayerl.sportverband.entity.Tabelle;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface AbstrakteBean{

    public List<Spiel> bucheStadion(Spiel m, Date datum, Tabelle selectedLigaAnzeigen);
}

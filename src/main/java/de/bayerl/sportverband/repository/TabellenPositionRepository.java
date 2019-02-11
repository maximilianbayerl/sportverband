package de.bayerl.sportverband.repository;

import de.bayerl.sportverband.entity.Tabelle;
import de.bayerl.sportverband.entity.Tabellenposition;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
@Transactional
public class TabellenPositionRepository extends SingleIdEntityRepository <Tabellenposition> {
    public List getTabellenpositionenVonTabelle(Tabelle t){
        Query q =
                this.getEntityManager().createQuery("SELECT p FROM Tabellenposition as p WHERE p.tabelle = " +
                        ":tabelle");
        q.setParameter("tabelle", t);
        List tabellenpositionen = q.getResultList();
        if(tabellenpositionen.isEmpty()){
            return null;
        } else {
            return tabellenpositionen;
        }
    }
}

package de.bayerl.sportverband.repository;

import de.bayerl.sportverband.entity.Tabelle;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
@Transactional
public class TabellenRepository extends SingleIdEntityRepository <Tabelle> {
    public Tabelle findByName(String ligaName){
        Query q = this.getEntityManager().createQuery("Select t FROM Tabelle as t WHERE t.ligaName= " +
                ":ligaName");
        q.setParameter("ligaName",ligaName);
        List<Tabelle> ligaNamen = q.getResultList();
        if (ligaNamen.isEmpty()){
            return null;
        } else {
            return ligaNamen.get(0);
        }
    }
}

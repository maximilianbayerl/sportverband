package de.bayerl.sportverband.repository;

import de.bayerl.sportverband.entity.Mannschaft;
import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
@Transactional
public class MannschaftsRepository extends SingleIdEntityRepository<Mannschaft> {
    public List findByLigaName(String ligaName){
        Query q = this.getEntityManager().createQuery("Select m FROM Mannschaft as m WHERE m.tabellenPosition.tabelle" +
                ".ligaName= " + ":ligaName");
        q.setParameter("ligaName",ligaName);
        List mannschaften = q.getResultList();
        if (mannschaften.isEmpty()){
            return null;
        } else {
            return mannschaften;
        }
    }
}

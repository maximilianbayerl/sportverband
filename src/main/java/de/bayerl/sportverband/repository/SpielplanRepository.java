package de.bayerl.sportverband.repository;



import de.bayerl.sportverband.entity.Mannschaft;
import de.bayerl.sportverband.entity.Spiel;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@RequestScoped
@Transactional
public class SpielplanRepository extends SingleIdEntityRepository<Spiel> {

    public List<Spiel> findByMannschaft(Mannschaft m ){
        Query q = this.getEntityManager().createQuery("Select s FROM Spiel as s WHERE s.mannschaftGast="+
                ":mannschaft"+ " OR s.mannschaftHeim="+":mannschaft");
        q.setParameter("mannschaft", m);
        List<Spiel> spiele = q.getResultList();
        if(spiele.isEmpty()){
            return null;
        } else {
            return spiele;
        }
    }

    public List<Spiel> findByLigaName(String name ){
        Query q = this.getEntityManager().createQuery("Select s FROM Spiel as s WHERE s.ligaName="+
                ":ligaName");
        q.setParameter("ligaName", name);
        List<Spiel> spiele = q.getResultList();
        if(spiele.isEmpty()){
            return Collections.emptyList();
        } else {
            return spiele;
        }
    }
}

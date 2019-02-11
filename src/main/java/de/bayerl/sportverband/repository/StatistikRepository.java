package de.bayerl.sportverband.repository;


import de.bayerl.sportverband.entity.Mannschaft;
import de.bayerl.sportverband.entity.Statistik;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
@Transactional
public class StatistikRepository extends SingleIdEntityRepository<Statistik>{
    public Statistik findByMannschaft(Mannschaft m){
        Query q = this.getEntityManager().createQuery("Select s FROM Statistik as s WHERE s.mannschaft= " +
                ":mannschaft");
        q.setParameter("mannschaft",m);
        List<Statistik> stats = q.getResultList();
        if (stats.isEmpty()){
            return null;
        } else {
            return stats.get(0);
        }
    }
}

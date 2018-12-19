package de.bayerl.sportverband.repository;

import de.bayerl.sportverband.entity.BasisEntity;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/*
adapted from: JAVA EE 7 Enterprise-Anwendungsentwicklung leicht gemacht
*/

public abstract class SingleIdEntityRepository<E extends BasisEntity> implements Serializable {
    @PersistenceContext(unitName = "sportverbandPU")
    private EntityManager entityManager;

    private final Class<BasisEntity> clasz;

    protected SingleIdEntityRepository(){
        final ParameterizedType genericSuperClass = (ParameterizedType) getClass().getSuperclass().getGenericSuperclass();
        clasz = (Class<BasisEntity>) genericSuperClass.getActualTypeArguments()[0];
    }

    protected EntityManager getEntityManager(){
        return this.entityManager;
    }

    public E persist(E entity){
        this.entityManager.persist(entity);
        System.out.println(entity);
        return entity;
    }
    public E merge (E entity){
        return this.entityManager.merge(entity);
    }
    public E findById(Long id){
        return (E) entityManager.find(clasz,id);
    }
    public void remove(Long id){
        final E entity = findById(id);
        entityManager.remove(entity);
    }
    public List<E> findAll() {
        final String statement = "SELECT e FROM " + clasz.getSimpleName() + " e";
        final Query query = entityManager.createQuery(statement);
        return query.getResultList();
    }
}

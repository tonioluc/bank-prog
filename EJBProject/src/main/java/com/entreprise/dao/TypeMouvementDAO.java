package com.entreprise.dao;

import com.entreprise.entities.TypeMouvement;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class TypeMouvementDAO {
    
    @PersistenceContext(unitName = "EJBProjectPU")
    private EntityManager em;
    
    public TypeMouvement create(TypeMouvement type) {
        em.persist(type);
        return type;
    }
    
    public TypeMouvement findById(Integer id) {
        return em.find(TypeMouvement.class, id);
    }
    
    public List<TypeMouvement> findAll() {
        return em.createQuery("SELECT t FROM TypeMouvement t ORDER BY t.description", TypeMouvement.class)
                 .getResultList();
    }
    
    public TypeMouvement update(TypeMouvement type) {
        return em.merge(type);
    }
    
    public void delete(Integer id) {
        TypeMouvement type = findById(id);
        if (type != null) {
            em.remove(type);
        }
    }
}

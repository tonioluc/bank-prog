package com.entreprise.dao;

import com.entreprise.entities.CompteCourant;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Stateless
public class CompteCourantDAO {
    
    @PersistenceContext(unitName = "EJBProjectPU")
    private EntityManager em;
    
    public CompteCourant create(CompteCourant compte) {
        em.persist(compte);
        return compte;
    }
    
    public CompteCourant findById(Integer id) {
        return em.find(CompteCourant.class, id);
    }
    
    public List<CompteCourant> findAll() {
        return em.createQuery(
            "SELECT c FROM CompteCourant c JOIN FETCH c.client ORDER BY c.client.nom", 
            CompteCourant.class
        ).getResultList();
    }
    
    public CompteCourant findByIdWithClient(Integer id) {
        TypedQuery<CompteCourant> query = em.createQuery(
            "SELECT c FROM CompteCourant c JOIN FETCH c.client WHERE c.idCompte = :id", 
            CompteCourant.class
        );
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public CompteCourant update(CompteCourant compte) {
        return em.merge(compte);
    }
    
    public void delete(Integer id) {
        CompteCourant compte = findById(id);
        if (compte != null) {
            em.remove(compte);
        }
    }
}
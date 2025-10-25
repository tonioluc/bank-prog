package com.entreprise.dao;

import com.entreprise.entities.MouvementCourant;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Stateless
public class MouvementCourantDAO {
    
    @PersistenceContext(unitName = "EJBProjectPU")
    private EntityManager em;
    
    public MouvementCourant create(MouvementCourant mouvement) {
        em.persist(mouvement);
        em.flush(); // Pour obtenir l'ID immédiatement
        return mouvement;
    }
    
    public MouvementCourant findById(Integer id) {
        return em.find(MouvementCourant.class, id);
    }
    
    public MouvementCourant findByIdWithDetails(Integer id) {
        TypedQuery<MouvementCourant> query = em.createQuery(
            "SELECT m FROM MouvementCourant m " +
            "JOIN FETCH m.typeMouvement " +
            "JOIN FETCH m.compte c " +
            "JOIN FETCH c.client " +
            "WHERE m.idMouvement = :id", 
            MouvementCourant.class
        );
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<MouvementCourant> findAll() {
        return em.createQuery(
            "SELECT m FROM MouvementCourant m " +
            "JOIN FETCH m.typeMouvement " +
            "JOIN FETCH m.compte c " +
            "JOIN FETCH c.client " +
            "ORDER BY m.idMouvement DESC", 
            MouvementCourant.class
        ).getResultList();
    }
    
    public List<MouvementCourant> findByCompte(Integer idCompte) {
        TypedQuery<MouvementCourant> query = em.createQuery(
            "SELECT m FROM MouvementCourant m " +
            "JOIN FETCH m.typeMouvement " +
            "WHERE m.compte.idCompte = :idCompte " +
            "ORDER BY m.idMouvement DESC", 
            MouvementCourant.class
        );
        query.setParameter("idCompte", idCompte);
        return query.getResultList();
    }
    
    public List<MouvementCourant> findByType(Integer idTypeMouvement) {
        TypedQuery<MouvementCourant> query = em.createQuery(
            "SELECT m FROM MouvementCourant m " +
            "JOIN FETCH m.compte c " +
            "JOIN FETCH c.client " +
            "WHERE m.typeMouvement.idTypeMouvement = :idType " +
            "ORDER BY m.idMouvement DESC", 
            MouvementCourant.class
        );
        query.setParameter("idType", idTypeMouvement);
        return query.getResultList();
    }
    
    public MouvementCourant update(MouvementCourant mouvement) {
        return em.merge(mouvement);
    }
    
    public void delete(Integer id) {
        MouvementCourant mouvement = findById(id);
        if (mouvement != null) {
            em.remove(mouvement);
        }
    }
    
    public Long count() {
        return em.createQuery("SELECT COUNT(m) FROM MouvementCourant m", Long.class)
                 .getSingleResult();
    }
}